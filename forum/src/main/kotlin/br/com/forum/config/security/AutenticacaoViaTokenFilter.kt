package br.com.forum.config.security

import br.com.forum.repository.UsuarioReposirory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AutenticacaoViaTokenFilter(val tokenService: TokenService, val usuarioReposirory: UsuarioReposirory): OncePerRequestFilter() {

    override fun doFilterInternal(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, filterChain: FilterChain) {
        httpServletRequest.getHeader("Authorization")?.let {
            if(it.isNotEmpty() && it.startsWith("Bearer ")){
                val token = it.substring(7, it.length)
                if(tokenService.isTokenValido(token)){
                    autenticarCliente(token)
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }

    private fun autenticarCliente(token: String){
        val usuario = usuarioReposirory.getById(tokenService.getIdUsuario(token))
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(usuario, null, usuario.authorities)
        SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
    }

}