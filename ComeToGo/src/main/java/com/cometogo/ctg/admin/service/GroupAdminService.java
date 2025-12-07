package com.cometogo.ctg.admin.service;

import com.cometogo.ctg.admin.dao.GroupAdminDao;
import com.cometogo.ctg.admin.dto.GroupAdminDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupAdminService {

    private final GroupAdminDao groupAdminDao;

    public List<GroupAdminDto> getGroups(String filterType, String keyword, String warnSort) {
        return groupAdminDao.findGroups(filterType, keyword, warnSort);
    }

    @Transactional
    public void deleteGroup(Long groupId) {
        groupAdminDao.deleteGroupLocations(groupId);
        groupAdminDao.deleteGroupUsers(groupId);
        groupAdminDao.deleteGroupBoards(groupId);
        groupAdminDao.deleteGroupJoins(groupId);
        groupAdminDao.deleteGroupChats(groupId);
        groupAdminDao.deleteGroupMarkets(groupId);
        groupAdminDao.deleteGroupSchedules(groupId);
        groupAdminDao.deleteGroup(groupId);
    }

    public void addWarning(Long groupId) {
        groupAdminDao.addGroupWarning(groupId);
    }
}
