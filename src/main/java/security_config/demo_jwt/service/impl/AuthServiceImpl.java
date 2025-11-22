package security_config.demo_jwt.service.impl;

import security_config.demo_jwt.dto.auth.AuthResponse;
import security_config.demo_jwt.dto.auth.LoginRequest;
import security_config.demo_jwt.dto.auth.RegisterRequest;

public interface AuthServiceImpl {

    // metodo de login
    public AuthResponse login(LoginRequest request);
    // metodo de register
    public AuthResponse register(RegisterRequest request);
}
