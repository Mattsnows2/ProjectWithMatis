package com.myProject.myProject.security;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.node.BooleanNode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.bytebuddy.implementation.Implementation.Context.ExtractableView;


@Component
public class JwtUtil {

    private String SECRET_KEY="secret";

    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public java.util.Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);

    }

    public <T> T extractClaim(String token,Function<Claims, T >  claimsResolver){
        final Claims claims =extractAllCLaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllCLaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new java.util.Date());
    }

    public String generateToken(UserDetails userDeails){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims, userDeails.getUsername());
    }

    private String createToken(Map<String,Object> claims,String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new java.util.Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean valiateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }
    
}
