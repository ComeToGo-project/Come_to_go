package com.cometogo.ctg.admin.dao;

import com.cometogo.ctg.admin.dto.UserBanDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserBanDao {

    UserBanDto findActiveBanByUser(@Param("userId") Long userId);

    int insertUserBan(UserBanDto banDto);

    int updateBanStatusByUser(@Param("userId") Long banId, @Param("banStatus") String banStatus);

}
