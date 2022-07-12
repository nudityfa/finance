package com.adminUtil.commonUtil;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
  * token校验工具类
  * @author luojinhua
  * @date 17:58 2020/2/19
  * @param
  * @return
  */
public class JwtUtil {


    public static final String AUTHORIZE_TOKEN = "token";

    public static final String DEFAULT_ACCOUNT = "admin";

    /**
     * 3天
     */
    private static final long EXPIRE_TIME =3 * 24 * 60 * 60 * 1000;

    public static String sign(String password,String username, String id) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(password);
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return JWT.create()
                .withClaim("username", username)
                .withClaim("uuid",id)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


}