package br.gov.servidor.modules.servidor.mappers;

import br.gov.servidor.core.mappers.EnderecoMapper;
import br.gov.servidor.modules.servidor.dtos.ServidorTemporarioRequestDto;
import br.gov.servidor.modules.servidor.dtos.ServidorTemporarioResponseDto;

import br.gov.servidor.modules.servidor.models.ServidorTemporario;
import br.gov.servidor.modules.servidor.models.Unidade;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.Period;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, uses = {EnderecoMapper.class}, imports = {LocalDate.class, Period.class})
public interface ServidorTemporarioMapper {


    @Mapping(target = "id", ignore = true)
    ServidorTemporario toEntity(ServidorTemporarioRequestDto servidorTemporarioDto);

    @Mapping(target = "fotos", ignore = true)
    ServidorTemporarioResponseDto toResponseDto(ServidorTemporario servidorTemporario);

    default String toUnidade(Unidade unidade) {
        return unidade.getNome();
    }

    @InheritInverseConfiguration
    void partialUpdate(ServidorTemporarioRequestDto servidorTemporarioDto, @MappingTarget ServidorTemporario servidorTemporario);
}