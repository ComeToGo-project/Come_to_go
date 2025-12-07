package com.cometogo.ctg.admin.dao;

import com.cometogo.ctg.admin.dto.SystemAdminDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemAdminDao {
    List<SystemAdminDto> findAllCategories();

    int addCategory(@Param("categoryName") String categoryName, @Param("parentId") Long parentId);

    int countCategoryUsedInGroups(@Param("categoryId") Long categoryId);

    int deleteCategory(@Param("categoryId") Long categoryId);
}
