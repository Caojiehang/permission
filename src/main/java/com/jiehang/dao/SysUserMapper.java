package com.jiehang.dao;

import com.jiehang.beans.PageQuery;
import com.jiehang.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser findByKeyword(@Param("keyword") String keyword);

    int countByMail(@Param("mail") String mail,@Param("id") Integer id);
    int countByTelephone(@Param("telephone") String telephone,@Param("id") Integer id);

    int countByDeptId(@Param("deptId") int deptId);
    int countByUserName(@Param("userName") String userName);
    List<SysUser> getPageByUserName(@Param("userName") String userName,@Param("page") PageQuery page);

    List<SysUser> getPageByDeptId(@Param("deptId") int deptId, @Param("page") PageQuery page);
    List<SysUser> getByIdList(@Param("idList") List<Integer> idList);
    List<SysUser> getAll();

    List<String> getNameByIdList(@Param("idList") List<Integer> idList);

    List<Integer> getIdListByNameList(@Param("nameList") List<String> nameList);

}