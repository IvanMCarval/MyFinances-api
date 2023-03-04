package com.ivanm.myfinances.service.impl;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
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
  private PasswordEncoder encoder;

  public UsuarioServiceImpl(UsuarioRepository repository, PasswordEncoder encoder) {
    super();
    this.repository = repository;
    this.encoder = encoder;
  }

  @Override
  public Usuario autenticar(String email, String senha) {
    Optional<Usuario> usuario = repository.findByEmail(email);

    boolean senhasBatem = encoder.matches(senha, usuario.get().getSenha());

    if (!usuario.isPresent() || !senhasBatem) {
      throw new ErroAutenticacao("Erro na autenticação de usuário.");
    }

    return usuario.get();
  }

  private void CriptografarSenha(Usuario usuario) {
    String senha = usuario.getSenha();
    String senhaCript = encoder.encode(senha);
    usuario.setSenha(senhaCript);
  }

  @Override
  @Transactional
  public Usuario salvarUsuario(Usuario usuario) {
    validarEmail(usuario.getEmail());
    CriptografarSenha(usuario);
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
