package com.cometogo.ctg.user.dao;

import com.cometogo.ctg.user.dto.UserVerifyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface UserVerifyDao {
    // 인증 정보 저장
    void insertVerify(UserVerifyDto verifyDto);

    // 이메일과 코드로 인증 정보 조회
    UserVerifyDto selectByEmailAndCode(@Param("email") String email,
                                   @Param("verifyCode") String verifyCode);

    // 이메일로 최근 인증 정보 조회
    UserVerifyDto selectLatestByEmail(String email);

    // 인증 성공 처리
    void updateVerifySuccess(@Param("verifyId") Long verifyId,
                             @Param("verifiedAt") LocalDateTime verifiedAt);

    // 이메일로 기존 인증 정보 삭제
    void deleteByEmail(String email);
}
