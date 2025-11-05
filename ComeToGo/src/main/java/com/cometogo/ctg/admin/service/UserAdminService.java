package com.cometogo.ctg.admin.service;

import com.cometogo.ctg.admin.dao.UserAdminDao;
import com.cometogo.ctg.admin.dto.UserAdminDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAdminService {
    private final UserAdminDao userAdminDao;

    public List<UserAdminDto> getAllUsers() {
        return userAdminDao.findAll();
    }

    @Transactional
    public void suspendUser(Long userId) {
        userAdminDao.suspendUser(userId);
    }

    @Transactional
    public void unsuspendUser(Long userId) {
        userAdminDao.unsuspendUser(userId);
    }

}
