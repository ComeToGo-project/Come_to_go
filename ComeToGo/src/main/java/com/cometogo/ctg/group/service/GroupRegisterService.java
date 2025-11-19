package com.cometogo.ctg.group.service;

import com.cometogo.ctg.group.dao.GroupDao;
import com.cometogo.ctg.group.dao.GroupRegisterDao;
import com.cometogo.ctg.group.dto.GroupRegisterDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GroupRegisterService {


    private final GroupDao groupDao;

    private final GroupRegisterDao groupRegisterDao;

    public GroupRegisterService(GroupDao groupDao, GroupRegisterDao groupRegisterDao) {
        this.groupDao = groupDao;
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

    public GroupRegisterDto getRegisterById(Long registerId) {
        return groupRegisterDao.findById(registerId);
    }

    /** 🔹 그룹별 신청 목록 조회 */
    public List<GroupRegisterDto> getRegistersByGroup(Long groupId) {
        return groupRegisterDao.findByGroupId(groupId);
    }

    public void updateRegisterStatus(Long registerId, String status, Long processedBy) {

        // 1) 상태 변경 (이미 있음)
        GroupRegisterDto dto = new GroupRegisterDto();
        dto.setGroupJoinId(registerId);
        dto.setJoinStatus(status);
        dto.setProcessedBy(processedBy);
        groupRegisterDao.updateStatus(dto);

        // 2) 승인일 때만 멤버 테이블에 추가해야 함 (❗ 빠져있음)
        if (status.equals("APPROVED")) {
            // groupId, userId 가져오기
            GroupRegisterDto info = groupRegisterDao.findById(registerId);

            groupDao.insertGroupMember(
                    info.getGroupId(),
                    info.getUserId(),
                    "MEMBER",
                    "ACTIVE"
            );
        }
    }


}


