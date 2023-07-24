package com.open.library.exeption;

import com.open.library.constraints.SystemConstraints;
import com.open.library.utils.HelperUtils;
import com.open.library.utils.response.BaseResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        BaseResponse responseDTO = BaseResponse
                .builder()
                .success(false)
                .data(SystemConstraints.ACCESS_DENIED)
                .code(String.valueOf(HttpStatus.FORBIDDEN.value()))
                .build();

        String json = HelperUtils.JSON_WRITER.writeValueAsString(responseDTO);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
