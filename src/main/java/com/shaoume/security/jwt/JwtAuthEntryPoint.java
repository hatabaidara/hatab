package com.shaoume.security.jwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
@Component @Slf4j
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest req,HttpServletResponse res,AuthenticationException ex) throws IOException {
        log.error("Unauthorized: {}",ex.getMessage());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Map<String,Object> body=new HashMap<>();
        body.put("status",401);
        body.put("error","Unauthorized");
        body.put("message","Accès refusé : authentification requise");
        body.put("path",req.getServletPath());
        body.put("timestamp",LocalDateTime.now().toString());
        new ObjectMapper().writeValue(res.getOutputStream(),body);
    }
}
