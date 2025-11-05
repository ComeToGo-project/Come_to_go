package com.cometogo.ctg.admin.service;

import com.cometogo.ctg.admin.dao.GroupAdminDao;
import com.cometogo.ctg.admin.dto.GroupAdminDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupAdminService {

    private final GroupAdminDao groupAdminDao;

    public List<GroupAdminDto> getAllGroups() {
        return groupAdminDao.findAllGroups();
    }

    public void deleteGroup(Long groupId) {
        groupAdminDao.deleteGroup(groupId);
    }

    public void addWarning(Long groupId) {
        groupAdminDao.addGroupWarning(groupId);
    }
}
