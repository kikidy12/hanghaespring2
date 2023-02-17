package com.example.hanghaespring2.auth.service;

import com.example.hanghaespring2.auth.dto.AuthDto;
import com.example.hanghaespring2.common.entity.User;
import com.example.hanghaespring2.common.entity.UserRoleEnum;
import com.example.hanghaespring2.common.security.jwt.TokenProvider;
import com.example.hanghaespring2.common.util.CustomClientException;
import com.example.hanghaespring2.user.dto.UserDto;
import com.example.hanghaespring2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Transactional
    public UserDto.UserRes addUser(AuthDto.UserAdd dto) {
        userRepository.findByUsername(dto.getUsername()).ifPresent(v -> {
            throw new CustomClientException("중복된 username 입니다.");
        });
        String password = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(password);

        if (dto.getRole() == UserRoleEnum.ADMIN) {
            if (!dto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new CustomClientException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
        }

        User user = User.builder().dto(dto).build();

        return this.userRepository.save(user).res();
    }


    public UserDto.UserRes login(AuthDto.Login dto, HttpServletResponse response) {

        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() ->
                new CustomClientException("회원을 찾을 수 없습니다.")
        );

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new CustomClientException("회원을 찾을 수 없습니다");
        }

        response.addHeader(TokenProvider.AUTHORIZATION_HEADER, tokenProvider.createToken(user.getUsername(), user.getRole()));

        return UserDto.UserRes.builder().user(user).build();
    }
}
