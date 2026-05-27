package com.shaoume.dto.response;
import lombok.*;
import java.time.LocalDateTime;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FavoriteResponse {
    private Long id;
    private ProductResponse product;
    private LocalDateTime createdAt;
}
