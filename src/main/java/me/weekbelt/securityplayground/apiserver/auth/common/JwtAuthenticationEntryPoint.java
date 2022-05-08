package me.weekbelt.securityplayground.apiserver.auth.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.weekbelt.securityplayground.apiserver.common.ErrorCode;
import me.weekbelt.securityplayground.apiserver.common.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        RuntimeException exception = (RuntimeException) request.getAttribute("exception");
        ErrorResponse errorResponse = getErrorResponse(exception);
        setResponse(response, errorResponse);
    }

    private ErrorResponse getErrorResponse(RuntimeException exception) {
        if (exception instanceof ExpiredJwtException) {
            return ErrorResponse.of(ErrorCode.TOKEN_EXPIRED);
        }

        return ErrorResponse.of(ErrorCode.TOKEN_INVALID);
    }

    private void setResponse(HttpServletResponse response, ErrorResponse errorResponse) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        OutputStream outputStream = response.getOutputStream();
        objectMapper.writeValue(outputStream, errorResponse);
        outputStream.flush();
    }
}
