package br.gov.servidor.core.mappers;

import br.gov.servidor.core.dtos.EnderecoResponseDto;
import br.gov.servidor.core.models.Endereco;
import br.gov.servidor.core.dtos.EnderecoRequestDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, uses = {CidadeMapper.class})
public interface EnderecoMapper {

    @Mapping(source = "cidadeId", target = "cidade", qualifiedByName = "toCidade")
    Endereco toEntity(EnderecoRequestDto enderecoRequestDto);

    EnderecoResponseDto toDto(Endereco endereco);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "cidadeId", target = "cidade", qualifiedByName = "toCidade")
    void partialUpdate(EnderecoRequestDto enderecoRequestDto, @MappingTarget Endereco endereco);
}