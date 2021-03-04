package com.github.rafaelmdb.domain.service;

import com.github.rafaelmdb.service.BaseService;
import com.github.rafaelmdb.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl extends BaseService implements UserDetailsService {
    @Autowired
    private PasswordEncoder encoder;

    public UsuarioServiceImpl(MessageService messageService) {
        super(messageService);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if (!userName.equals("rafael")) {
            throw new UsernameNotFoundException("Usuário não encontrado.");
        }
        return User
                .builder()
                .username("rafael")
                .password(encoder.encode("rafael"))
                .roles("USER", "ADMIN")
                .build();
    }
}
