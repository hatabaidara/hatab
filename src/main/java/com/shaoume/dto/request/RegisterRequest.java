package com.shaoume.dto.request;
import jakarta.validation.constraints.*;
import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RegisterRequest {
    @NotBlank(message="Le prénom est obligatoire") @Size(min=2,max=100) private String firstName;
    @NotBlank(message="Le nom est obligatoire") @Size(min=2,max=100) private String lastName;
    @NotBlank(message="L'email est obligatoire") @Email private String email;
    @NotBlank(message="Le mot de passe est obligatoire") @Size(min=8) private String password;
    private String phone;
    private String role;
    private String shopName;
}
