package br.com.forum.repository

import br.com.forum.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UsuarioReposirory: JpaRepository<Usuario, Long> {

    abstract fun findByEmail(email: String): Optional<Usuario>

}