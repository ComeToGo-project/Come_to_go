package com.cometogo.ctg.admin.service;

import com.cometogo.ctg.admin.dao.MarketAdminDao;
import com.cometogo.ctg.admin.dto.MarketAdminDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketAdminService {
    private final MarketAdminDao marketAdminDao;

    public List<MarketAdminDto> getAllMarketItems() {
        return marketAdminDao.findAllMarketItems();
    }

    public void deleteMarketItem(Long itemId) {
        marketAdminDao.deleteMarketItem(itemId);
    }
}
