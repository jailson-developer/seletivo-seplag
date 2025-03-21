package br.gov.servidor.modules.security.services;


import br.gov.servidor.modules.security.dtos.AuthResponseDto;
import br.gov.servidor.modules.security.models.Usuario;
import br.gov.servidor.modules.security.utils.TokenManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class AuthenticationService {

    @Inject
    TokenManager tokenManager;

    public AuthResponseDto login(String login, String password) {
        var user = Usuario.findByLogin(login, password);
        return tokenManager.generateToken(user);
    }

    public AuthResponseDto refreshToken(Long userId) {
        return tokenManager.generateToken(Usuario.findById(userId));
    }
}
