package com.loltoulan.sse_demo.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

public interface SseRedisService {

    void sendDataToClient(SseEmitter emitter, String userId) throws IOException;

    void mockSseData(String userId, SseEmitter emitter) throws IOException;
}
