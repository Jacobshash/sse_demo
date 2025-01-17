package com.loltoulan.sse_demo.controller;

import com.loltoulan.sse_demo.service.SseRedisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SseRedisController {


    private SseEmitter emitter;

    private final SseRedisService sseRedisService;

    @GetMapping(path = "/sse/sse-emitter/redis/{userId}", produces = "text/event-stream")
    public SseEmitter getSseEmitterSseStreamByRedis(@PathVariable("userId") String userId) {
        this.emitter = new SseEmitter(-1L);
        try {
            sseRedisService.sendDataToClient(emitter, userId);
        } catch (Exception ex) {
            log.error("Error sending data to client", ex);
        }
        return emitter;
    }

    @GetMapping("/sse/sse-emitter/redis/close/{userId}")
    public ResponseEntity<Void> close(@PathVariable("userId") String userId) {
        try {
            if (Objects.isNull(this.emitter)) {
                log.info("emitter is null");
                return ResponseEntity.ok().build();
            }
            // emitter.send("close connection");
            this.emitter.complete();
            this.emitter = null;
            sseRedisService.clearRedisByUserId(userId);
        } catch (Exception e) {
            log.error("Error closing emitter", e);
            this.emitter.completeWithError(e);
            this.emitter = null;
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sse/sse-emitter/mock/{userId}")
    public ResponseEntity<Void> mockSseData(@PathVariable("userId") String userId, @RequestParam("data") String data) {
        try {
            sseRedisService.mockSseData(userId, this.emitter, data);
        } catch (IOException e) {
            log.error("Error sending data to client", e);
        }
        return ResponseEntity.ok().build();
    }

}