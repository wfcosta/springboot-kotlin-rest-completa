package br.com.forum.config.security

import br.com.forum.repository.UsuarioReposirory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
class SecurityConfigurations: WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var autenticacaoService: AutenticacaoService

    @Autowired
    lateinit var tokenService: TokenService

    @Autowired
    lateinit var usuarioReposirory: UsuarioReposirory

    //Configureções de autorização
    override fun configure(http: HttpSecurity?) {
        http!!.authorizeRequests()
            .antMatchers(HttpMethod.GET,"/topicos").permitAll()
            .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
            .antMatchers(HttpMethod.POST, "/auth").permitAll()
            .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
            .antMatchers(HttpMethod.GET, "/instances/**").permitAll()
            .antMatchers(HttpMethod.POST, "/instances/**").permitAll()  
            .anyRequest().authenticated()
            //Apenas para web session
            //.and().formLogin()
            .and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().addFilterBefore(AutenticacaoViaTokenFilter(tokenService, usuarioReposirory), UsernamePasswordAuthenticationFilter::class.java)
    }

    //Configurações de autenticação ex: Login
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(autenticacaoService).passwordEncoder(BCryptPasswordEncoder())
    }

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    //Configurações de arquivos estaticos(js, css, img)
    override fun configure(web: WebSecurity?) {
        web!!.ignoring()
            .antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**")
    }
}

//fun main(args: Array<String>) {
//    println(BCryptPasswordEncoder().encode("123456"))
//}