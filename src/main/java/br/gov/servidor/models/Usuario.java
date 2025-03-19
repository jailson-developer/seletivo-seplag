package br.gov.servidor.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_id_gen")
    @SequenceGenerator(name = "usuario_id_gen", sequenceName = "usuario_user_id_seq", allocationSize = 1)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "user_name", length = 80)
    private String name;

    @Column(name = "user_login", length = 40)
    private String login;

    @Column(name = "user_password", length = 250)
    private String password;

    @ManyToMany
    @JoinTable(name = "role_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new LinkedHashSet<>();
}