package com.cometogo.ctg.admin.service;

import com.cometogo.ctg.admin.dao.UserAdminDao;
import com.cometogo.ctg.admin.dao.UserBanDao;
import com.cometogo.ctg.admin.dto.UserAdminDto;
import com.cometogo.ctg.admin.dto.UserBanDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class UserAdminService {
    private final UserAdminDao userAdminDao;
    private final UserBanDao userBanDao;

    public void checkAndReleaseUserBan(Long userId) {
        UserBanDto banInfo = userBanDao.findActiveBanByUser(userId);
        if (banInfo != null && banInfo.getBanEnd().isBefore(now())) {
            unbanUser(userId);
        }
    }

    public List<UserAdminDto> getUsers(String filterType, String keyword, String userStatus) {
        List<UserAdminDto> userList = userAdminDao.findUsers(filterType, keyword, userStatus);
        for (UserAdminDto user : userList) {
            if ("SUSPENDED".equals(user.getUserStatus()) && user.getBanEnd() != null) {
                long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), user.getBanEnd().toLocalDate());
                user.setBanDaysLeft(daysLeft);
            }
        }
        return userList;
    }

    @Transactional
    public void banUser(Long userId, int banDays) {
        LocalDateTime now = now();
        LocalDateTime banEndLocal = now.plusDays(banDays);

        UserBanDto userBanDto = new UserBanDto();
        userBanDto.setUserId(userId);
        userBanDto.setBanStart(now);
        userBanDto.setBanEnd(banEndLocal);
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
