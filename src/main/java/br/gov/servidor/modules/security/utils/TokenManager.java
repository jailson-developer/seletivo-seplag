package br.gov.servidor.modules.security.utils;


import br.gov.servidor.modules.security.dtos.AuthResponseDto;
import br.gov.servidor.modules.security.models.Role;
import br.gov.servidor.modules.security.models.Usuario;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;


import java.time.Duration;
import java.util.stream.Collectors;

@ApplicationScoped
public class TokenManager {

    @ConfigProperty(name = "security.token.expiration")
    Duration acessTokenExpiration;

    @ConfigProperty(name = "security.refreshtoken.expiration")
    Duration refreshTokenExpiration;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    public AuthResponseDto generateToken(Usuario user) {
        var auth = AuthResponseDto.builder();
        long currentTimeInSecs = currentTimeInSecs();

        var claimsBuilder = Jwt.issuer(issuer)
                .issuedAt(currentTimeInSecs)
                .expiresAt(currentTimeInSecs + refreshTokenExpiration.toSeconds())
                .claim("userId", user.getId())
                .subject(user.getLogin())
                .claim("type", "Refresh");

        auth.refresh_token(claimsBuilder.sign());
        auth.refresh_expires_in(refreshTokenExpiration.toSeconds());

        claimsBuilder.groups(user.getRoles().stream().map(Role::getNome).collect(Collectors.toUnmodifiableSet()))
                .expiresAt(currentTimeInSecs + acessTokenExpiration.toSeconds())
                .claim("type", "Bearer");

        auth.access_token(claimsBuilder.sign());
        auth.expires_in(acessTokenExpiration.toSeconds());
        return auth.build();
    }

    private int currentTimeInSecs() {
        long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    }
}