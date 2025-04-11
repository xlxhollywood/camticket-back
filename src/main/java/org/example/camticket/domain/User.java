package org.example.camticket.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.camticket.domain.BaseEntity;
import org.example.camticket.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long kakaoId;

    @Setter
    @Column(columnDefinition = "varchar(200)")
    private String name;

    @Setter
    @Column(columnDefinition = "varchar(30)")
    private String nickName;

    @Setter
    @Column(columnDefinition = "varchar(30)")
    private String email;

    @Setter
    @Column(columnDefinition = "TEXT")
    private String profileImageUrl;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;



    // ***추후 성별, 생일 받으면 빌더 타입 수정해야함.
    public static User from(UserDto dto){
        return User.builder()
                .kakaoId(dto.getKakaoId())
                .name(dto.getName())
                .email(dto.getEmail())
                .profileImageUrl(dto.getProfileImageUrl())
                .role(Role.ROLE_USER) // 기본 권한 설정
                .build();
    }


}
