package com.onlinecoursehub.impl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BadgeDto {
    private String name;
    private LocalDateTime assignedDate;
}
