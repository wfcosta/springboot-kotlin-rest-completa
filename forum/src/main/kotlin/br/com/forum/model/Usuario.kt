package br.com.forum.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
data class Usuario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
    val nome: String,
    val email: String,
    val senha: String,
    @ManyToMany(fetch = FetchType.EAGER) val perfis: MutableList<Perfil>
    ): UserDetails{

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.perfis
    }

    override fun getPassword(): String {
        return this.senha
    }

    override fun getUsername(): String {
        return this.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}