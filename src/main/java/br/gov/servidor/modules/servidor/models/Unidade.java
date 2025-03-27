package br.gov.servidor.modules.servidor.models;

import br.gov.servidor.core.models.Endereco;
import br.gov.servidor.core.utils.QueryUtils;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "unidade")
public class Unidade extends PanacheEntityBase {

    @Id
    @Column(name = "unid_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unidade_unid_id_gen")
    @SequenceGenerator(name = "unidade_unid_id_gen", sequenceName = "unidade_unid_id_seq", allocationSize = 1)
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

    public static PanacheQuery<Unidade> findByNomeOuSigla(String nome, String sigla) {
        Parameters parameters = new Parameters();
        StringBuilder query = new StringBuilder("1=1");
        if (StringUtils.isNotBlank(nome)) {
            parameters.and("nome", QueryUtils.formatarQueryContem(nome));
            query.append(" AND upper(unaccent(nome)) LIKE :nome");
        }
        if (StringUtils.isNotBlank(sigla)) {
            parameters.and("sigla", QueryUtils.formatarQueryContem(sigla));
            query.append(" AND upper(unaccent(sigla)) LIKE :sigla");
        }
        return find(query.toString(), parameters);
    }
}