package br.gov.servidor.modules.servidor.models;

import br.gov.servidor.core.models.Endereco;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "unidade")
public class Unidade {

    @Id
    @ColumnDefault("nextval('unidade_unid_id_seq')")
    @Column(name = "unid_id", nullable = false)
    private Long id;

    @Column(name = "unid_nome", length = 200)
    private String nome;

    @Column(name = "unid_sigla", length = 20)
    private String sigla;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinTable(name = "unidade_endereco",
            joinColumns = @JoinColumn(name = "unid_id"),
            inverseJoinColumns = @JoinColumn(name = "end_id"))
    private Endereco endereco;

}