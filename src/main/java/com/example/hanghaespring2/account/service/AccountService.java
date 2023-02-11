package com.example.hanghaespring2.account.service;

import com.example.hanghaespring2.account.dto.AccountDto;
import com.example.hanghaespring2.account.repository.AccountRepository;
import com.example.hanghaespring2.common.entity.User;
import com.example.hanghaespring2.jwt.TokenProvider;
import com.example.hanghaespring2.user.dto.UserDto;
import com.example.hanghaespring2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;

    @Transactional
    public UserDto.UserRes addUser(AccountDto.UserAdd dto) {
        accountRepository.findByUsername(dto.getUsername()).ifPresent(v -> {
            throw new NullPointerException("중복된 사용자가 존재합니다.");
        });

        String pasword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(pasword);

        User user = User.builder().dto(dto).build();

        return this.accountRepository.save(user).res();
    }


    public UserDto.UserRes login(AccountDto.Login dto, HttpServletResponse response) {

        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() ->
                new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        System.out.println();
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        response.addHeader(TokenProvider.AUTHORIZATION_HEADER, tokenProvider.createToken(authentication));

        return UserDto.UserRes.builder().user(user).build();
    }
}
