package com.ivanm.myfinances.service;

//import java.util.Optional;

import org.junit.jupiter.api.Assertions;
//import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

//import com.ivanm.myfinances.exception.ErroAutenticacao;
import com.ivanm.myfinances.exception.RegraNegocioException;
//import com.ivanm.myfinances.exception.RegraNegocioException;
import com.ivanm.myfinances.model.entity.Usuario;
import com.ivanm.myfinances.model.repository.UsuarioRepository;
import com.ivanm.myfinances.service.impl.UsuarioServiceImpl;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

  @SpyBean
  UsuarioServiceImpl service;

  @MockBean
  UsuarioRepository repository;

  /*
   * @Test
   * public void deveSalvarUmUsuario() {
   * // cenario
   * Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
   * Usuario usuario =
   * Usuario.builder().id(1l).nome("nome").email("email@email.com").senha("senha")
   * .build();
   * Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario)
   * ;
   * 
   * // ação
   * Usuario usuarioSalvo = service.salvarUsuario(new Usuario());
   * 
   * // verificação
   * Assertions.assertThat(usuarioSalvo).isNotNull();
   * Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
   * Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
   * Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
   * Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
   * }
   */

  @Test
  public void naoDeveSalvarUsuarioCOmEmailJaCadastrado() {
    Assertions.assertThrows(RegraNegocioException.class, () -> {
      // cenario
      String email = "email@email.com";
      Usuario usuario = Usuario.builder().email(email).build();
      Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);

      // ação
      service.salvarUsuario(usuario);

      // verificação
      Mockito.verify(repository, Mockito.never()).save(usuario);
    });
  }

  /*
   * @Test
   * public void deveAutenticarUmUsuarioComSucesso(){
   * // cenario
   * String email = "email@email.com";
   * String senha = "senha";
   * 
   * Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
   * Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
   * 
   * // ação
   * Usuario result = service.autenticar(email, senha);
   * 
   * // verificação
   * Assertions.assertThat(result).isNotNull();
   * }
   */

  /*
   * @Test
   * public void
   * deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
   * // cenario
   * Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional
   * .empty());
   * 
   * // ação
   * Throwable exception = Assertions.catchThrowable(() ->
   * service.autenticar("email@email.com", "senha"));
   * 
   * // verificação
   * Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class)
   * .hasMessage("Usuario não encontrado para o email informado");
   * }
   * 
   * @Test
   * public void deveLancarErroQuandoSenhaNaoAutenticar() {
   * // cenario
   * String senha = "senha";
   * Usuario usuario =
   * Usuario.builder().email("email@email.com").senha(senha).build();
   * Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional
   * .of(usuario));
   * 
   * // ação
   * Throwable exception = Assertions.catchThrowable(() ->
   * service.autenticar("email@email.com", "123"));
   * 
   * // verificação
   * Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).
   * hasMessage("Senha invalida");
   * }
   */

  /*
   * @Test
   * public void deveValidarEmail() {
   * Assertions.assertDoesNotThrow(() -> {
   * // cenario
   * Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false)
   * ;
   * 
   * // ação
   * service.validarEmail("email@email.com");
   * });
   * }
   * 
   * @Test
   * public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
   * Assertions.assertThrows(RegraNegocioException.class, () -> {
   * // cenario
   * Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
   * 
   * // ação
   * service.validarEmail("email@email.com");
   * });
   * }
   */
}
