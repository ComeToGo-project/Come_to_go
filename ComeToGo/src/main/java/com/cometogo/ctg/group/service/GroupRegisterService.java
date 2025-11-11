package com.cometogo.ctg.group.service;

import com.cometogo.ctg.group.dao.GroupRegisterDao;
import com.cometogo.ctg.group.dto.GroupRegisterDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GroupRegisterService {

    private final GroupRegisterDao groupRegisterDao;

    public GroupRegisterService(GroupRegisterDao groupRegisterDao) {
        this.groupRegisterDao = groupRegisterDao;
    }

    /** 🔹 가입 신청 */
    @Transactional
    public void requestRegister(Long userId, Long groupId, String message) {
        GroupRegisterDto existing = groupRegisterDao.findByUserAndGroup(userId, groupId);
        if (existing != null) {
            throw new IllegalStateException("이미 가입 신청을 하셨습니다.");
        }

        GroupRegisterDto register = new GroupRegisterDto();
        register.setUserId(userId);
        register.setGroupId(groupId);
        register.setMessage(message);
        groupRegisterDao.insertRegister(register);
    }

    /** 🔹 그룹별 신청 목록 조회 */
    public List<GroupRegisterDto> getRegistersByGroup(Long groupId) {
        return groupRegisterDao.findByGroupId(groupId);
    }

    /** 🔹 승인/거절 처리 */
    @Transactional
    public void updateRegisterStatus(Long registerId, String status, Long adminId) {
        GroupRegisterDto dto = new GroupRegisterDto();
        dto.setGroupJoinId(registerId);
        dto.setJoinStatus(status);
        dto.setProcessedBy(adminId);
        groupRegisterDao.updateStatus(dto);
    }
}


