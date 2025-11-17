package com.cometogo.ctg.admin.service;

import com.cometogo.ctg.admin.dao.MarketAdminDao;
import com.cometogo.ctg.admin.dto.GroupAdminDto;
import com.cometogo.ctg.admin.dto.MarketAdminDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketAdminService {
    private final MarketAdminDao marketAdminDao;

    public List<MarketAdminDto> getMarketItems(String filterType, String keyword, String status) {
        return marketAdminDao.findMarketItems(filterType, keyword, status);
    }

    @Transactional
    public void deleteMarketItem(Long itemId) {
        marketAdminDao.deleteItemImages(itemId);
        marketAdminDao.deleteMarketChats(itemId);
        marketAdminDao.deleteMarketItem(itemId);
    }
}
