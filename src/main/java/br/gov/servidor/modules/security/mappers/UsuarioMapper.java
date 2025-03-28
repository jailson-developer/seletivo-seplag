package br.gov.servidor.modules.security.mappers;

import br.gov.servidor.modules.security.dtos.UsuarioDto;
import br.gov.servidor.modules.security.models.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {RoleMapper.class}, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface UsuarioMapper {

    Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto);

    UsuarioDto usuarioToUsuarioDto(Usuario usuario);
}
