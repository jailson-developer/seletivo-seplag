package br.gov.servidor.modules.servidor.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "foto_pessoa")
public class FotoPessoa extends PanacheEntityBase {

    @SequenceGenerator(name = "foto_pessoa_id_gen", sequenceName = "cidade_cid_id_seq", allocationSize = 1)
    @EmbeddedId
    private FotoPessoaId id = new FotoPessoaId();

    @MapsId("pesId")
    @ManyToOne
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;

    @Column(name = "fp_data")
    private LocalDate data = LocalDate.now();

    @Column(name = "fp_bucket", length = 50)
    private String bucket;

    @Column(name = "fp_hash", length = 200)
    private String hash;
}