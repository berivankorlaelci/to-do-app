package com.todoapp.todo.security;

import com.todoapp.todo.service.JwtService;
import com.todoapp.todo.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    @Lazy
    private final UserService userService;

    public JwtAuthFilter( JwtService jwtService,@Lazy UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        // Authorization header kontrolü
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Authorization header is missing or invalid.");
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(7);

        // Token'dan kullanıcı adını çıkarma
        try {
            userName = jwtService.extractUser(token);
        } catch (Exception e) {
            System.out.println("Error extracting user from token: " + e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        // Kullanıcı adı varsa ve henüz doğrulanmamışsa
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails user;

            // Kullanıcıyı yükleme
            try {
                user = userService.loadUserByUsername(userName);
            } catch (UsernameNotFoundException e) {
                System.out.println("User not found: " + userName);
                filterChain.doFilter(request, response);
                return;
            }

            // Token doğrulama
            if (jwtService.validateToken(token, user)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                System.out.println("Invalid token for user: " + userName);
            }
        }

        filterChain.doFilter(request, response);
    }




}
