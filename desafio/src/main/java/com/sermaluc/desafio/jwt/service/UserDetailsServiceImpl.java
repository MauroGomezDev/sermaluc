package com.sermaluc.desafio.jwt.service;

import java.util.List;
import java.util.Set;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var usuario = getById(username);

    if (usuario == null) {
      throw new UsernameNotFoundException(username);
    }
    return User
        .withUsername(username)
        .password(usuario.password())
        .roles(usuario.roles().toArray(new String[0]))
        .build();
  }

  public record Usuario(String username, String password, Set<String> roles) {};

  public static Usuario getById(String username) {
    // "secreto" => [BCrypt] => "$2a$12$9CdcqPEC117w3KGTlOWMuu6qrOlts6IyTnjhOY.nKFXxKy2J46E4e"
    var password = "$2a$12$9CdcqPEC117w3KGTlOWMuu6qrOlts6IyTnjhOY.nKFXxKy2J46E4e";
    Usuario sermaluc = new Usuario(
        "sermaluc@gmail.com",
        password,
        Set.of("USER")
    );

    Usuario sermaluc1 = new Usuario(
        "sermaluc1@gmail.com",
        password,
        Set.of("ADMIN")
    );
    var usuarios = List.of(sermaluc, sermaluc1);

    return usuarios
        .stream()
        .filter(e -> e.username().equals(username))
        .findFirst()
        .orElse(null);
  }
}
