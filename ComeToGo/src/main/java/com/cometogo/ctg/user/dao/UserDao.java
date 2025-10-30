package com.cometogo.ctg.user.dao;


import com.cometogo.ctg.user.dto.UserDto;
import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    boolean joinUser(@Valid UserDto userDto);

    int countById(String id); // 아이디 중복 횟수 조회

    int countByEmail(String email);  // 이메일 중복 횟수 조회

    int countByNickname(String nickname);  // 닉네임 중복 횟수 조회

    // 아이디 찾기
    UserDto findUserById(String id);
    // 닉네임 찾기
    String findNicknameByUserId(Long userId);
}
