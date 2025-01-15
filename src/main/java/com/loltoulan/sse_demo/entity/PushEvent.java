package com.loltoulan.sse_demo.entity;

import lombok.Getter;
import lombok.Setter;

import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class PushEvent extends ApplicationEvent {

    private String data;

    public PushEvent(Object source, String data) {
        super(source);
        this.data = data;
    }

}

