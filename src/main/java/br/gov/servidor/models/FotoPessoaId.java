package br.gov.servidor.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class FotoPessoaId implements Serializable {

    @Serial
    private static final long serialVersionUID = -340791570018009154L;

    @ColumnDefault("nextval('foto_pessoa_fp_id_seq')")
    @Column(name = "fp_id", nullable = false)
    private Long fpId;

    @Column(name = "pes_id")
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FotoPessoaId entity = (FotoPessoaId) o;
        return Objects.equals(this.fpId, entity.fpId) &&
                Objects.equals(this.id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fpId, id);
    }

}