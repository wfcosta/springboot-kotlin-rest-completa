package br.com.forum.model

import java.time.LocalDateTime
import java.util.ArrayList
import javax.persistence.*

@Entity
data class Topico constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    var titulo: String,
    var mensagem: String,
    val dataCriacao: LocalDateTime? = LocalDateTime.now(),
    @Enumerated(EnumType.STRING) val status: StatusTopico? = StatusTopico.NAO_RESPONDIDO,
    @ManyToOne val autor: Usuario? = null,
    @ManyToOne val curso: Curso,
    @OneToMany(mappedBy = "topico") val respostas: List<Resposta> = ArrayList()
){

}