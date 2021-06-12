package br.com.forum.controller.form

import br.com.forum.model.Topico
import br.com.forum.repository.CursoRepository
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class TopicoForm(
    @field:NotNull @field:NotEmpty @field:Length(min = 5) val titulo: String,
    @field:NotNull @field:NotEmpty @field:Length(min = 10) val mensagem: String,
    @field:NotNull @field:NotEmpty val nomeCurso: String
){

    fun converter(cursoRepository: CursoRepository): Topico {
        return Topico(titulo = titulo, mensagem = mensagem, curso = cursoRepository.findByNome(this.nomeCurso))
    }

}
