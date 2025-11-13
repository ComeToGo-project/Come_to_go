package com.cometogo.ctg.group.dao;

import com.cometogo.ctg.group.dto.GroupRegisterDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GroupRegisterDao {


    int insertRegister(GroupRegisterDto register); // 가입 신청 등록

    List<GroupRegisterDto> findByGroupId(Long groupId); // 그룹별 신청 목록

    GroupRegisterDto findByUserAndGroup(@Param("userId") Long userId,
                                        @Param("groupId") Long groupId); // 특정 유저 신청여부

    int updateStatus(GroupRegisterDto register); // 상태 변경 (승인/거절)
    }

