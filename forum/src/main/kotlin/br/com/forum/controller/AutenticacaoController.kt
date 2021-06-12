package br.com.forum.controller

import br.com.forum.config.security.TokenService
import br.com.forum.controller.dto.TokenDto
import br.com.forum.controller.form.LoginForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.naming.AuthenticationException
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AutenticacaoController {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var tokenService: TokenService

    @PostMapping
    fun autenticar(@RequestBody @Valid loginForm: LoginForm): ResponseEntity<TokenDto>{
        return try {
            val authentication = authenticationManager.authenticate(loginForm.converter())
            val token = tokenService.gerarToken(authentication)
            ResponseEntity.ok(TokenDto(token, "Bearer"))
        }catch (e: AuthenticationException){
            ResponseEntity.badRequest().build()
        }
    }
}