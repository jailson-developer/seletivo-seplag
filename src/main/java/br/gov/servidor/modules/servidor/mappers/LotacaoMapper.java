package br.gov.servidor.modules.servidor.mappers;

import br.gov.servidor.core.mappers.EnderecoMapper;
import br.gov.servidor.modules.servidor.dtos.LotacaoRequestDto;
import br.gov.servidor.modules.servidor.dtos.LotacaoResponseDto;
import br.gov.servidor.modules.servidor.models.Lotacao;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, uses = {EnderecoMapper.class})
public interface LotacaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "unidade.id", source = "unidadeId")
    @Mapping(target = "pessoa.id", source = "pessoaId")
    Lotacao toEntity(LotacaoRequestDto lotacaoRequestDto);

    @Mapping(target = "unidade", source = "unidade.nome")
    LotacaoResponseDto toDto(Lotacao lotacao);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Lotacao partialUpdate(LotacaoRequestDto lotacaoRequestDto, @MappingTarget Lotacao lotacao);
}