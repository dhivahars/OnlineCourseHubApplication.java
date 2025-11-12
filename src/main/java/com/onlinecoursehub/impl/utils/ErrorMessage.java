package com.onlinecoursehub.impl.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    private int statusCode=420;
    private String message;
    private boolean success;
}
