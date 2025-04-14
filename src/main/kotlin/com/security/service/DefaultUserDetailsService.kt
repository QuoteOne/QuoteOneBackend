package com.security.service

import com.security.repository.IUserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class DefaultUserDetailsService(
    val userRepo: IUserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        return userRepo.findByUsername(username!!) ?: throw UsernameNotFoundException(username)
    }
}