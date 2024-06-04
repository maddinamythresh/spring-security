package com.example.spring.Security_demo.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;


@Service
public class JwtService {

    private String secretKey;

    public String generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keygen=KeyGenerator.getInstance("HmacSHA256");
        SecretKey secretkey=keygen.generateKey();
        System.out.println(secretkey+"secret");

        return Base64.getEncoder().encodeToString(secretkey.getEncoded());

    }

    public JwtService() throws NoSuchAlgorithmException {
        this.secretKey=generateSecretKey();
    }


    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().addClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*5))
                .signWith(getKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getKey() {
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        System.out.println(keyBytes);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractusername(String token) {
        System.out.println("JWT Token: " + token);

        System.out.println(token);
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        System.out.println(token+"is good");
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractusername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}