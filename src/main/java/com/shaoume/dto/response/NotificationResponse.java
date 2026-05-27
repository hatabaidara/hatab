package com.shaoume.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationResponse {
    private Long id;
    private String title;
    private String message;
    private String type;
    private boolean read;
    private String actionUrl;
    private LocalDateTime createdAt;
}
