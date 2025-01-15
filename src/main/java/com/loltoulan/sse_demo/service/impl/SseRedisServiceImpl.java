package com.loltoulan.sse_demo.service.impl;

import com.loltoulan.sse_demo.service.SseRedisService;
import com.loltoulan.sse_demo.util.RedisUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class SseRedisServiceImpl implements SseRedisService {

    private final RedisUtils redisUtils;
    private static final String prefix = "biz:funds:rate:";


    @Override
    public void sendDataToClient(SseEmitter emitter, String userId) throws IOException {
        String object = String.valueOf(redisUtils.get(prefix + userId));
        log.info("Sending data to client");
        if (emitter == null) {
            return;
        }
        emitter.send(Objects.requireNonNullElse(object, "No data"));
    }

    @Override
    public void mockSseData(String userId, SseEmitter emitter) throws IOException {
        redisUtils.set(prefix + userId, String.valueOf(Math.random() * 100));
        sendDataToClient(emitter, userId);
    }

}
