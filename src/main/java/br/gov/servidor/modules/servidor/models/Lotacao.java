package br.gov.servidor.modules.servidor.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "lotacao")
public class Lotacao {

    @Id
    @ColumnDefault("nextval('lotacao_lot_id_seq')")
    @Column(name = "lot_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;

    @Column(name = "lot_data_lotacao")
    private LocalDate dataLotacao;

    @Column(name = "lot_data_remocao")
    private LocalDate dataRemocao;

    @Column(name = "lot_portaria", length = 100)
    private String portaria;

    @JoinColumn(name = "unid_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Unidade unidade;

}