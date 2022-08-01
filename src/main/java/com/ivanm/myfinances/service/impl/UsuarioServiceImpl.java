package com.ivanm.myfinances.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ivanm.myfinances.exception.ErroAutenticacao;
import com.ivanm.myfinances.exception.RegraNegocioException;
import com.ivanm.myfinances.model.entity.Usuario;
import com.ivanm.myfinances.model.repository.UsuarioRepository;
import com.ivanm.myfinances.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

  private UsuarioRepository repository;

  public UsuarioServiceImpl(UsuarioRepository repository) {
    super();
    this.repository = repository;
  }

  @Override
  public Usuario autenticar(String email, String senha) {
    Optional<Usuario> usuario = repository.findByEmail(email);

    if (!usuario.isPresent() || !usuario.get().getSenha().equals(senha)) {
      throw new ErroAutenticacao("Erro na autenticação de usuário.");
    }

    /*
     * if(!usuario.isPresent()){
     * throw new ErroAutenticacao("Usuario não encontrado para o email informado");
     * }
     * if(!usuario.get().getSenha().equals(senha)){
     * throw new ErroAutenticacao("Senha invalida");
     * }
     */

    return usuario.get();
  }

  @Override
  @Transactional
  public Usuario salvarUsuario(Usuario usuario) {
    validarEmail(usuario.getEmail());
    return repository.save(usuario);
  }

  @Override
  public void validarEmail(String email) {
    boolean existe = repository.existsByEmail(email);

    if (existe) {
      throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
    }
  }

  @Override
  public Optional<Usuario> obterPorId(Long id) {
    return repository.findById(id);
  }
}
