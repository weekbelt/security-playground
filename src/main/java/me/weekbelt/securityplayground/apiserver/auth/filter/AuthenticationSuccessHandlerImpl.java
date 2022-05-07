package me.weekbelt.securityplayground.apiserver.auth.filter;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.weekbelt.securityplayground.apiserver.auth.TokenProvider;
import me.weekbelt.securityplayground.apiserver.auth.dto.MemberContext;
import me.weekbelt.securityplayground.apiserver.auth.dto.TokenDto;
import me.weekbelt.securityplayground.persistence.auth.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MemberContext memberContext = (MemberContext) authentication.getPrincipal();
        Member member = memberContext.getMember();
        List<String> roles = getRoles(memberContext);

        log.info("Authentication Success! username: {}", member.getUsername());

        setResponse(response, tokenProvider.createTokenDto(member, roles));
    }

    private List<String> getRoles(MemberContext memberContext) {
        return memberContext.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(toList());
    }

    private void setResponse(HttpServletResponse response, TokenDto tokenDto) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        OutputStream outputStream = response.getOutputStream();
        objectMapper.writeValue(outputStream, tokenDto);
        outputStream.flush();
    }
}

