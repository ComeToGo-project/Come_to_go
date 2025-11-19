package com.cometogo.ctg.group.service;

import com.cometogo.ctg.group.dao.GroupDao;
import com.cometogo.ctg.group.dao.GroupRegisterDao;
import com.cometogo.ctg.group.dto.GroupDetailDto;
import com.cometogo.ctg.group.dto.GroupDto;
import com.cometogo.ctg.group.dto.MemberDto;
import com.cometogo.ctg.group.dto.MyGroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class GroupService {

        private final GroupDao groupDao;
        private final GroupRegisterDao groupRegisterDao;


        /** 🔹 그룹 생성 (트랜잭션 처리) */
        @Transactional
        public Long createGroup(GroupDto groupDto) {
            // --- 동호회 생성 전 기본값 설정 (Mapper XML에서 설정되는 필드는 건너뜀) ---
            // (이 부분은 이전 답변과 동일)

            // 1. 그룹 정보 저장 (ctg_group 테이블)
            groupDao.insertGroup(groupDto); // 이 호출 후 groupDto.getGroupId()에 DB에서 생성된 ID가 주입됨

            if (groupDto.getGroupId() == null) {
                throw new IllegalStateException("그룹 ID가 생성되지 않았습니다. DB 설정(AUTO_INCREMENT, KeyProperty 등) 확인이 필요합니다.");
            }

            // 2. 위치 정보 저장 (ctg_group_location 테이블)
            groupDao.insertGroupLocation(groupDto);

            // 3. 생성자를 그룹 멤버로 추가 (group_users 테이블)
            MemberDto memberDto = new MemberDto(); // MemberDto 생성
            memberDto.setGroupId(groupDto.getGroupId());       // 생성된 그룹 ID 설정
            memberDto.setUserId(groupDto.getOwnerUserId());    // 동호회 개설자 ID 설정
            memberDto.setRole("OWNER");                        // 역할: 'OWNER'
            memberDto.setStatus("ACTIVE");                     // 상태: 'ACTIVE'
            // joinedAt은 XML에서 NOW()로 설정되므로 DTO에서 설정 불필요

            // ✅ 바로 이 부분! GroupDao 인터페이스 시그니처에 맞춰 개별 파라미터로 넘깁니다.
            groupDao.insertGroupMember(
                    memberDto.getGroupId(),   // @Param("groupId") 에 해당
                    memberDto.getUserId(),    // @Param("userId") 에 해당
                    memberDto.getRole(),      // @Param("role") 에 해당
                    memberDto.getStatus()     // @Param("status") 에 해당
            );

            // 🔹 새로 생성된 그룹 ID를 반환
            return groupDto.getGroupId();
        }

        @Transactional
        public GroupDetailDto getGroupDetailById(Long groupId, Long loginUserId) {
            GroupDetailDto groupDetail = groupDao.getGroupDetailById(groupId,loginUserId);

            if (groupDetail != null && loginUserId != null) {

                // 🔹 오너 여부
                groupDetail.setOwner(loginUserId.equals(groupDetail.getOwnerUserId()));

                // 🔹 멤버 여부 (정확하게 수정)
                boolean member = groupDao.isUserMemberOfGroup(groupId, loginUserId);
                groupDetail.setMember(member);
            }

            return groupDetail;
        }





        @Transactional(readOnly = true)
        public GroupDetailDto getGroupDetail(Long groupId, Long userId) {
            GroupDetailDto groupDetail = groupDao.getGroupDetailById(groupId,userId);
            if (groupDetail == null) return null;

            groupDetail.setMemberCount(groupDao.getMemberCountByGroupId(groupId));
            groupDetail.setPostCount(groupDao.getPostCountByGroupId(groupId));
            groupDetail.setMembers(groupDao.getMembersByGroupId(groupId));
            groupDetail.setSchedules(groupDao.getScheduleByGroupId(groupId)); // 메서드명 getEventsByGroupId → getScheduleByGroupId로 맞추기
            groupDetail.setPosts(groupDao.getPostsByGroupId(groupId));

            // 로그인한 사용자가 멤버인지 확인
            if (userId != null) {
                boolean isMember = groupDao.isUserMemberOfGroup(groupId, userId);
                groupDetail.setMember(isMember);
            } else {
                groupDetail.setMember(false);
            }

            return groupDetail;
        }





        @Transactional
        public GroupDto getGroupById(Long groupId) {
            return groupDao.findById(groupId);
        }

        @Transactional
        public List<MyGroupDto> getMyGroups(Long userId) {
            return groupDao.getMyGroups(userId);
        }

        @Transactional(readOnly = true)
        public List<GroupDetailDto> getAllGroups() {
            return groupDao.selectAllGroups();
        }

        @Transactional
        public List<GroupDetailDto> searchGroups(String keyword, String category,String region, String sort) {
            return groupDao.searchGroups(keyword,category,region,sort);
        }

        public boolean isMember(Long userId, Long groupId) {
            return groupDao.isMember(userId, groupId);
        }
    }


