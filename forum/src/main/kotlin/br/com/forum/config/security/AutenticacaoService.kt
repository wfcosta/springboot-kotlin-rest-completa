package br.com.forum.config.security

import br.com.forum.repository.UsuarioReposirory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AutenticacaoService: UserDetailsService {

    @Autowired
    lateinit var usuarioReposirory: UsuarioReposirory

    override fun loadUserByUsername(userName: String?): UserDetails {
        val usuario = usuarioReposirory.findByEmail(userName!!)
        if(usuario.isPresent){
            return usuario.get()
        }
        throw UsernameNotFoundException("Dados inválidos!")
    }

}