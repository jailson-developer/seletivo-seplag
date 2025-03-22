package br.gov.servidor.modules.servidor.mappers;

import br.gov.servidor.core.mappers.EnderecoMapper;
import br.gov.servidor.core.models.Endereco;
import br.gov.servidor.core.models.EnderecoRequestDto;
import br.gov.servidor.modules.servidor.dtos.ServidorEfetivoRequestDto;
import br.gov.servidor.modules.servidor.dtos.ServidorEfetivoResponseDto;
import br.gov.servidor.modules.servidor.dtos.ServidorEfetivoResumoResponseDto;
import br.gov.servidor.modules.servidor.models.ServidorEfetivo;
import jakarta.inject.Inject;
import org.mapstruct.*;

import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, uses = {EnderecoMapper.class})
public interface ServidorEfetivoMapper {


    @Mapping(target = "fotos", ignore = true)
    @Mapping(target = "lotacoes", ignore = true)
    @Mapping(target = "id", ignore = true)
    ServidorEfetivo toEntity(ServidorEfetivoRequestDto servidorEfetivoDto);

    ServidorEfetivoResponseDto toResponseDto(ServidorEfetivo servidorEfetivo);

    ServidorEfetivoResumoResponseDto toResumoResponseDto(ServidorEfetivo servidorEfetivo);

    @InheritInverseConfiguration
    void partialUpdate(ServidorEfetivoRequestDto servidorEfetivoDto, @MappingTarget ServidorEfetivo servidorEfetivo);
}