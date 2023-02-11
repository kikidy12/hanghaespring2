package com.example.hanghaespring2.common.config;


//import com.sparta.myselectshop.jwt.JwtAccessDeniedHandler;
//import com.sparta.myselectshop.jwt.JwtAthenticationEntryPoint;
//import com.sparta.myselectshop.jwt.JwtFilter;
//import com.sparta.myselectshop.jwt.TokenProvider;
import com.example.hanghaespring2.common.jwt.JwtAccessDeniedHandler;
import com.example.hanghaespring2.common.jwt.JwtAthenticationEntryPoint;
import com.example.hanghaespring2.common.jwt.JwtFilter;
import com.example.hanghaespring2.common.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {


    private final TokenProvider tokenProvider;
    private final JwtAthenticationEntryPoint jwtAtuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().mvcMatchers(
                "/api-docs/**",
                "/swagger-ui/**"
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()

                .authorizeRequests()
                .antMatchers("/api/signup", "/api/login").permitAll()
                .antMatchers(HttpMethod.GET, "/api/post/").permitAll()
                .antMatchers(HttpMethod.GET, "/api/post/{id}").permitAll()
                .antMatchers("/api/**").hasRole("USER")

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAtuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)


                .and()
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}