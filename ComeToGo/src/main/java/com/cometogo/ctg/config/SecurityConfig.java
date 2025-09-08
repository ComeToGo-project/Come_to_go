package com.cometogo.ctg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration              // 이 클래스가 스프링 설정 클래스(Bean 등록용) 임을 명시
@EnableWebSecurity          // Spring Security를 활성화하고 보안 설정을 이 클래스에서 관리하도록 지정
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .formLogin(withDefaults());
        return http.build();
    }
    // @Bean : 이 메소드가 반환하는 객체를 스프링 컨테이너에 Bean으로 등록하겠다는 뜻.
    // SecurityFilterChain : 실제로 모든 요청이 통과하는 시큐리티 필터들의 집합. (여기서 정하는 규칙들이 들어가는곳.)
    // .authorizeHttpRequests(auth -> auth.anyRequest().authenticated()) : 들어오는 모든 요청은 인증(로그인) 되어야만 접근 가능하다는 규칙
    // 현재는 "모든 URL = 로그인 필요" 로 잠겨있는 상태.
    // .formLogin(withDefaults()) : 스프링 시큐리티가 제공하는 기본 로그인 사용.
    // return http.build(); : 설정한 보안 규칙을 SecurityFilterChain 객체로 빌드해서 스프링 컨테이너에 등록.

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호 암호화를 위한 BCrypt 해시 함수 사용
        return new BCryptPasswordEncoder();
        // PaswwordEncoder : 비밀번호 암호화 및 검증을 담당하는 인터페이스
        // new BCryptPasswordEncoder() : BCrypt 해시 함수를 사용해서 비밀번호를 암호화
        //  - 회원가입 시 : passwordEncoder.encode(사용자가 입력한 비밀번호) -> 암호화된 문자열을 DB에 저장
        //  - 로그인 시 : passwordEncoder.matches(입력값, DB 저장값) -> 두 값이 일치하는지 검증
    }
}