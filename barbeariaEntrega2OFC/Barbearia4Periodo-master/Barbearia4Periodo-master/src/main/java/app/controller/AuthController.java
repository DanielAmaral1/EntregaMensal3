package app.controller;

import app.security.JwtUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final String DEFAULT_USERNAME = "Master";
    private static final String DEFAULT_PASSWORD = "1234";
    private static final List<String> DEFAULT_ROLES = List.of("MASTER");

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        if (!DEFAULT_USERNAME.equals(request.username()) || !DEFAULT_PASSWORD.equals(request.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Usuário ou senha inválidos."));
        }

        String token = jwtUtil.generateToken(DEFAULT_USERNAME, DEFAULT_ROLES);
        long expiresIn = jwtUtil.getExpirationInSeconds();
        Instant expiresAt = Instant.now().plusSeconds(expiresIn);

        LoginResponse response = new LoginResponse(
                DEFAULT_USERNAME,
                DEFAULT_ROLES,
                token,
                expiresIn,
                expiresAt.toString()
        );

        return ResponseEntity.ok(response);
    }

    public record LoginRequest(
            @NotBlank(message = "Usuário é obrigatório.") String username,
            @NotBlank(message = "Senha é obrigatória.") String password
    ) {
    }

    public record LoginResponse(
            String username,
            List<String> roles,
            String token,
            long expiresIn,
            String expiresAt
    ) {
    }
}

