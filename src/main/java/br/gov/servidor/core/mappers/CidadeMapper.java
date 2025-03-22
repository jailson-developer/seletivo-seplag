package br.gov.servidor.core.mappers;

import br.gov.servidor.core.dtos.CidadeDto;
import br.gov.servidor.core.models.Cidade;
import jakarta.ws.rs.NotFoundException;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface CidadeMapper {

    @Named("toCidade")
    default Cidade toCidade(Long cidadeId){
        if(cidadeId == null) return null;
        return Cidade.<Cidade>findByIdOptional(cidadeId).orElseThrow(() -> new NotFoundException("Cidade não encontrada!"));
    }

    Cidade toEntity(CidadeDto cidadeDto);

    CidadeDto toDto(Cidade cidade);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Cidade partialUpdate(CidadeDto cidadeDto, @MappingTarget Cidade cidade);
}