package com.cometogo.ctg.admin.service;

import com.cometogo.ctg.admin.dao.SystemAdminDao;
import com.cometogo.ctg.admin.dto.SystemAdminDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SystemAdminService {
    private final SystemAdminDao systemAdminDao;

    public List<SystemAdminDto> getAllCategories() {
        return systemAdminDao.findAllCategories();
    }

    public void addCategory(String categoryName) {
        systemAdminDao.addCategory(categoryName);
    }

    public void deleteCategory(Long categoryId) {
        systemAdminDao.deleteCategory(categoryId);
    }
}
