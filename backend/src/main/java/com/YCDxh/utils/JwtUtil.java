package com.YCDxh.utils;

import com.YCDxh.security.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author YCDxhg
 */

public class JwtUtil {


    private static String secretKeyString = "gUM+BALaPCW3O1VDxasOL26CNITirSV11zoWFeqT1mqfp9VuX3tFX2J60nGZDslIdKHfNUHPMJuR+PJZ+B3M2w=="; // 从配置文件读取 Base64 编码的秘钥
    private static Key secretKey = new SecretKeySpec(Base64.getDecoder().decode(secretKeyString), "HmacSHA512");

    public static final long EXPIRATION_TIME = 1000 * 60 * 60 * 12; // 12小时（根据需求调整）

    // 获取用户名
    public static String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // 获取过期时间
    public static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // 通用声明解析方法
    private static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // 解析所有声明（关键修改：使用 SecretKey）
    private static Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey) // 使用 SecretKey 对象
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // 检查是否过期
    private static boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // 生成令牌（关键修改：签名方式调整）
    public static String generateToken(MyUserDetails userDetails) {
//        System.out.println("secretKey: " + secretKey);
//        System.out.println("secretKeyString: " + secretKeyString);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities()); // 增加角色信息
        claims.put("userId", userDetails.getUserId()); // 增加用户ID

        return doGenerateToken(claims, userDetails.getUsername());
    }

    // 内部生成方法（关键修改：移除算法参数）
    private static String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey) // 直接使用 SecretKey 签名
                .compact();
    }

    // 验证令牌有效性
    public static boolean validateToken(String token, MyUserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // 解析 JWT 令牌并返回 Claims
    public static Claims parseJwtClaims(String token) {
        return getAllClaimsFromToken(token);
    }

}
