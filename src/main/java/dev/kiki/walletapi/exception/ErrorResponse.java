package dev.kiki.walletapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private Map<String, String> errors;

    public ErrorResponse(Instant timestamp, int status, String error, String message) {
        this(timestamp, status, error, message, null);
    }
}
