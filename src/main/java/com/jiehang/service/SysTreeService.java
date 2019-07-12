package com.jiehang.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.jiehang.dao.SysDeptMapper;
import com.jiehang.dto.DeptLevelDto;
import com.jiehang.model.SysDept;
import com.jiehang.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @ClassName SysTreeService
 * @Description TODO
 * @Author jiehangcao
 * @Date 2019-07-11 17:03
 **/
@Service
public class SysTreeService {
    @Resource
    private SysDeptMapper sysDeptMapper;

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
        Collections.sort(rootList, new Comparator<DeptLevelDto>() {
            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });
        //recursion to build tree
        transformDeptTree(rootList, LevelUtil.ROOT, levelDeptMap);
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
