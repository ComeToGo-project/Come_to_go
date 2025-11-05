package com.cometogo.ctg.admin.dao;

import com.cometogo.ctg.admin.dto.GroupAdminDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GroupAdminDao {
    List<GroupAdminDto> findAllGroups();

    int deleteGroup(@Param("groupId") Long groupId);

    int addGroupWarning(@Param("groupId") Long groupId);
}
