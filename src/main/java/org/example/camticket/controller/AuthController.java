package org.example.camticket.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.camticket.controller.response.KakaoLoginResponse;
import org.example.camticket.dto.UserDto;
import org.example.camticket.service.AuthService;
import org.example.camticket.service.KakaoService;
import org.example.camticket.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService; // 사용자 인증 관련 서비스

    private final KakaoService kakaoService; // 카카오 API와 통신하는 서비스

    private final JwtUtil jwtUtil;

    @Value("${custom.jwt.secret}") // application properties에서 JWT 비밀키를 주입받음
    private String SECRET_KEY;

    @Value("${custom.jwt.expire-time-ms}") // JWT 만료 시간을 주입받음
    private long EXPIRE_TIME_MS;
    @Value("${custom.jwt.refresh-expire-time-ms}") // JWT 만료 시간을 주입받음
    private long EXPIRE_REFRESH_TIME_MS;

    // 카카오 로그인을 처리하는 엔드포인트 코드를 받자마자 GetMapping 호출됨
    // http://localhost:8080/camticket/auth/kakao-login"
    @GetMapping("/camticket/auth/kakao-login")
    public ResponseEntity<KakaoLoginResponse> kakaoLogin(
            @RequestParam String code,
            HttpServletRequest request,
            HttpServletResponse response) { // HttpServletResponse 추가

        UserDto userDto =
                authService.kakaoLogin(
                        kakaoService.kakaoLogin(code, request.getHeader("Origin") + "/login/oauth/kakao"));

        // JWT 토큰 생성
        List<String> jwtToken = jwtUtil.createToken(userDto.getId(), SECRET_KEY, EXPIRE_TIME_MS, EXPIRE_REFRESH_TIME_MS);

        // TODO: 액세스 토큰을 Authorization 헤더에 추가
        response.setHeader("Authorization", "Bearer " + jwtToken.get(0));


        // 응답 본문에 JWT 토큰 및 사용자 정보 추가
        return ResponseEntity.ok(
                KakaoLoginResponse.builder()
                        .name(userDto.getName())
                        .profileImageUrl(userDto.getProfileImageUrl())
                        .email(userDto.getEmail())
                        .build());
    }
}
