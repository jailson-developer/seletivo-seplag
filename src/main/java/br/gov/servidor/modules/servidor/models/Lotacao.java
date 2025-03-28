package br.gov.servidor.modules.servidor.models;

import br.gov.servidor.core.utils.QueryUtils;
import br.gov.servidor.modules.servidor.dtos.LotacaoFiltroParams;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "lotacao")
public class Lotacao extends PanacheEntityBase {

    @Id
    @Column(name = "lot_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lotacao_lot_id_gen")
    @SequenceGenerator(name = "lotacao_lot_id_gen", sequenceName = "lotacao_lot_id_seq", allocationSize = 1)
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

    public static PanacheQuery<Lotacao> findByParams(LotacaoFiltroParams filtro) {
        Parameters parameters = new Parameters();
        StringBuilder query = new StringBuilder("1=1");
        if (StringUtils.isNotBlank(filtro.getPortaria())) {
            parameters.and("portaria", QueryUtils.formatarQueryContem(filtro.getPortaria()));
            query.append(" AND upper(portaria) LIKE :portaria");
        }
        if (filtro.getDataLotacao() != null) {
            parameters.and("dataLotacao", filtro.getDataLotacao());
            query.append(" AND dataLotacao = :dataLotacao");
        }
        if (filtro.getDataRemocao() != null) {
            parameters.and("dataRemocao", filtro.getDataRemocao());
            query.append(" AND dataRemocao = :dataRemocao");
        }
        return find(query.toString(), parameters);
    }
}