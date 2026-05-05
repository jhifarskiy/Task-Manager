package jhifarskiy.homework.dto;

import java.time.LocalDateTime;

public class ErrorResponseDto {
    private String message;
    private String detailMessage;
    private LocalDateTime errorTime;

    public ErrorResponseDto() {
    }

    public ErrorResponseDto(String message, String detailMessage, LocalDateTime errorTime) {
        this.message = message;
        this.detailMessage = detailMessage;
        this.errorTime = errorTime;
    }

    public String getMessage() {
        return message;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public LocalDateTime getErrorTime() {
        return errorTime;
    }
}
