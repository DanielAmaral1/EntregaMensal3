package app.controller;

import app.entity.Usuario;
import app.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<String> criarUsuario(@RequestBody Usuario usuario) {
        // Criptografa a senha antes de salvar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        // Aqui você adicionaria a lógica para salvar no repository
        return ResponseEntity.ok("Usuário criado com senha criptografada!");
    }
}