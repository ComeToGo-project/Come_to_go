package com.cometogo.ctg.user.service;


import com.cometogo.ctg.user.dao.UserAddressDao;
import com.cometogo.ctg.user.dao.UserDao;
import com.cometogo.ctg.user.dto.UserAddressDto;
import com.cometogo.ctg.user.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserDao userDao;
    private final UserAddressDao userAddressDao;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserDao userDao, UserAddressDao userAddressDao, PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.userAddressDao = userAddressDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public boolean joinUser(@Valid UserDto userDto) {
        // 아이디 중복검사
        if (isIdDuplicate(userDto.getId())) {
            return false;
        }

        // 1. 비밀번호 암호화
        String encodedPw = passwordEncoder.encode(userDto.getPw());
        userDto.setPw(encodedPw);

        // 2. 회원 저장
        boolean userSaved = userDao.joinUser(userDto);
        if (!userSaved) return false;

        // 3. 주소 저장 전, 회원 PK(user_id)가 userDto에 세팅돼 있어야 함
        UserAddressDto addressDto = userDto.getAddress();
        if (addressDto == null) return false;

        addressDto.setUserId(userDto.getUserId()); // FK 설정
        // 4. 주소 저장
        return userAddressDao.joinUserAddress(addressDto);
    }

    // 아이디 중복검사
    public boolean isIdDuplicate(String id) {
        return userDao.countById(id) > 0;
    }

    // 이메일 중복검사
    public boolean isEmailDuplicate(String email) {
        return userDao.countByEmail(email) > 0;
    }

    // 닉네임 중복검사
    public boolean isNicknameDuplicate(String nickname) {
        return userDao.countByNickname(nickname) > 0;
    }

    // 로그인
    public UserDto login(String id, String rawPw) {
        UserDto user = userDao.findUserById(id);
        if (user != null && passwordEncoder.matches(rawPw, user.getPw())) {
            return user; // 로그인 성공
        }
        return null; // 로그인 실패
    }

    public String getNicknameByUserId(Long userId) {
        return userDao.findNicknameByUserId(userId);
    }
}
