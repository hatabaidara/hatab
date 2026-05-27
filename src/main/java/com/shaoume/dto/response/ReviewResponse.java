package com.shaoume.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewResponse {
    private Long id;
    private Integer rating;
    private String title;
    private String comment;
    private boolean approved;
    private UserResponse user;
    private Long productId;
    private LocalDateTime createdAt;
}
