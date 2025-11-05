package com.cometogo.ctg.admin.dto;

import lombok.Getter;

@Getter
public class MarketAdminDto {
    private Long itemId;
    private String itemName;
    private Long userId;
    private String userName;
    private String city;
    private String createdAt;
    private String status;
}
