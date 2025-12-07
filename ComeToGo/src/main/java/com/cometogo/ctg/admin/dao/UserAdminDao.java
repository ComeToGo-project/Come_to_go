package com.cometogo.ctg.admin.dao;

import com.cometogo.ctg.admin.dto.UserAdminDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserAdminDao {

    List<UserAdminDto> findUsers(@Param("filterType") String filterType,
                                 @Param("keyword") String keyword,
                                 @Param("userStatus") String userStatus);

    int suspendUser(@Param("userId") Long userId);

    int unsuspendUser(@Param("userId") Long userId);

    Long findUserIdByNickname(String nickname);

    String findUserEmailById(Long userId);
}
