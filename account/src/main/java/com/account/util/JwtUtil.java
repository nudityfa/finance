package com.account.util;

import com.account.entity.Account;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.log4j.Log4j2;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
  * token校验工具类
  * @author luojinhua
  * @date 17:58 2020/2/19
  * @param
  * @return
  */
@Log4j2
public class JwtUtil {

    private static final long EXPIRE_TIME =3 * 24 * 60 * 60 * 1000;

    private static final String web = "web";

    private static final String mobile = "mobile";

    public static String sign(Account account) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(account.getPassword());
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return JWT.create()
                .withClaim("username", account.getUsername())
                .withClaim("uuid",account.getId())
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