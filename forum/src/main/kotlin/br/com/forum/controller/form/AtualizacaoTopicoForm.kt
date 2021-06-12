package br.com.forum.controller.form

import br.com.forum.model.Topico
import br.com.forum.repository.CursoRepository
import br.com.forum.repository.TopicoRepository
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class AtualizacaoTopicoForm(
    @field:NotNull @field:NotEmpty @field:Length(min = 5) val titulo: String,
    @field:NotNull @field:NotEmpty @field:Length(min = 10) val mensagem: String,
) {

    fun atualizar(id: Long, topicoRepository: TopicoRepository): Topico {
        val topico = topicoRepository.getById(id)
        topico.titulo = this.titulo
        topico.mensagem = this.mensagem
        return topico
    }

}
