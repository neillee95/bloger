package me.lee.bloger.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class SecurityUser(private var username: String,
                   private var password: String,
                   private var roles: Collection<String> = listOf()) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
            roles.map { SimpleGrantedAuthority(it) }
                    .toMutableList()

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String = this.username

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String = this.password

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

}