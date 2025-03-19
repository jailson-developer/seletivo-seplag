package br.gov.servidor.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pessoa")
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_id_gen")
    @SequenceGenerator(name = "pessoa_id_gen", sequenceName = "pessoa_pes_id_seq", allocationSize = 1)
    @Column(name = "pes_id", nullable = false)
    private Long id;

    @Column(name = "pes_nome", length = 200)
    private String nome;

    @Column(name = "pes_data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "pes_sexo", length = 9)
    private String sexo;

    @Column(name = "pes_mae", length = 200)
    private String mae;

    @Column(name = "pes_pai", length = 200)
    private String pai;

    @OneToMany(mappedBy = "id")
    private Set<FotoPessoa> fotos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "id")
    private Set<Lotacao> lotacaos = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "pessoas")
    private Set<Endereco> enderecos = new LinkedHashSet<>();
}