package br.gov.servidor.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "role")
public class Role {

    @Id
    @ColumnDefault("nextval('role_role_id_seq')")
    @Column(name = "role_id", nullable = false)
    private Long id;

    @Column(name = "role_nome", length = 40)
    private String nome;

    @Column(name = "role_descricao", length = 100)
    private String descricao;

}