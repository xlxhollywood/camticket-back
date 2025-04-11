package org.example.camticket.service;


import lombok.RequiredArgsConstructor;
import org.example.camticket.domain.User;
import org.example.camticket.dto.UserDto;
import org.example.camticket.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RandomNicknameService randomNicknameService;


    // 카카오 로그인 로직
    public UserDto kakaoLogin(UserDto dto) {
        User user = userRepository
                .findByKakaoId(dto.getKakaoId())
                .orElseGet(() -> {
                    User newUser = User.from(dto);
                    newUser.setNickName(randomNicknameService.generateUniqueNickname());
                    return userRepository.save(newUser);
                });

        user.setEmail(dto.getEmail());
        user.setProfileImageUrl(dto.getProfileImageUrl());
        user.setName(dto.getName());

        return UserDto.from(user);
    }

    // 사용자 ID로 로그인한 사용자 정보 조회
    public User getLoginUser(Long userId) {
        // 사용자 ID로 사용자를 조회, 없으면 예외 발생
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
    }
}

