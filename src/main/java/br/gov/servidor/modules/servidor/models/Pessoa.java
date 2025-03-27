package br.gov.servidor.modules.servidor.models;

import br.gov.servidor.core.models.Endereco;
import br.gov.servidor.modules.servidor.enums.ESexo;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

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

    @Size(max = 200)
    @Column(name = "pes_nome", length = 200)
    private String nome;

    @Column(name = "pes_data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "pes_sexo")
    @Enumerated(EnumType.STRING)
    private ESexo sexo;

    @Size(max = 200)
    @Column(name = "pes_mae", length = 200)
    private String mae;

    @Size(max = 200)
    @Column(name = "pes_pai", length = 200)
    private String pai;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.PERSIST)
    private Set<FotoPessoa> fotos = new LinkedHashSet<>();

    @SQLRestriction("lot_data_remocao is null and lot_data_lotacao is not null")
    @OneToMany(mappedBy = "pessoa")
    private Set<Lotacao> lotacoes = new LinkedHashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "pessoa_endereco",
            joinColumns = @JoinColumn(name = "pes_id"),
            inverseJoinColumns = @JoinColumn(name = "end_id")
    )
    private Set<Endereco> enderecos = new LinkedHashSet<>();
}