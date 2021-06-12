package br.com.forum.controller.form

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

data class LoginForm(
    val email: String,
    val senha: String
    ) {

    fun converter(): UsernamePasswordAuthenticationToken{
        return UsernamePasswordAuthenticationToken(this.email, this.senha)
    }

}
