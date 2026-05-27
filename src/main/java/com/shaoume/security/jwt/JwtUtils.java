package com.shaoume.security.jwt;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component @Slf4j
public class JwtUtils {
    @Value("${app.jwt.secret}") private String jwtSecret;
    @Value("${app.jwt.expiration}") private long jwtExpiration;
    @Value("${app.jwt.refresh-expiration}") private long refreshExpiration;
    public String extractUsername(String token) { return extractClaim(token,Claims::getSubject); }
    public Date extractExpiration(String token) { return extractClaim(token,Claims::getExpiration); }
    public <T> T extractClaim(String token,Function<Claims,T> resolver) { return resolver.apply(extractAllClaims(token)); }
    private Claims extractAllClaims(String token) { return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody(); }
    public String generateToken(UserDetails u) { return createToken(new HashMap<>(),u.getUsername(),jwtExpiration); }
    public String generateToken(UserDetails u,Map<String,Object> claims) { return createToken(claims,u.getUsername(),jwtExpiration); }
    public String generateRefreshToken(UserDetails u) { return createToken(new HashMap<>(),u.getUsername(),refreshExpiration); }
    private String createToken(Map<String,Object> claims,String subject,long exp) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
            .setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis()+exp))
            .signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
    }
    public boolean isTokenValid(String token,UserDetails u) { return extractUsername(token).equals(u.getUsername())&&!isTokenExpired(token); }
    public boolean isTokenExpired(String token) { return extractExpiration(token).before(new Date()); }
    public boolean validateToken(String token) {
        try { Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token); return true; }
        catch(Exception e) { log.error("JWT error: {}",e.getMessage()); return false; }
    }
    private Key getSignKey() { return Keys.hmacShaKeyFor(Decoders.BASE64.decode(Base64.getEncoder().encodeToString(jwtSecret.getBytes()))); }
}
