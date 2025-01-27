package com.todoapp.todo.service;

import com.todoapp.todo.dto.AuthRequest;
import com.todoapp.todo.dto.CreateUserRequest;
import com.todoapp.todo.dto.JwtResponse;
import com.todoapp.todo.entity.Role;
import com.todoapp.todo.entity.User;
import com.todoapp.todo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow((EntityNotFoundException::new));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(CreateUserRequest request) {
        User newUser= User.builder()
                .name(request.name())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .authorities(request.authorities() == null ? Set.of(Role.ROLE_USER) : request.authorities())
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .build();

        return userRepository.save(newUser);
    }

    public ResponseEntity<JwtResponse>  login(AuthRequest loginRequest){
        /*Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(request.username());
        }
//        log.info("invalid username" + request.username());
        throw new UsernameNotFoundException("invalid username {}"+request.username());*/

        Optional<User> userDetails = userRepository.findByUsername(loginRequest.username());
        if (userDetails.isPresent() && passwordEncoder.matches(loginRequest.password(), userDetails.get().getPassword())) {
            // JWT token oluşturuluyor
            String token = jwtService.generateToken(userDetails.get().getUsername());

            // Giriş başarılı mesajı ve token'ı döndürüyoruz
            JwtResponse response = new JwtResponse(token, "Giriş başarılı");

            return ResponseEntity.ok(response);
        }
        JwtResponse errorResponse = new JwtResponse("Geçersiz kimlik bilgileri", null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);

    }
}
