package br.gov.servidor.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "servidor_temporario")
public class ServidorTemporario extends Pessoa {

    @Column(name = "st_data_admissao")
    private LocalDate dataAdmissao;

    @Column(name = "st_data_demissao")
    private LocalDate dataDemissao;
}