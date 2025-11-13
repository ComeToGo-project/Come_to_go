package com.cometogo.ctg.admin.dao;

import com.cometogo.ctg.admin.dto.UserAdminDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserAdminDao {

    List<UserAdminDto> users(
            @Param("filterType") String filterType,
            @Param("keyword") String keyword,
            @Param("userStatus") String userStatus
    );

//    UserAdminDto findByUsername(@Param("userId") Long userId);

    int suspendUser(@Param("userId") Long userId);

    int unsuspendUser(@Param("userId") Long userId);
}
