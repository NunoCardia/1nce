package com.once.demoproject.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class Error implements Serializable {

    private LocalDateTime timestamp;
    private String message;
}
