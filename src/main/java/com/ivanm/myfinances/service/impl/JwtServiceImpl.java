package com.ivanm.myfinances.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ivanm.myfinances.model.entity.Usuario;
import com.ivanm.myfinances.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService {

  @Value("${jwt.expiracao}")
  private String expiracao;
  @Value("${jst.chave-assinatura}")
  private String chaveAssinatura;

  @Override
  public String gerarToken(Usuario usuario) {
    long expLong = Long.valueOf(expiracao);
    LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expLong);
    Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
    Date data = Date.from(instant);

    String horaexpiracaoToken = dataHoraExpiracao.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));

    String token = Jwts.builder().setExpiration(data).setSubject(usuario.getEmail())
        .claim("userid", usuario.getId())
        .claim("nome", usuario.getNome())
        .claim("horaExpiracao", horaexpiracaoToken)
        .signWith(SignatureAlgorithm.HS512, chaveAssinatura).compact();

    return token;
  }

  @Override
  public Claims obterClaims(String token) throws ExpiredJwtException {
    return Jwts
        .parser()
        .setSigningKey(chaveAssinatura)
        .parseClaimsJws(token)
        .getBody();
  }

  @Override
  public boolean isTokenValido(String token) {
    try {
      Claims claims = obterClaims(token);
      Date dataEx = claims.getExpiration();

      LocalDateTime dataExpiracao = dataEx.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

      return !LocalDateTime.now().isAfter(dataExpiracao);

    } catch (ExpiredJwtException e) {
      return false;
    }
  }

  @Override
  public String obterLoginUsuario(String token) {
    Claims claims = obterClaims(token);
    return claims.getSubject();
  }

}
