package br.com.forum.controller

import br.com.forum.controller.dto.DetalhesDoTopicoDto
import br.com.forum.controller.dto.TopicoDto
import br.com.forum.controller.form.AtualizacaoTopicoForm
import br.com.forum.controller.form.TopicoForm
import br.com.forum.model.Topico
import br.com.forum.repository.CursoRepository
import br.com.forum.repository.TopicoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import javax.transaction.Transactional
import javax.validation.Valid


@RestController
@RequestMapping("/topicos")
class TopicosController {

    @Autowired
    lateinit var topicoRepository: TopicoRepository

    @Autowired
    lateinit var cursoRepository: CursoRepository

    @GetMapping
    @Cacheable(value = ["listaDeTopicos"])
    fun lista(@RequestParam(required = false) nomeCurso: String?,
              //@RequestParam pagina: Int,
              //@RequestParam qtd: Int,
              //@RequestParam ordenacao: String
              @PageableDefault(sort = ["id"], direction = Sort.Direction.ASC) paginacao: Pageable
    ): Page<TopicoDto> {
        //val paginacao: Pageable = PageRequest.of(pagina, qtd, Sort.Direction.ASC, ordenacao)
        nomeCurso?.let {
            return TopicoDto.converter(topicoRepository.findByCursoNome(nomeCurso, paginacao))
        }
        return TopicoDto.converter(topicoRepository.findAll(paginacao))
    }

    @Transactional
    @PostMapping
    @CacheEvict(value = ["listaDeTopicos"], allEntries = true)
    fun cadastrar(@RequestBody @Valid topicoForm: TopicoForm, uriComponentsBuilder: UriComponentsBuilder): ResponseEntity<TopicoDto>{
        var topico = topicoForm.converter(cursoRepository)
        topico = topicoRepository.save(topico)
        val uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.id).toUri()
        return ResponseEntity.created(uri).body(TopicoDto(topico))
    }

    @GetMapping("/{id}")
    fun detalhar(@PathVariable id: Long): ResponseEntity<DetalhesDoTopicoDto> {
        val topico: Optional<Topico>  = topicoRepository.findById(id)
        if(topico.isPresent){
            return ResponseEntity.ok(DetalhesDoTopicoDto(topico.get()))
        }
        return ResponseEntity.notFound().build()
    }

    @Transactional
    @PutMapping("/{id}")
    @CacheEvict(value = ["listaDeTopicos"], allEntries = true)
    fun atualizar(@PathVariable id: Long, @RequestBody @Valid atualizacaoTopicoForm: AtualizacaoTopicoForm): ResponseEntity<TopicoDto>{
        val topico: Optional<Topico>  = topicoRepository.findById(id)
        if(topico.isPresent){
            return ResponseEntity.ok(TopicoDto(atualizacaoTopicoForm.atualizar(id, topicoRepository)))
        }
        return ResponseEntity.notFound().build()
    }

    @Transactional
    @DeleteMapping("/{id}")
    @CacheEvict(value = ["listaDeTopicos"], allEntries = true)
    fun remover(@PathVariable id: Long): ResponseEntity<Any>{
        val topico: Optional<Topico>  = topicoRepository.findById(id)
        if(topico.isPresent){
            topicoRepository.deleteById(id)
            return ResponseEntity.ok().build()
        }
        return ResponseEntity.notFound().build()
    }

}