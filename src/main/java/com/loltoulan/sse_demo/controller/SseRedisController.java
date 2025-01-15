package com.loltoulan.sse_demo.controller;

import com.loltoulan.sse_demo.service.BizService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SseRedisController {


    private SseEmitter emitter;

    private final BizService bizService;

    @GetMapping("/sse/sse-emitter/redis/{userId}")
    public SseEmitter getSseEmitterSseStreamByRedis(@PathVariable("userId") String userId) {
        this.emitter = new SseEmitter();
        try {
            bizService.sendDataToClient(emitter, userId);
        } catch (Exception ex) {
            log.error("Error sending data to client", ex);
        }
        return emitter;
    }

    @GetMapping("/sse/sse-emitter/redis/close")
    public ResponseEntity<Void> close() {
        try {
            emitter.send("complete");
            this.emitter.complete();
        } catch (Exception e) {
            log.error("Error closing emitter", e);
            this.emitter.completeWithError(e);
            this.emitter = null;
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sse/sse-emitter/mock/{userId}")
    public ResponseEntity<Void> mockSseData(@PathVariable("userId") String userId) {
        try {
            bizService.mockSseData(userId,emitter);
        } catch (IOException e) {
            log.error("Error sending data to client", e);
        }
        return ResponseEntity.ok().build();
    }

}