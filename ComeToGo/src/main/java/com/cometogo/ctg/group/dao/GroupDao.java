package com.cometogo.ctg.group.dao;

import com.cometogo.ctg.group.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

    GroupDetailDto getGroupDetailById(@Param("groupId") Long groupId,
                                      @Param("loginUserId") Long loginUserId);

    int getMemberCountByGroupId(Long groupId);


    int getPostCountByGroupId(Long groupId);


    List<MemberDto> getMembersByGroupId(Long groupId);


    List<ScheduleDto> getScheduleByGroupId(Long groupId);

    List<PostDto> getPostsByGroupId(Long groupId);

    //List<CategoryDto> getAllCategories();


    boolean isUserMemberOfGroup(@Param("groupId") Long groupId, @Param("userId") Long userId);

    boolean isGroupNameExists(@Param("groupName") String groupName);

    List<GroupDetailDto> selectAllGroups();

    List<GroupDetailDto> searchGroups( @Param("keyword") String keyword,
                                       @Param("region") String region,
                                       @Param("category") String category,
                                       @Param("sort") String sort);


    List<MyGroupDto> getMyGroups(@Param("userId") Long userId);

    boolean existsMemberInGroup(@Param("groupId") Long groupId, @Param("userId") Long userId);

    boolean isMember(@Param("userId") Long userId,
                     @Param("groupId") Long groupId);


    /**
     * 그룹 ID로 그룹 정보 조회
     * owner_user_id도 같이 가져와서 회장 권한 체크용
     */
    @Select("SELECT group_id AS groupId, group_name AS groupName, owner_user_id AS ownerUserId " +
            "FROM ctg_group " +
            "WHERE group_id = #{groupId}")
    GroupDto findById(Long groupId);

}
