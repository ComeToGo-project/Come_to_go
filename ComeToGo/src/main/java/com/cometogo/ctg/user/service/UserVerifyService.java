package com.cometogo.ctg.user.service;

import com.cometogo.ctg.user.dao.UserVerifyDao;
import com.cometogo.ctg.user.dto.UserVerifyDto;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserVerifyService {

    private final UserVerifyDao userVerifyDao;
    private final JavaMailSender mailSender;

    // 인증 코드 생성 및 이메일 발송
    public void sendVerificationEmail(String email) {
        // 기존 인증 코드 삭제
        userVerifyDao.deleteByEmail(email);
        System.out.println("기존 인증코드 삭제");
        // 6자리 랜덤 인증 코드 생성
        String verifyCode = generateVerifyCode();
        System.out.println("랜덤 인증코드 생성");
        // DB 저장
        UserVerifyDto verifyDto = new UserVerifyDto();
        verifyDto.setEmail(email);
        verifyDto.setVerifyCode(verifyCode);
        userVerifyDao.insertVerify(verifyDto);
        System.out.println("DB 저장");
        // 이메일 발송
        sendEmail(email, verifyCode);
        System.out.println("이메일 발송");
    }

    // 인증 코드 확인
    public String verifyCode(String email, String code) {
        System.out.println("=== 인증 코드 확인 시작 ===");
        System.out.println("이메일: " + email);
        System.out.println("입력 코드: " + code);

        UserVerifyDto verify = userVerifyDao.selectByEmailAndCode(email, code);

        if (verify == null) {
            System.out.println("❌ DB에서 해당 이메일/코드 조합을 찾을 수 없음");
            return "invalid"; // 잘못된 코드
        }

        System.out.println("✓ DB에서 인증 정보 찾음");
        System.out.println("DB 코드: " + verify.getVerifyCode());
        System.out.println("만료 시간: " + verify.getExpiredAt());
        System.out.println("현재 시간: " + LocalDateTime.now());
        System.out.println("인증 여부: " + verify.getIsVerify());

        // 이미 인증된 코드인지 확인
        if (verify.getIsVerify()) {
            System.out.println("❌ 이미 인증된 코드");
            return "already"; // 이미 인증됨
        }

        // 만료 시간 확인
        if (LocalDateTime.now().isAfter(verify.getExpiredAt())) {
            System.out.println("❌ 인증 코드가 만료됨");
            return "expired"; // 만료됨
        }

        // 인증 성공 처리
        System.out.println("✓ 인증 성공! 업데이트 진행");
        userVerifyDao.updateVerifySuccess(verify.getVerifyId(), LocalDateTime.now());
        System.out.println("=== 인증 완료 ===");

        return "success"; // 인증 성공
    }

    // 랜덤 6자리 코드 생성
    private String generateVerifyCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    // 이메일 발송
    private void sendEmail(String to, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject("[CTG] 이메일 인증 코드");
            helper.setText("인증 코드: " + code + "\n\n5분 이내에 입력해주세요.");

            mailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("이메일 발송 실패", e);
        }
    }
}

