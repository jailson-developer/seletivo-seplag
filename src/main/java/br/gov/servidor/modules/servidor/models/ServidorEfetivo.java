package br.gov.servidor.modules.servidor.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "servidor_efetivo")
public class ServidorEfetivo extends Pessoa {

    @Column(name = "se_matricula", length = 20)
    private String matricula;

    public static Optional<ServidorEfetivo> findByMatricula(String matricula) {
        return ServidorEfetivo.<ServidorEfetivo>find("matricula = ?1", matricula).firstResultOptional();
    }
}