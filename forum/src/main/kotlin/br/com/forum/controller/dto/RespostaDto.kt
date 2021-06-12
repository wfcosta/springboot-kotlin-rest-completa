package br.com.forum.controller.dto

import br.com.forum.model.Resposta
import java.time.LocalDateTime

data class RespostaDto(
    val id: Long,
    val message: String,
    val dataCriacao: LocalDateTime,
    val nomeAutor: String
) {

    constructor(resposta: Resposta) : this(resposta.id, resposta.mensagem, resposta.dataCriacao, resposta.autor.nome)

    companion object{
        fun convertList(respostas: List<Resposta>): List<RespostaDto>{
            val result = arrayListOf<RespostaDto>()
            respostas.forEach {
                result.add(RespostaDto(it))
            }
            return result
        }
    }

}
