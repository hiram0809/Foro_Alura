package com.alura.forokev.repository; // Paquete corregido

import com.alura.forokev.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    // AÑADE ESTE MÉTODO CRÍTICO
    boolean existsByEmail(String email);
}