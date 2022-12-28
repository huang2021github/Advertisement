package com.advertisement.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.druid.util.StringUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class JwtUtils {

    //过期时间  七天
    public static final long EXPIRE_TIME = 72 * 60 * 60 *1000;

    public String header = "auth";

    public String sign(Map<String, Object> map, String secret, int expireTime){

        try {
            // 设置过期时间
            DateTime expireDate = DateUtil.offset(new Date(), DateField.SECOND,expireTime);
            // 私钥加密算法
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 设置头部信息
            Map<String,Object> header = new HashMap<>(2);
            header.put("typ", "jwt");
            // 返回token字符串
            JWTCreator.Builder builder = JWT.create()
                    .withHeader(header)
                    .withIssuedAt(new Date()) //发证时间
                    .withExpiresAt(expireDate);//过期时间

            map.entrySet().forEach(entry ->{
                if (entry.getValue() instanceof Integer) {
                    builder.withClaim(entry.getKey(), (Integer) entry.getValue());
                } else if (entry.getValue() instanceof Long) {
                    builder.withClaim(entry.getKey(), (Long) entry.getValue());
                } else if (entry.getValue() instanceof Boolean) {

                    builder.withClaim(entry.getKey(), (Boolean) entry.getValue());
                } else if (entry.getValue() instanceof String) {
                    builder.withClaim(entry.getKey(), String.valueOf(entry.getValue()));
                } else if (entry.getValue() instanceof Double) {
                    builder.withClaim(entry.getKey(), (Double) entry.getValue());
                } else if (entry.getValue() instanceof Date) {
                    builder.withClaim(entry.getKey(), (Date) entry.getValue());
                }
            });
            return builder.sign(algorithm);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检验token是否正确
     *
     * @param **token**
     * @return
     */
    public  boolean verify(String token,String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取用户自定义Claim集合
     *
     * @param token
     * @return
     */
    public  Map<String, Claim> getClaims(String token, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        Map<String, Claim> jwt = verifier.verify(token).getClaims();
        return jwt;
    }

    /**
     * 获取过期时间
     *
     * @param token
     * @return
     */
    public  Date getExpiresAt(String token,String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm).build().verify(token).getExpiresAt();
    }

    /**
     * 获取jwt发布时间
     */
    public  Date getIssuedAt(String token,String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm).build().verify(token).getIssuedAt();
    }

    /**
     * 验证token是否失效
     *
     * @param token
     * @return true:过期   false:没过期
     */
    public  boolean isExpired(String token,String secret) {
        try {
            final Date expiration = getExpiresAt(token,secret);
            return expiration.before(new Date());
        } catch (TokenExpiredException e) {
            // e.printStackTrace();
            return true;
        }

    }

    /**
     * 直接Base64解密获取header内容
     *
     * @param token
     * @return
     */
    public  String getHeaderByBase64(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        } else {
            byte[] header_byte = Base64.getDecoder().decode(token.split("\\.")[0]);
            String header = new String(header_byte);
            return header;
        }

    }

    /**
     * 直接Base64解密获取payload内容
     *
     * @param token
     * @return
     */
    public  String getPayloadByBase64(String token) {

        if (StringUtils.isEmpty(token)) {
            return null;
        } else {
            byte[] payload_byte = Base64.getDecoder().decode(token.split("\\.")[1]);
            String payload = new String(payload_byte);
            return payload;
        }

    }

}
