package br.gov.servidor.modules.security.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthResponseDto {
    private String access_token;
    private String refresh_token;
    private Long expires_in;
    private Long refresh_expires_in;
}