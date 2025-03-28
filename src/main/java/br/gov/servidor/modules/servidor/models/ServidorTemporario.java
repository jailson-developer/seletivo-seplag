package br.gov.servidor.modules.servidor.models;

import br.gov.servidor.core.utils.QueryUtils;
import br.gov.servidor.modules.servidor.dtos.ServidorTemporarioFiltroParams;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

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

    public static PanacheQuery<ServidorTemporario> findByParams(ServidorTemporarioFiltroParams params) {
        StringBuilder query = new StringBuilder("1=1");
        Parameters parameters = new Parameters();
        if (StringUtils.isNotBlank(params.getNome())) {
            parameters.and("nome", QueryUtils.formatarQueryContem(params.getNome()));
            query.append(" AND upper(unaccent(nome)) like :nome");
        }
        if (StringUtils.isNotBlank(params.getMae())) {
            parameters.and("mae", QueryUtils.formatarQueryContem(params.getMae()));
            query.append(" AND upper(unaccent(mae)) like :mae");
        }
        if (StringUtils.isNotBlank(params.getPai())) {
            parameters.and("pai", QueryUtils.formatarQueryContem(params.getPai()));
            query.append(" AND upper(unaccent(pai)) like :pai");
        }
        return find(query.toString(), Sort.ascending("nome"), parameters);
    }
}