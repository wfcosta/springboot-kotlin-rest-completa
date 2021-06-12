package br.com.forum.repository

import br.com.forum.model.Curso
import org.springframework.data.jpa.repository.JpaRepository

interface CursoRepository: JpaRepository<Curso, Long> {
    abstract fun findByNome(nome: String): Curso
}