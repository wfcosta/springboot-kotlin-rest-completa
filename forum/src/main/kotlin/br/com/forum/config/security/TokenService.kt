package br.com.forum.config.security

import br.com.forum.model.Usuario
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService {

    @Value("\${forum.jwt.expiration}")
    lateinit var expiration: String

    @Value("\${forum.jwt.secret}")
    lateinit var secret: String

    fun gerarToken(authentication: Authentication): String{
        return  Jwts.builder()
            .setIssuer("API do fórum")
            .setSubject((authentication.principal as Usuario).id.toString())
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + expiration.toLong()))
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    fun isTokenValido(token: String): Boolean{
        return try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token)
            true
        }catch (e: Exception){
            false
        }
    }

    fun getIdUsuario(token: String): Long{
        return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).body.subject.toLong()
    }

}