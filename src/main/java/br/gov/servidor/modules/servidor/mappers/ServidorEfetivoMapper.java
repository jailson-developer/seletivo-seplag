package br.gov.servidor.modules.servidor.mappers;

import br.gov.servidor.core.mappers.EnderecoMapper;
import br.gov.servidor.modules.servidor.dtos.LotacaoResponseDto;
import br.gov.servidor.modules.servidor.dtos.ServidorEfetivoRequestDto;
import br.gov.servidor.modules.servidor.dtos.ServidorEfetivoResponseDto;
import br.gov.servidor.modules.servidor.dtos.ServidorEfetivoResumoResponseDto;
import br.gov.servidor.modules.servidor.models.Lotacao;
import br.gov.servidor.modules.servidor.models.ServidorEfetivo;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, uses = {EnderecoMapper.class}, imports = {LocalDate.class, Period.class})
public interface ServidorEfetivoMapper {


    @Mapping(target = "fotos", ignore = true)
    @Mapping(target = "lotacoes", ignore = true)
    @Mapping(target = "id", ignore = true)
    ServidorEfetivo toEntity(ServidorEfetivoRequestDto servidorEfetivoDto);

    ServidorEfetivoResponseDto toResponseDto(ServidorEfetivo servidorEfetivo);

    @Mapping(target = "idade", expression = "java(servidorEfetivo.getDataNascimento()==null?null:Period.between(servidorEfetivo.getDataNascimento(), LocalDate.now()).getYears())")
    @Mapping(target = "lotacao", source = "lotacoes")
    @Mapping(target = "fotos", ignore = true)
    ServidorEfetivoResumoResponseDto toResumoResponseDto(ServidorEfetivo servidorEfetivo);

    default LotacaoResponseDto lotacoesToLocacaoResponseDto(Set<Lotacao> lotacoes) {
        Optional<Lotacao> lotacao = lotacoes.stream().findFirst();
        return lotacao.map(value -> LotacaoResponseDto.builder()
                .dataLotacao(value.getDataLotacao())
                .portaria(value.getPortaria())
                .unidade(value.getUnidade().getNome())
                .build()).orElse(null);
    }

    @InheritInverseConfiguration
    void partialUpdate(ServidorEfetivoRequestDto servidorEfetivoDto, @MappingTarget ServidorEfetivo servidorEfetivo);
}