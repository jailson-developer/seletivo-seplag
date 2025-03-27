package br.gov.servidor.modules.servidor.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FotoResponseDto {
    private String url;
    private String nome;
}
