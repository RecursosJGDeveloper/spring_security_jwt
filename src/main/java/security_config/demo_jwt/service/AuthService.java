
package security_config.demo_jwt.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import security_config.demo_jwt.dto.auth.AuthResponse;
import security_config.demo_jwt.dto.auth.LoginRequest;
import security_config.demo_jwt.dto.auth.RegisterRequest;
import security_config.demo_jwt.models.Role;
import security_config.demo_jwt.models.UserEntity;
import security_config.demo_jwt.repository.AuthRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import security_config.demo_jwt.service.impl.AuthServiceImpl;
@Service
@RequiredArgsConstructor
public class AuthService implements AuthServiceImpl{

    private final AuthRepository authRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user=authRepo.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
        .token(token)
        .build();
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        UserEntity user = UserEntity.builder()
        .username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .country(request.getCountry())
        .role(Role.USER)
        .build();

        authRepo.save(user);
        return AuthResponse.builder()
        .token(jwtService.getToken(user))
        .build();

    }

}
