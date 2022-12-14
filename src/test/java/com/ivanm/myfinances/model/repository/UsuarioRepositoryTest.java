package com.ivanm.myfinances.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.ivanm.myfinances.model.entity.Usuario;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {

  @Autowired
  UsuarioRepository repository;

  @Autowired
  TestEntityManager entityManager;

  @Test
  public void deveVerificarAExistenciaDeUmEmail() {
    // cenario
    Usuario usuario = criarUsuario();
    entityManager.persist(usuario);

    // ação/execução
    boolean result = repository.existsByEmail("usuario@email.com");

    // verificação
    Assertions.assertThat(result).isTrue();
  }

  @Test
  public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail() {
    // cenario

    // ação
    boolean result = repository.existsByEmail("usuario@email.com");

    // verificação
    Assertions.assertThat(result).isFalse();
  }

  @Test
  public void devePersistirUmUsuarioNaBaseDeDado() {
    // cenario
    Usuario usuario = criarUsuario();

    // ação
    Usuario usuarioSalvo = repository.save(usuario);

    Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
  }

  @Test
  public void deveBuscarUmUsuarioPorEmail() {
    // cenario
    Usuario usuario = criarUsuario();
    entityManager.persist(usuario);

    //verificação
    Optional<Usuario> result = repository.findByEmail("usuario@email.com");

    Assertions.assertThat(result.isPresent()).isTrue();
  }

  @Test
  public void deveRetornarVazioAoBuscarUsuarioPorEmailQuandoNaoExisteNaBase() {
    // cenario

    //verificação
    Optional<Usuario> result = repository.findByEmail("usuario@email.com");

    Assertions.assertThat(result.isPresent()).isFalse();
  }

  public static Usuario criarUsuario() {
    return Usuario.builder().nome("usuario").email("usuario@email.com").senha("senha").build();
  }
}
