package com.jiehang.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.jiehang.dao.SysAclMapper;
import com.jiehang.dao.SysAclModuleMapper;
import com.jiehang.dao.SysDeptMapper;
import com.jiehang.dto.AclDto;
import com.jiehang.dto.AclModuleLevelDto;
import com.jiehang.dto.DeptLevelDto;
import com.jiehang.model.SysAcl;
import com.jiehang.model.SysAclModule;
import com.jiehang.model.SysDept;
import com.jiehang.util.LevelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName SysTreeService
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-11 17:03
 **/
@Service
@Slf4j
public class SysTreeService {
    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    @Resource
    private SysCoreService sysCoreService;

    @Resource
    private SysAclMapper sysAclMapper;

    /**
     * get user acl info
     * @param userId
     * @return
     */
    public List<AclModuleLevelDto> userAclTree(int userId) {
        // user allocated permission
        List<SysAcl> userAclList = sysCoreService.getUserAclList(userId);
        List<AclDto> aclDtoList = Lists.newArrayList();
        for(SysAcl acl:userAclList) {
            AclDto dto = AclDto.adapt(acl);
            dto.setHasAcl(true);
            dto.setChecked(true);
            aclDtoList.add(dto);
        }
        return aclListToTree(aclDtoList);
    }

    /**
     * 1.取出用户已分配的权限点
     * 2.取出角色已分配的权限点
     * 3.取出所有的权限点
     * 4.将用户和角色所有的权限点ID进行整合
     * 5.与整个权限点做比较
     * @param roleId
     * @return
     */
    public List<AclModuleLevelDto> roleTree(int roleId) {
        // user allocated permission
        List<SysAcl> userAclList = sysCoreService.getCurrentUserAclList();
        // role allocated permission
        List<SysAcl> roleAclList = sysCoreService.getRoleAclList(roleId);

        List<AclDto> aclDtoList = Lists.newArrayList();

        List<SysAcl> allAclList = sysAclMapper.getAll();

        Set<Integer> userAclIdSet = userAclList.stream().map(sysAcl ->
                sysAcl.getId()).collect(Collectors.toSet());

        Set<Integer> roleAclIdSet =  roleAclList.stream().map(sysAcl ->
                sysAcl.getId()).collect(Collectors.toSet());

        //Set<SysAcl> aclSet = new HashSet<>(allAclList);
        //aclSet.addAll(userAclList);
        //List<SysAcl> aclAclList = sysAclMapper.getAll();
        for(SysAcl acl:allAclList) {
            AclDto dto = AclDto.adapt(acl);
            if(userAclIdSet.contains(acl.getId())) {
                dto.setHasAcl(true);
            }
            if(roleAclIdSet.contains(acl.getId())) {
                dto.setChecked(true);
            }
            aclDtoList.add(dto);
        }
        return aclListToTree(aclDtoList);
    }


    /**
     * 1.取出每个权限点模块下的集合
     * 2.绑定权限点和权限模块
     * @param aclDtoList
     * @return
     */
    public List<AclModuleLevelDto> aclListToTree(List<AclDto> aclDtoList) {
        if(CollectionUtils.isEmpty(aclDtoList)) {
            return Lists.newArrayList();
        }
        List<AclModuleLevelDto> aclModuleLevelList = aclModuleTree();
        Multimap<Integer,AclDto> moduleIdAclMap = ArrayListMultimap.create();
        for(AclDto acl: aclDtoList) {
            if(acl.getStatus() == 1) {
                moduleIdAclMap.put(acl.getAclModuleId(),acl);
            }
        }
        bindAclsWithOrder(aclModuleLevelList,moduleIdAclMap);
        return aclModuleLevelList;
    }

    /**
     *
     * @param aclModuleLevelDtoList
     * @param moduleIdAclMap
     */
    public void bindAclsWithOrder(List<AclModuleLevelDto> aclModuleLevelDtoList,Multimap<Integer,AclDto> moduleIdAclMap) {
        if(CollectionUtils.isEmpty(aclModuleLevelDtoList)) {
            return;
        }
        for(AclModuleLevelDto dto: aclModuleLevelDtoList) {
            List<AclDto> aclDtoList = (List<AclDto>) moduleIdAclMap.get(dto.getId());
            if(CollectionUtils.isNotEmpty(aclDtoList)) {
                Collections.sort(aclDtoList,aclSeqComparator);
                dto.setAclList(aclDtoList);
            }
            bindAclsWithOrder(dto.getAclModuleList(),moduleIdAclMap);
        }
    }

    /**
     * Build permission module tree display
     * @return
     */
    public List<AclModuleLevelDto> aclModuleTree() {
        List<SysAclModule> sysAclModuleList = sysAclModuleMapper.getAllAclModule();
        List<AclModuleLevelDto> dtoList = Lists.newArrayList();
        for(SysAclModule sysAclModule: sysAclModuleList) {
            dtoList.add(AclModuleLevelDto.adapt(sysAclModule));
        }
        return aclModuleListToTree(dtoList);
    }

    /**
     * Permission module tree building logic
     * @param aclModuleLevelDtoList
     * @return
     */
    public List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> aclModuleLevelDtoList) {
        if(CollectionUtils.isEmpty(aclModuleLevelDtoList)) {
            return Lists.newArrayList();
        }
        // level - > { dept1,dept2, dept3}  to handle Map<String,List<Object>>
        Multimap<String, AclModuleLevelDto> levelAclModuleMap = ArrayListMultimap.create();
        List<AclModuleLevelDto> rootList = Lists.newArrayList();

        for (AclModuleLevelDto dto : aclModuleLevelDtoList) {
            levelAclModuleMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        //sort by descending
        Collections.sort(rootList,aclModuleSeqComparator);
        //recursion to build tree
        transformAclModuleTree(rootList,LevelUtil.ROOT,levelAclModuleMap);

        return rootList;

    }

    /**
     * recursion to handle each level module
     * from root level
     * @param dtoList
     * @param level
     * @param levelAclModuleMap
     */
    public void transformAclModuleTree(List<AclModuleLevelDto> dtoList,String level,Multimap<String,AclModuleLevelDto> levelAclModuleMap) {
        for(int i = 0; i< dtoList.size();i++) {
            AclModuleLevelDto dto = dtoList.get(i);
            String nextLevel = LevelUtil.calculateLevel(level,dto.getId());
            List<AclModuleLevelDto> tempList = (List<AclModuleLevelDto>) levelAclModuleMap.get(nextLevel);
            if(CollectionUtils.isNotEmpty(tempList)) {
                Collections.sort(tempList,aclModuleSeqComparator);
                dto.setAclModuleList(tempList);
                transformAclModuleTree(tempList,nextLevel,levelAclModuleMap);
            }
        }
    }

    /**
     * Build tree data
     *
     * @return
     */
    public List<DeptLevelDto> deptTree() {
        List<SysDept> deptList = sysDeptMapper.getAllDept();
        List<DeptLevelDto> dtoList = Lists.newArrayList();
        for (SysDept dept : deptList) {
            DeptLevelDto dto = DeptLevelDto.adapt(dept);
            dtoList.add(dto);
        }
        return deptListToTree(dtoList);
    }

    /**
     * Build tree
     *
     * @param deptLevelList
     * @return
     */
    public List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelList) {
        if (CollectionUtils.isEmpty(deptLevelList)) {
            return Lists.newArrayList();
        }
        // level - > { dept1,dept2, dept3}  to handle Map<String,List<Object>>
        Multimap<String, DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = Lists.newArrayList();

        for (DeptLevelDto dto : deptLevelList) {
            levelDeptMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        //sort by descending
        Collections.sort(rootList,deptSeqComparator);
        //recursion to build tree
        transformDeptTree(rootList,LevelUtil.ROOT,levelDeptMap);
        return rootList;
    }

    /**
     * Comparator based on seq
     */
    public Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };


    public Comparator<AclModuleLevelDto> aclModuleSeqComparator = new Comparator<AclModuleLevelDto>() {
        public int compare(AclModuleLevelDto o1, AclModuleLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };


    public Comparator<AclDto> aclSeqComparator = new Comparator<AclDto>() {
        @Override
        public int compare(AclDto o1, AclDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

    /**
     * recursion method to handle all dept level
     * from root level: 0 -> all
     * level:0.1
     * level:0.2
     *
     * @param deptLevelList
     * @param level
     * @param levelDeptMap
     */
    public void transformDeptTree(List<DeptLevelDto> deptLevelList, String level, Multimap<String, DeptLevelDto> levelDeptMap) {
        for (int i = 0; i < deptLevelList.size(); i++) {
            //traverse every element under this level
            DeptLevelDto deptLevelDto = deptLevelList.get(i);
            // handle the data under this level
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            // handle next level
            List<DeptLevelDto> tempDeptList = (List<DeptLevelDto>) levelDeptMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempDeptList)) {
                //sorting
                Collections.sort(tempDeptList, deptSeqComparator);
                //set next level department
                deptLevelDto.setDeptList(tempDeptList);
                // enter next level operation
                transformDeptTree(tempDeptList, nextLevel, levelDeptMap);
            }
        }

    }


}
