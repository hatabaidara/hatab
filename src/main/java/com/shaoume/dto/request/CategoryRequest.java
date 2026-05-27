package com.shaoume.dto.request;
import jakarta.validation.constraints.*;
import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CategoryRequest {
    @NotBlank @Size(max=100) private String name;
    @Size(max=500) private String description;
    private String imageUrl;
    private Long parentId;
}
