package com.gateway.filter;

import com.adminUtil.commonUtil.JwtUtil;
import com.adminUtil.commonUtil.ResponseBean;
import com.adminUtil.redis.RedisKey;
import com.alibaba.fastjson.JSON;
import com.gateway.entity.WhiteList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @description
 * @author luojinhua
 * @date 2021/5/11 15:40
 * @param
 * @return
 */
@Component
public class AuthorizeGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizeGatewayFilterFactory.Config> {

    @Autowired
    private RedissonClient redissonClient;

    public final static String AUTHORIZE_TOKEN = "token";

    public AuthorizeGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("enabled");
    }

    /**
     * 先查看缓存是否含有whiteList
     * 如果没有则查询WhiteList枚举是否含有值
     * 如果没有值则直接通过
     * 如果有则写入缓存，并且进行筛选是否通过
     *
     * 因为使用的缓存是set，所以无需担心并发情况的重复写入
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(AuthorizeGatewayFilterFactory.Config config) {
        return (exchange, chain) -> {
            if (!config.isEnabled()) {
                return chain.filter(exchange);
            }
            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();
            String token = headers.getFirst(AUTHORIZE_TOKEN);
            String path = request.getURI().getPath();
            redissonClient.getConfig();
            if (redissonClient.getSet(RedisKey.set_white_list.getValue()).contains(path)){
                return chain.filter(exchange);//白名单通过
            }
            if (token == null) {
                ServerHttpResponse response = exchange.getResponse();
                byte[] data = JSON.toJSONString(ResponseBean.fail(ResponseBean.Code.badRequest, "请求头无token")).getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(data);
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                return response.writeWith(Mono.just(buffer));
            }

            String username = JwtUtil.getUsername(token);

            if (username == null){
                ServerHttpResponse response = exchange.getResponse();
                byte[] data = JSON.toJSONString(ResponseBean.fail(ResponseBean.Code.unAccept, "token解析失败")).getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(data);
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                return response.writeWith(Mono.just(buffer));
            }

            Object object = redissonClient.getBucket(RedisKey.token.getValue() + username).get();

            if (object == null || !object.toString().equals(token)){
                ServerHttpResponse response = exchange.getResponse();
                byte[] data = JSON.toJSONString(ResponseBean.fail(ResponseBean.Code.unauthorized, "此token已过期")).getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(data);
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                return response.writeWith(Mono.just(buffer));
            }

            return chain.filter(exchange);//校验通过
        };
    }

    public static class Config {
        // 控制是否开启认证
        private boolean enabled;

        public Config() {}

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}