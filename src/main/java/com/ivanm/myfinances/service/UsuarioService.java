package com.ivanm.myfinances.service;

import java.util.Optional;

import com.ivanm.myfinances.model.entity.Usuario;

public interface UsuarioService {
  Usuario autenticar(String email, String senha);

  Usuario salvarUsuario(Usuario usuario);

  void validarEmail(String email);

  Optional<Usuario> obterPorId(Long id);
}
