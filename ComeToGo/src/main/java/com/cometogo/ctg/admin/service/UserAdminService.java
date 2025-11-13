package com.cometogo.ctg.admin.service;

import com.cometogo.ctg.admin.dao.UserAdminDao;
import com.cometogo.ctg.admin.dao.UserBanDao;
import com.cometogo.ctg.admin.dto.UserBanDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserAdminService {
    private final UserAdminDao userAdminDao;
    private final UserBanDao userBanDao;

    @Transactional
    public void banUser(Long userId, int banDays) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime banEnd = now.plusDays(banDays);

        UserBanDto userBanDto = new UserBanDto();
        userBanDto.setUserId(userId);
        userBanDto.setBanStart(now);
        userBanDto.setBanEnd(banEnd);
        userBanDto.setBanStatus("BAN");
        userBanDao.insertUserBan(userBanDto);

        userAdminDao.suspendUser(userId);
    }

    @Transactional
    public void unbanUser(Long userId) {
        userBanDao.updateBanStatusByUser(userId, "ACTIVE");

        userAdminDao.unsuspendUser(userId);
    }
}
