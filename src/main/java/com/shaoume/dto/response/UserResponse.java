package com.shaoume.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.shaoume.entity.enums.Role;
import lombok.*;
import java.time.LocalDateTime;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String country;
    private String postalCode;
    private String avatarUrl;
    private Role role;
    private boolean enabled;
    private LocalDateTime createdAt;
}
