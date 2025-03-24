package br.gov.servidor.modules.servidor.mappers;

import br.gov.servidor.core.mappers.EnderecoMapper;
import br.gov.servidor.modules.servidor.models.Unidade;
import br.gov.servidor.modules.servidor.models.UnidadeRequestDto;
import br.gov.servidor.modules.servidor.models.UnidadeResponseDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, uses = {EnderecoMapper.class})
public interface UnidadeMapper {

    @Mapping(target = "id", ignore = true)
    Unidade toEntity(UnidadeRequestDto unidadeRequestDto);

    UnidadeResponseDto toDto(Unidade unidade);

    @Mapping(target = "id", ignore = true)
    void partialUpdate(UnidadeRequestDto unidadeRequestDto, @MappingTarget Unidade unidade);
}