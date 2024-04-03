package net.breezeware.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@AllArgsConstructor
public class ErrorDetail {
    @Schema(description = "Brief error message", example = "NOT_FOUND")
    private String message;

    @Schema(description = "Cause for the error", example = "Resource not found for id")
    private String detail;
}
