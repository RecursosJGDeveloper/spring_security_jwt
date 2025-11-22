package security_config.demo_jwt.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // Clave en Base64 (32 bytes) usada para HMAC SHA-256
    private static final String SECRET_KAY = "VGhpc0lzQVRlc3RLZXlGb3JTdHJpbmdTZWN1cml0eTEyMzQ1Njc4";

    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(),user);
    }

    private String getToken(Map<String,Object> extraClaims, UserDetails user) {
        return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(user.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
        .signWith(getKay(),SignatureAlgorithm.HS256)
        .compact();
    }

    private Key getKay() {
        byte[] keyBytes=Decoders.BASE64.decode(SECRET_KAY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
