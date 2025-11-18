package com.cometogo.ctg.user.dao;

import com.cometogo.ctg.user.dto.UserAddressDto;
import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAddressDao {

    boolean joinUserAddress(UserAddressDto userAddressDto);

    boolean updateAddress(@Valid UserAddressDto addressDto);
}
