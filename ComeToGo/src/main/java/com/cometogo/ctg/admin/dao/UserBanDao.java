package com.cometogo.ctg.admin.dao;

import com.cometogo.ctg.admin.dto.UserBanDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserBanDao {

    int insertUserBan(UserBanDto banDto);

    int updateBanStatusByUser(@Param("userId") Long userId, @Param("status") String newStatus);

}
