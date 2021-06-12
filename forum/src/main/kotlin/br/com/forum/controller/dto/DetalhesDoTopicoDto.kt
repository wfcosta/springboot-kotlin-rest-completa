package br.com.forum.controller.dto

import br.com.forum.model.Resposta
import br.com.forum.model.StatusTopico
import br.com.forum.model.Topico
import java.time.LocalDateTime

data class DetalhesDoTopicoDto(
    val id: Long?,
    val titulo: String,
    val mensagem: String,
    val dataCriacao: LocalDateTime?,
    val nomeAutor: String?,
    val statusTopico: StatusTopico?,
    val respostas: List<RespostaDto>
){

    constructor(topico: Topico) :
            this(topico.id,
                topico.titulo,
                topico.mensagem,
                topico.dataCriacao,
                topico.autor?.nome,
                topico.status,
                RespostaDto.convertList(topico.respostas)
            ){

    }

}