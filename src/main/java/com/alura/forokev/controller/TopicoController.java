package com.alura.forokev.controller;

import com.alura.forokev.dto.TopicoRequest;
import com.alura.forokev.model.Topico;
import com.alura.forokev.model.Usuario;
import com.alura.forokev.repository.TopicoRepository;
import com.alura.forokev.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<Topico>> listarTopicos() {
        return ResponseEntity.ok(topicoRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Topico> crearTopico(
            @RequestBody @Valid TopicoRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Usuario autor = usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Topico topico = new Topico();
        topico.setTitulo(request.titulo());
        topico.setMensaje(request.mensaje());
        topico.setAutor(autor);

        Topico topicoGuardado = topicoRepository.save(topico);
        return ResponseEntity.status(HttpStatus.CREATED).body(topicoGuardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));

        // Verificar que el usuario sea el autor
        if (!topico.getAutor().getEmail().equals(userDetails.getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        topicoRepository.delete(topico);
        return ResponseEntity.noContent().build();
    }
}