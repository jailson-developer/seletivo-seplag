package br.gov.servidor.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "servidor_efetivo")
public class ServidorEfetivo extends Pessoa {

    @Column(name = "se_matricula", length = 20)
    private String matricula;
}