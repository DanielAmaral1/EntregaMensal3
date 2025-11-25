package app.service;

import app.entity.Usuario;
import app.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Value("${master.password}")
    private String masterPassword;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void criarUsuarioPadrao() {
        if (!usuarioRepository.existsByUsername("Master")) {
            Usuario usuario = new Usuario();
            usuario.setUsername("Master");
            usuario.setPassword(passwordEncoder.encode(masterPassword));
            usuario.setRole("MASTER");
            usuarioRepository.save(usuario);
        }
    }

    public boolean validarCredenciais(String username, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        return usuario.isPresent() && passwordEncoder.matches(password, usuario.get().getPassword());
    }

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}