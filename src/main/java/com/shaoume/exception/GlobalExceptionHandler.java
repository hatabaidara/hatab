package com.shaoume.exception;
import com.shaoume.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice @Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> notFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(404).body(ApiResponse.error(404,ex.getMessage()));
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Void>> badReq(BadRequestException ex) {
        return ResponseEntity.status(400).body(ApiResponse.error(400,ex.getMessage()));
    }
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<Void>> conflict(ConflictException ex) {
        return ResponseEntity.status(409).body(ApiResponse.error(409,ex.getMessage()));
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> badCreds(BadCredentialsException ex) {
        return ResponseEntity.status(401).body(ApiResponse.error(401,"Email ou mot de passe incorrect"));
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> denied(AccessDeniedException ex) {
        return ResponseEntity.status(403).body(ApiResponse.error(403,"Accès refusé"));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String,String>>> validation(MethodArgumentNotValidException ex) {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(e -> errors.put(((FieldError)e).getField(),e.getDefaultMessage()));
        return ResponseEntity.status(400).body(ApiResponse.<Map<String,String>>builder().success(false).status(400).message("Erreur de validation").data(errors).build());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> generic(Exception ex) {
        log.error("Error: {}",ex.getMessage(),ex);
        return ResponseEntity.status(500).body(ApiResponse.error(500,"Erreur interne"));
    }
}
