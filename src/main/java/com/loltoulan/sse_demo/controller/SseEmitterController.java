package com.loltoulan.sse_demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SseEmitterController {

    private final ExecutorService nonBlockingService = Executors.newCachedThreadPool();

    private SseEmitter emitter;
    @GetMapping("/sse/sse-emitter/create")
    public SseEmitter getSseEmitterSseStreamByRedis() {
        this.emitter = new SseEmitter();
        nonBlockingService.execute(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    emitter.send("Data: " + i);
                    Thread.sleep(1000);
                }
            } catch (Exception ex) {
                log.error("Error sending data to client", ex);
            }
        });
        return emitter;
    }

    @GetMapping("/sse/sse-emitter/close")
    public ResponseEntity<Void> close() {
        try {
            this.emitter.complete();
        } catch (Exception e) {
            log.error("Error closing emitter", e);
            this.emitter.completeWithError(e);
            this.emitter = null;
        }
        return ResponseEntity.ok().build();
    }

}
