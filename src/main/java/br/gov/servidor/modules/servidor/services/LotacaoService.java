package br.gov.servidor.modules.servidor.services;

import br.gov.servidor.core.exceptions.RegraNegocioException;
import br.gov.servidor.core.pagination.PageRequest;
import br.gov.servidor.core.pagination.PagedResponse;
import br.gov.servidor.modules.servidor.dtos.LotacaoFiltroParams;
import br.gov.servidor.modules.servidor.dtos.LotacaoRequestDto;
import br.gov.servidor.modules.servidor.dtos.LotacaoResponseDto;
import br.gov.servidor.modules.servidor.mappers.LotacaoMapper;
import br.gov.servidor.modules.servidor.models.Lotacao;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class LotacaoService {

    @Inject
    LotacaoMapper mapper;

    @Transactional
    public LotacaoResponseDto salvar(LotacaoRequestDto lotacaoDto) {
        if (lotacaoDto.getDataRemocao() != null)
            throw new RegraNegocioException("Não é permitido cadastrar lotação com data de remoção");
        var lotacao = mapper.toEntity(lotacaoDto);
        lotacao.persistAndFlush();
        return mapper.toDto(lotacao);
    }

    @Transactional
    public LotacaoResponseDto editar(Long id, LotacaoRequestDto lotacaoDto) {
        var lotacao = Lotacao.<Lotacao>findByIdOptional(id).orElseThrow(() -> new NotFoundException("Lotação não encontrada!"));
        mapper.partialUpdate(lotacaoDto, lotacao);
        lotacao.persistAndFlush();
        return mapper.toDto(lotacao);
    }

    public LotacaoResponseDto findById(Long id) {
        Lotacao lotacao = Lotacao.<Lotacao>findByIdOptional(id).orElseThrow(() -> new NotFoundException("Lotação não encontrada!"));
        return mapper.toDto(lotacao);
    }

    @Transactional
    public void deleteById(Long id) {
        Lotacao.deleteById(id);
    }

    public PagedResponse<LotacaoResponseDto> findByParams(PageRequest pageRequest, LotacaoFiltroParams filtro) {
        PanacheQuery<Lotacao> query = Lotacao.findByParams(filtro);
        return new PagedResponse<>(query, pageRequest, mapper::toDto);
    }
}
