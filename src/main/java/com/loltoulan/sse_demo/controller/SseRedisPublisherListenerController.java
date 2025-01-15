package com.loltoulan.sse_demo.controller;

import com.loltoulan.sse_demo.config.SseMapUtil;
import com.loltoulan.sse_demo.service.SseRedisPublisherListenerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SseRedisPublisherListenerController {

    private final SseRedisPublisherListenerService sseRedisPublisherListenerService;

    @GetMapping(path = "/sse/sse-emitter/pub-lis/{userId}", produces = "text/event-stream")
    public SseEmitter getSseEmitterSseStreamByRedis(@PathVariable("userId") String userId) {
        SseEmitter emitter = new SseEmitter(-1L);
        SseMapUtil.setSseEmitter(userId, emitter);
        try {
            sseRedisPublisherListenerService.sendDataToClient(emitter, userId);
        } catch (Exception ex) {
            log.error("Error sending data to client", ex);
        }
        return emitter;
    }

    @GetMapping("/sse/sse-emitter/pub-lis/close/{userId}")
    public ResponseEntity<Void> close(@PathVariable("userId") String userId) {
        SseEmitter sseEmitter = SseMapUtil.getSseEmitter(userId);
        try {
            sseEmitter.send("sse-emitter complete");
            sseEmitter.complete();
        } catch (Exception e) {
            log.error("Error closing emitter", e);
            sseEmitter.completeWithError(e);
            SseMapUtil.removeSseEmitter(userId);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sse/sse-emitter/pub-lis/mock/{userId}")
    public ResponseEntity<Void> mockSseData(@PathVariable("userId") String userId) {
        try {
            sseRedisPublisherListenerService.mockSseData(userId);
        } catch (Exception e) {
            log.error("Error sending data to client", e);
        }
        return ResponseEntity.ok().build();
    }

}
