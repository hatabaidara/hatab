package com.shaoume.security.jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
@Component @RequiredArgsConstructor @Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req,@NonNull HttpServletResponse res,@NonNull FilterChain chain) throws ServletException,IOException {
        final String authHeader=req.getHeader("Authorization");
        if(!StringUtils.hasText(authHeader)||!authHeader.startsWith("Bearer ")) { chain.doFilter(req,res); return; }
        final String jwt=authHeader.substring(7);
        try {
            String email=jwtUtils.extractUsername(jwt);
            if(StringUtils.hasText(email)&&SecurityContextHolder.getContext().getAuthentication()==null) {
                UserDetails ud=userDetailsService.loadUserByUsername(email);
                if(jwtUtils.isTokenValid(jwt,ud)) {
                    UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(ud,null,ud.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch(Exception e) { log.error("Auth error: {}",e.getMessage()); }
        chain.doFilter(req,res);
    }
}
