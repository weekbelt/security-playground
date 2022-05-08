package me.weekbelt.securityplayground.apiserver.auth.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.weekbelt.securityplayground.apiserver.auth.TokenProvider;
import me.weekbelt.securityplayground.apiserver.auth.common.AuthenticationFailureHandlerImpl;
import me.weekbelt.securityplayground.apiserver.auth.filter.AuthenticationFilter;
import me.weekbelt.securityplayground.apiserver.auth.common.AuthenticationSuccessHandlerImpl;
import me.weekbelt.securityplayground.apiserver.auth.common.JwtAuthenticationEntryPoint;
import me.weekbelt.securityplayground.apiserver.auth.filter.JwtAuthenticationFilter;
import me.weekbelt.securityplayground.apiserver.auth.provider.AuthenticationProviderImpl;
import me.weekbelt.securityplayground.apiserver.auth.service.MemberService;
import me.weekbelt.securityplayground.persistence.auth.service.MemberDataService;
import me.weekbelt.securityplayground.persistence.auth.service.MemberRoleDataService;
import me.weekbelt.securityplayground.persistence.auth.service.RoleDataService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberDataService memberDataService;

    private final MemberRoleDataService memberRoleDataService;

    private final RoleDataService roleDataService;

    private final TokenProvider tokenProvider;

    private final ObjectMapper objectMapper;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin().disable()
            .httpBasic().disable()
            .csrf().disable();

        http
            .sessionManagement().sessionCreationPolicy(STATELESS);

        http
            .authorizeRequests()
            .antMatchers("/admin/**")
            .authenticated();

        http
            .addFilter(authenticationFilter())
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint())
        ;
    }

    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint(objectMapper);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setFilterProcessesUrl("/auth/login");
        authenticationFilter.setAuthenticationManager(authenticationManager());
        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return authenticationFilter;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(tokenProvider, memberService());
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthenticationProviderImpl(memberService(), passwordEncoder());
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandlerImpl(tokenProvider, objectMapper);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandlerImpl(objectMapper);
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberDataService, memberRoleDataService, roleDataService, passwordEncoder());
    }
}
