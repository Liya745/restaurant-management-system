package com.itheima;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTTest {


    /**
     * 测试生成jwt令牌
     */

    @Test
    public void testGenerateJwt(){
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("id",1);
        dataMap.put("username","admin");
        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, "aXRoZWltYQ==")//指定签名算法和密钥
                .addClaims(dataMap) //添加自定义信息数据
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) //设置过期时间
                .compact(); //生成令牌
        System.out.println(jwt);
    }

    @Test
    public void testParseJWT(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTc2OTY1OTAyOX0.AcyzCsLFWxliUMDQbjvJ27OHiCaQV9b_fqpjIoRjw5k";

        Claims claims = Jwts.parser().setSigningKey("aXRoZWltYQ==") //指定密钥
                .parseClaimsJws(token) //解析令牌
                .getBody(); //获取自定义信息

        System.out.println(claims);
    }
}
