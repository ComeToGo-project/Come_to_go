package com.cometogo.ctg.user.dao;


import com.cometogo.ctg.user.dto.UserAddressDto;
import com.cometogo.ctg.user.dto.UserDto;
import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    // 이름과 이메일로 아이디 찾기
    @Select("SELECT id FROM ctg_users WHERE user_name = #{userName} AND email = #{email}")
    String findIdByNameAndEmail(@Param("userName") String userName, @Param("email") String email);

    // 아이디와 이메일로 회원 찾기
    @Select("SELECT * FROM ctg_users WHERE id = #{userId} AND email = #{email}")
    UserDto findByIdAndEmail(@Param("userId") String userId, @Param("email") String email);

    // 비밀번호 업데이트
    @Update("UPDATE ctg_users SET pw = #{password} WHERE id = #{userId}")
    void updatePassword(@Param("userId") String userId, @Param("password") String password);

    @Select("SELECT user_name FROM ctg_users WHERE user_id = #{userId}")
    String findByName(Long userId);

    @Select("SELECT nickname FROM ctg_users WHERE user_id = #{userId}")
    String findByNickName(Long userId);

    @Select("SELECT id FROM ctg_users WHERE user_id = #{userId}")
    String findById(Long userId);

    @Update("UPDATE ctg_users SET pw = #{newPw} WHERE user_id = #{userId}")
    boolean changePassword(Long userId, String newPw);

    @Select("SELECT pw FROM ctg_users WHERE user_id = #{uesrId}")
    String findPasswordByUserId(Long userId);

    @Update("UPDATE ctg_users SET nickname = #{newNickname} WHERE user_id = #{userId}")
    void updateNickname(Long userId, String newNickname);

}

