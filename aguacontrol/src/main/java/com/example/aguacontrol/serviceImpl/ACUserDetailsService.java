package com.example.aguacontrol.serviceImpl;

import com.example.aguacontrol.model.Permiso;
import com.example.aguacontrol.model.Usuario;
import com.example.aguacontrol.repository.UsuarioRepository;
import com.example.aguacontrol.security.ACUserDetails;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ACUserDetailsService implements UserDetailsService {
    private final UsuarioRepository repo;

    @Override
    @NullMarked
    public ACUserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        Usuario usuario = repo.findByNombreOrEmailEager(identifier)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        List<GrantedAuthority> authorities = usuario.getRoles().stream()
                .flatMap(rol -> rol.getPermisos().stream())
                .map(Permiso::getNombre).distinct()
                .map(perm -> new SimpleGrantedAuthority("PERM_" + perm))
                .collect(Collectors.toList());

        return new ACUserDetails(
                usuario.getId(),
                usuario.getPersona().getNombre(),
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getRoles(),
                authorities
        );
    }
}