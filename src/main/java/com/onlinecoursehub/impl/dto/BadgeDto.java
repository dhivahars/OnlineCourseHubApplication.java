package com.onlinecoursehub.impl.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BadgeDto {
    private String name;
    private LocalDateTime assignedDate;
}
