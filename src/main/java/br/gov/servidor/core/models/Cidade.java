package br.gov.servidor.core.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cidade")
public class Cidade extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cidade_id_gen")
    @SequenceGenerator(name = "cidade_id_gen", sequenceName = "cidade_cid_id_seq", allocationSize = 1)
    @Column(name = "cid_id", nullable = false)
    private Long id;

    @Column(name = "cid_nome", length = 200)
    private String nome;

    @Column(name = "cid_uf", length = 2)
    private String uf;

}