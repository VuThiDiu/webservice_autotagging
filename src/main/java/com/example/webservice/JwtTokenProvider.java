package com.example.webservice;


import com.example.webservice.model.CustomUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    private final String JWT_SECRET =  "automate_tagging_model";
    private final long JWT_EXPIRATION = 30L*1000L*60L*24L*60L;

    public  String generateToken (CustomUserDetails userDetails){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject((userDetails.getUser().getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String getUserIdFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();}
    public boolean validateToken(String authToken){
        try{
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex){
            log.error("Invalid JWT Token");
        }
        catch (ExpiredJwtException ex){
            log.error("Expired JWT Exception");
        }
        catch (UnsupportedJwtException ex){
            log.error("UnsupportedJWTException JWT Token");
        }
        catch (IllegalArgumentException ex){
            log.error("JWT claims string is empty.");
        }
        return false;
    }

}
