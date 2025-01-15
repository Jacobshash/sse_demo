package com.loltoulan.sse_demo.listener;

import com.loltoulan.sse_demo.config.SseMapUtil;
import com.loltoulan.sse_demo.entity.PushEvent;
import com.loltoulan.sse_demo.service.SseRedisPublisherListenerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class EventListenerBus {

    private final SseRedisPublisherListenerService sseRedisPublisherListenerService;
    @EventListener
    public void eventListener(PushEvent event) {
        log.info("{}监听到数据：{}", this.getClass().getSimpleName(), event.getData());
        SseEmitter sseEmitter = SseMapUtil.getSseEmitter(event.getData());
        try {
            sseRedisPublisherListenerService.sendDataToClient(sseEmitter, event.getData());
        } catch (IOException e) {
            log.error("Error sending data to client", e);
        }
    }


}
