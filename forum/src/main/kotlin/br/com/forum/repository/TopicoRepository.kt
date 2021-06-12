package br.com.forum.repository

import br.com.forum.model.Topico
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface TopicoRepository : JpaRepository<Topico, Long> {

    abstract fun findByCursoNome(nomeCurso: String?, paginacao: Pageable): Page<Topico>

}