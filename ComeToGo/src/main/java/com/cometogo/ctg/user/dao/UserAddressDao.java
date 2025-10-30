package com.cometogo.ctg.user.dao;

import com.cometogo.ctg.user.dto.UserAddressDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAddressDao {

    boolean joinUserAddress(UserAddressDto userAddressDto);
}
