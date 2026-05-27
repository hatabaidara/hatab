package com.shaoume.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private int status;
    @Builder.Default private LocalDateTime timestamp = LocalDateTime.now();
    public static <T> ApiResponse<T> success(T data) { return ApiResponse.<T>builder().success(true).status(200).data(data).build(); }
    public static <T> ApiResponse<T> success(String msg, T data) { return ApiResponse.<T>builder().success(true).status(200).message(msg).data(data).build(); }
    public static <T> ApiResponse<T> created(String msg, T data) { return ApiResponse.<T>builder().success(true).status(201).message(msg).data(data).build(); }
    public static <T> ApiResponse<T> error(int status, String msg) { return ApiResponse.<T>builder().success(false).status(status).message(msg).build(); }
}
