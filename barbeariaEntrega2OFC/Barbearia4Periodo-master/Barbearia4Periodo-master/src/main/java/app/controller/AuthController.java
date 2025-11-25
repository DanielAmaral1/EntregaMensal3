package app.controller;

import app.entity.Usuario;
import app.security.JwtUtil;
import app.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    @Autowired
    public AuthController(JwtUtil jwtUtil, UsuarioService usuarioService) {
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        if (!usuarioService.validarCredenciais(request.username(), request.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Usuário ou senha inválidos."));
        }

        Usuario usuario = usuarioService.findByUsername(request.username()).orElseThrow();
        List<String> roles = List.of(usuario.getRole());
        
        String token = jwtUtil.generateToken(usuario.getUsername(), roles);
        long expiresIn = jwtUtil.getExpirationInSeconds();
        Instant expiresAt = Instant.now().plusSeconds(expiresIn);

        LoginResponse response = new LoginResponse(
                usuario.getUsername(),
                roles,
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

