package com.cometogo.ctg.admin.dao;

import com.cometogo.ctg.admin.dto.GroupAdminDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GroupAdminDao {

    List<GroupAdminDto> findGroups(
            @Param("filterType") String filterType,
            @Param("keyword") String keyword,
            @Param("warnSort") String warnSort
    );

    //동호회 삭제
    int deleteGroup(@Param("groupId") Long groupId);

    //모든 자식 테이블 삭제 메소드
    int deleteGroupLocations(@Param("groupId") Long groupId);
    int deleteGroupUsers(@Param("groupId") Long groupId);
    int deleteGroupBoards(@Param("groupId") Long groupId);
    int deleteGroupJoins(@Param("groupId") Long groupId);
    int deleteGroupChats(@Param("groupId") Long groupId);
    int deleteGroupMarkets(@Param("groupId") Long groupId);
    int deleteGroupSchedules(@Param("groupId") Long groupId);

    int addGroupWarning(@Param("groupId") Long groupId);

    Long findOwnerUserIdByNickname(@Param("nickname") String nickname);
}
