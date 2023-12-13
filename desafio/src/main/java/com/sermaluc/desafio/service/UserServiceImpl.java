package com.sermaluc.desafio.service;

import com.sermaluc.desafio.exceptions.InvalidDataException;
import com.sermaluc.desafio.exceptions.UserAlreadyExistsException;
import com.sermaluc.desafio.exceptions.UserNotFoundException;
import com.sermaluc.desafio.jwt.UserRequest;
import com.sermaluc.desafio.model.User;
import com.sermaluc.desafio.repository.UserRepository;
import com.sermaluc.desafio.util.EmailValidatorService;
import com.sermaluc.desafio.util.Utils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailValidatorService emailValidatorService;

    /**
     * Realiza algunas validaciones antes de llamar al metodo de grabacion
     * @param userRequest incompleto recibido como parametro de la llamada http
     * @return User
     */
    public User createUser(UserRequest userRequest) {
        User existingUser = userRepository.findByEmail(userRequest.getEmail());

        if (existingUser != null) {
            throw new UserAlreadyExistsException("Email ya existe");
        }

        if (!emailValidatorService.isValidEmail(userRequest.getEmail())) {
            throw new InvalidDataException("Formato de correo electrónico incorrecto");
        }

        if (!Utils.isValidPassword(userRequest.getPassword())) {
            throw new InvalidDataException("Formato de contraseña incorrecto");
        }

        return this.saveUser(userRequest);
    }

    @Override
    public User getUserByEmail(UserRequest userRequest) {

        User user = userRepository.findByEmail(userRequest.getEmail());

        if (user!=null) {
            return user;
        } else {
            throw new UserNotFoundException("Email no existe");
        }
    }

    @Override
    public User updateUser(UserRequest updatedUser) {
        User user = userRepository.findByEmail(updatedUser.getEmail());

        if (user != null) {
            user.setName(updatedUser.getName());
            user.setModified(LocalDate.now());
            user.setLastLogin(LocalDate.now());
            user.setActive(false);
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("No existe usuario para modificar");
        }

    }

    @Override
    public User patchUpdateUser(UserRequest userRequest) {

        User user = userRepository.findByEmail(userRequest.getEmail());

        if (user != null) {
            user.setLastLogin(LocalDate.now());
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("No existe usuario para modificar");
        }
    }

    @Override
    public boolean deleteUserByEmail(UserRequest userRequest) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userRequest.getEmail()));

        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        } else {
            throw new UserNotFoundException("No existe usuario para eliminar");
        }
     }

    /**
     * Crea un el objeto de tipo User para ser grabado en la base de datos
     * @param userRequest recibido como parametro de la llamada http
     * @return User
     */
    public User saveUser(UserRequest userRequest) {
        // Generacion de UUID para el id del nuevo usuario
        UUID userId = UUID.randomUUID();

        User user = new User();
        user.setId(userId.toString());
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setPhones(userRequest.getPhones());
        LocalDate dateCreate = LocalDate.now();
        user.setCreated(dateCreate);
        user.setModified(null);
        user.setLastLogin(dateCreate);
        user.setActive(true);
        String token = this.createJwtToken(user);
        user.setToken(token);

        return userRepository.save(user);
    }

    /**
     * Crea un token a partir de los datos del usuario
     * @param user
     * @return String token
     */
    public String createJwtToken(User user) {
        // Define las reclamaciones (claims) del token
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", user.getId());
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());

        // Configura la fecha de emisión y caducidad del token
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate now = LocalDate.now();
        LocalDate expirationLocalDate = LocalDate.now();

        Date date = Date.from(now.atStartOfDay(defaultZoneId).toInstant());
        Date expirationDate = Date.from(now.atStartOfDay(defaultZoneId).toInstant());

        // Crea el token JWT
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, "SecretKey")
                .compact();

        return token;
    }

    /**
     * En este metodo aplicao 2 caracteristicas de java 8 (metodo stream y una expresion lamda)
     * @return
     */
    @Override
    public List<User> listar() {
        List<User> users = userRepository.findAll();
        if (users != null) {
            return users.stream()
                    .sorted((user1, user2) -> {
                        return user1.getName().compareTo(user2.getName());
                    })
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

}
