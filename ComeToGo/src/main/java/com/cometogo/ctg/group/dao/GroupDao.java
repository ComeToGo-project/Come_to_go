package com.cometogo.ctg.group.dao;

import com.cometogo.ctg.group.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Mapper
public interface GroupDao {


    void insertGroup(GroupDto groupDto);

    void insertGroupLocation(GroupDto groupDto);

    void insertGroupMember(@Param("groupId") Long groupId,
                           @Param("userId") Long userId,
                           @Param("role") String role,
                           @Param("status") String status);

    GroupDetailDto getGroupDetailById(Long groupId);

    int getMemberCountByGroupId(Long groupId);


    int getPostCountByGroupId(Long groupId);


    List<MemberDto> getMembersByGroupId(Long groupId);


    List<ScheduleDto> getScheduleByGroupId(Long groupId);

    List<PostDto> getPostsByGroupId(Long groupId);



    boolean isUserMemberOfGroup(@Param("groupId") Long groupId, @Param("userId") Long userId);

    List<GroupDetailDto> selectAllGroups();

    List<GroupDetailDto> searchGroups( @Param("keyword") String keyword,
                                       @Param("region") String region,
                                       @Param("category") String category,
                                       @Param("sort") String sort);


    GroupDetailDto findGroupDetailByGroupIdAndUserName(@Param("groupId") Long groupId, @Param("userName") String userName);


    List<MyGroupDto> getMyGroups(@Param("userId") Long userId);

}
