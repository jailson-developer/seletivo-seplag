package br.gov.servidor.modules.security.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RoleDto implements Serializable {
    private  Long id;
    private  String nome;
}
