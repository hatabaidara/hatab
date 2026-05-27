package com.shaoume.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private boolean active;
    private Long parentId;
    private String parentName;
    private List<CategoryResponse> subCategories;
    private int productCount;
    private LocalDateTime createdAt;
}
