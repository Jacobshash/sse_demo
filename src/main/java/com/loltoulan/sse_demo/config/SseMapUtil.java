package com.loltoulan.sse_demo.config;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author LOL_toulan
 */
public class SseMapUtil {
    private static final Map<String, SseEmitter> sseEmitterMap = new HashMap<>();

    public static SseEmitter getSseEmitter(String userId) {
        return sseEmitterMap.get(userId);
    }

    public static void setSseEmitter(String userId,SseEmitter sseEmitter) {
        sseEmitterMap.put(userId,sseEmitter);
    }

    public static void removeSseEmitter(String userId) {
        sseEmitterMap.remove(userId);
    }
}
