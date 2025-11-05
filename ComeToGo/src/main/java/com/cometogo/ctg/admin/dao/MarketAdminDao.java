package com.cometogo.ctg.admin.dao;

import com.cometogo.ctg.admin.dto.MarketAdminDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MarketAdminDao {
    List<MarketAdminDto> findAllMarketItems();

    int deleteMarketItem(@Param("itemId") Long boardId);
}
