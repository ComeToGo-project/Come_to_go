package com.cometogo.ctg.admin.dao;

import com.cometogo.ctg.admin.dto.MarketAdminDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MarketAdminDao {
    List<MarketAdminDto> findMarketItems(
            @Param("filterType") String filterType,
            @Param("keyword") String keyword,
            @Param("status") String status
    );

    int deleteItemImages(@Param("itemId") Long itemId);
    int deleteMarketChats(@Param("itemId") Long itemId);
    int deleteMarketItem(@Param("itemId") Long itemId);
}
