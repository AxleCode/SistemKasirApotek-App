package com.SistemInformasiApotek.UserService.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "2jppsRuS+PR3gxW2nC1fCK6VEJNE8W/Z3bfpBAVOGinUMexvwNbNsysTFalzeUqsdEo5AsMjSUPvwlIgkyZmI3syozhYrm4CVfdWsnMAHszzDsi9Sq7o1lpj6FWQLfNKJfbmtHjqY7l4ez30bO7atvQYClqhCCPoUr58pKmguwz6x5F0QNeNTSaAqPF0ih7IhSXtrzkZKMqMk1Lz6i9aibr0Hqr8aUxgyOJg3VYtG33xbZtAXDdngLqw+P27CHyKvEXBOPqqPpBVN8s2X3zyvPz0J2kpHeEU/RJqZDk4Vapb4UOSbPMaYXGihxeuH24FCJeHtdXwwG+XovzNehrqaK06SqBHfRO91S+9oC+Jx+4=";
    // Metode untuk mengekstrak username dari token JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    // Metode generik untuk mengekstrak klaim tertentu dari token JWT
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extactAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Token akan kadaluarsa dalam 24 jam
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // Menggunakan algoritma HS512 untuk mengenkripsi
                .compact();
    }

    // Metode untuk memeriksa apakah token JWT masih valid berdasarkan UserDetails
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    // Metode untuk mengekstrak waktu kadaluarsa dari token JWT
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    // Metode untuk mengekstrak semua klaim dari token JWT
    private Claims extactAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey()) // Menggunakan kunci rahasia untuk dekripsi token
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    // Metode untuk mendapatkan kunci rahasia dalam bentuk objek Key
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

