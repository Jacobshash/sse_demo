package com.loltoulan.sse_demo.publisher;

import com.loltoulan.sse_demo.entity.PushEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class BizEventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        log.info("Setting application event publisher");
        this.applicationEventPublisher = applicationEventPublisher;
    }


    public void publishEvent(String data) {
        log.info("Publishing event");
        applicationEventPublisher.publishEvent(new PushEvent(this, data));
    }
}
