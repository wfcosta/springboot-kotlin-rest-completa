package br.com.forum.config.validation

import br.com.forum.config.validation.dto.ErroDeFormularioDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ValidationErrorHandler {

    @Autowired
    lateinit var messageSource: MessageSource

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(ex: MethodArgumentNotValidException): List<ErroDeFormularioDto>{
        val result = arrayListOf<ErroDeFormularioDto>()
        ex.bindingResult.fieldErrors.forEach {
            result.add(ErroDeFormularioDto(it.field, messageSource.getMessage(it, LocaleContextHolder.getLocale())))
        }
        return result
    }

}