package com.github.rafaelmdb;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@Slf4j
public class JwtService {
   /*@Value("security.jwt.expiracao")
    private String expiracao;

    @Value("security.jwt.chave-assinatura")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario){
        long minutosExpiracao =Long.valueOf(expiracao);

        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(minutosExpiracao);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        return Jwts.builder()
                .setSubject(usuario.getLogin())
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();
    }*/
}
