package com.yangfan.jwttest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;

/**
 * @author yangfan
 * @version 1.0
 * @description
 */
public class JwtTest {

    @Test
    public void createJwtTokenTest(){
        JwtBuilder builder = Jwts.builder()
                .setId("666").setSubject("行走在牛A的路上")
                .setIssuedAt(new Date())
                //添加自定义属性
                /*.claim("role", "admin")
                .claim("other", "other")*/
                .setExpiration(new Date(new Date().getTime() + 300000))
                .signWith(SignatureAlgorithm.HS256, "yangfan");
        String jwtToken = builder.compact();
        System.out.println(jwtToken);

        //eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLooYzotbDlnKjniZtB55qE6Lev5LiKIiwiaWF0IjoxNTk0MDMwODg3LCJleHAiOjE1OTQwMzExODd9.Ss-11xR1YDw9roSJ56S_e9GakNdHlU-3ByqjB7fNG2o
    }

    @Test
    public void parseToken(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLooYzotbDlnKjniZtB55qE6Lev5LiKIiwiaWF0IjoxNTk0MDMxMjkxLCJleHAiOjE1OTQwMzE1OTF9.ODcViHfVBFdA2grJYxsGTdQk7tp3_frlO_5e-Sbk7MA";
        Claims claims = Jwts.parser().setSigningKey("yangfan").parseClaimsJws(token).getBody();

        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println("创建时间" + claims.getIssuedAt());
        System.out.println("过期时间" + claims.getExpiration());
        //获取属性
        //System.out.println(claims.get("role"));


        //过期后会抛出异常：ExpiredJwtException
        //令牌存在问题会抛出异常：SignatureException
    }
}
