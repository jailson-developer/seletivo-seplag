package br.gov.servidor.modules.servidor.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class FotoPessoaId implements Serializable {

    @Column(name = "fp_id", nullable = false)
    @SequenceGenerator(name = "foto_pessoa_fp_id_gen", sequenceName = "foto_pessoa_fp_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foto_pessoa_fp_id_gen")
    private Long fpId;

    @Column(name = "pes_id")
    private Long pesId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FotoPessoaId entity = (FotoPessoaId) o;
        return Objects.equals(this.fpId, entity.fpId) &&
                Objects.equals(this.pesId, entity.pesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fpId, pesId);
    }

}