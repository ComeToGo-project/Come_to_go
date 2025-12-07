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

    public void addCategory(String categoryName, Long parentId) {
        systemAdminDao.addCategory(categoryName, parentId);
    }

    public boolean canDeleteCategory(Long categoryId) {
        return systemAdminDao.countCategoryUsedInGroups(categoryId) == 0;
    }

    public boolean deleteCategory(Long categoryId) {
        if (!canDeleteCategory(categoryId)) {
            return false;
        } else {
            systemAdminDao.deleteCategory(categoryId);
            return true;
        }
    }
}
