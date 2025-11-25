package app.controller;

import app.entity.Usuario;
import app.entity.Cliente;
import app.security.JwtUtil;
import app.service.UsuarioService;
import app.repository.ClienteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(JwtUtil jwtUtil, UsuarioService usuarioService, 
                         ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        // Primeiro tenta login como admin (Master)
        if (usuarioService.validarCredenciais(request.username(), request.password())) {
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
                    expiresAt.toString(),
                    null
            );
            return ResponseEntity.ok(response);
        }
        
        // Se não for admin, tenta login como cliente (usando email)
        Optional<Cliente> clienteOpt = clienteRepository.findByEmail(request.username());
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            if (cliente.getPassword() != null && passwordEncoder.matches(request.password(), cliente.getPassword())) {
                List<String> roles = List.of("CLIENTE");
                
                String token = jwtUtil.generateToken(cliente.getEmail(), roles);
                long expiresIn = jwtUtil.getExpirationInSeconds();
                Instant expiresAt = Instant.now().plusSeconds(expiresIn);

                LoginResponse response = new LoginResponse(
                        cliente.getNome(),
                        roles,
                        token,
                        expiresIn,
                        expiresAt.toString(),
                        cliente.getId_cliente()
                );
                return ResponseEntity.ok(response);
            }
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Email ou senha inválidos."));
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        // Verificar se email já existe
        if (clienteRepository.existsByEmail(request.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Email já cadastrado."));
        }
        
        // Criar novo cliente
        Cliente cliente = new Cliente();
        cliente.setNome(request.nome());
        cliente.setCelular(request.celular());
        cliente.setEmail(request.email());
        cliente.setPassword(passwordEncoder.encode(request.password()));
        
        clienteRepository.save(cliente);
        
        return ResponseEntity.ok(Map.of("message", "Conta criada com sucesso!"));
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
            String expiresAt,
            Long clienteId
    ) {
    }
    
    public record RegisterRequest(
            @NotBlank(message = "Nome é obrigatório.") String nome,
            @NotBlank(message = "Celular é obrigatório.") String celular,
            @NotBlank(message = "Email é obrigatório.") String email,
            @NotBlank(message = "Senha é obrigatória.") String password
    ) {
    }
}

