package com.alura.forokev.controller; // Paquete CORREGIDO (forokev)

import com.alura.forokev.dto.AuthResponse; // Paquetes corregidos
import com.alura.forokev.dto.LoginRequest;
import com.alura.forokev.model.Usuario;
import com.alura.forokev.repository.UsuarioRepository;
import com.alura.forokev.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtService jwtService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UsuarioRepository usuarioRepository;

    // SOLO UN MÉTODO REGISTER - ELIMINA EL DUPLICADO
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody LoginRequest request) {
        // Verificar si el email ya existe
        if (usuarioRepository.existsByEmail(request.email())) {
            return ResponseEntity.badRequest().build();
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail(request.email());
        nuevoUsuario.setPassword(passwordEncoder.encode(request.password()));
        usuarioRepository.save(nuevoUsuario);

        String token = jwtService.generateToken(nuevoUsuario);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    // AÑADE EL MÉTODO LOGIN QUE FALTA
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        Usuario user = (Usuario) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}