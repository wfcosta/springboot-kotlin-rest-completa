package br.com.forum.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Resposta(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
    val mensagem: String,
    @ManyToOne val topico: Topico,
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    @ManyToOne val autor: Usuario,
    val solucao: Boolean = false
){

}