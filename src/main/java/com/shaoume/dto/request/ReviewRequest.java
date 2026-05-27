package com.shaoume.dto.request;
import jakarta.validation.constraints.*;
import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReviewRequest {
    @NotNull @Min(1) @Max(5) private Integer rating;
    @Size(max=200) private String title;
    private String comment;
}
