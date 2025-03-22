package br.gov.servidor.core.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "endereco")
public class Endereco extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco_id_gen")
    @SequenceGenerator(name = "endereco_id_gen", sequenceName = "endereco_end_id_seq", allocationSize = 1)
    @Column(name = "end_id", nullable = false)
    private Long id;

    @Size(max = 50)
    @Column(name = "end_tipo_logradouro", length = 50)
    private String tipoLogradouro;

    @Size(max = 200)
    @Column(name = "end_logradouro", length = 200)
    private String logradouro;

    @Size(max = 10)
    @Column(name = "end_numero", length = 10)
    private String numero;

    @Size(max = 100)
    @Column(name = "end_bairro", length = 100)
    private String bairro;

    @ManyToOne
    @JoinColumn(name = "cid_id")
    private Cidade cidade;
}