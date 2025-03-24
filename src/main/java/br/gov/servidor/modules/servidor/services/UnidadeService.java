package br.gov.servidor.modules.servidor.services;

import br.gov.servidor.core.pagination.PageRequest;
import br.gov.servidor.core.pagination.PagedResponse;
import br.gov.servidor.modules.servidor.mappers.UnidadeMapper;
import br.gov.servidor.modules.servidor.models.Unidade;
import br.gov.servidor.modules.servidor.models.UnidadeRequestDto;
import br.gov.servidor.modules.servidor.models.UnidadeResponseDto;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UnidadeService {

    @Inject
    UnidadeMapper mapper;

    @Transactional
    public UnidadeResponseDto salvar(UnidadeRequestDto unidadeDto) {
        var unidade = mapper.toEntity(unidadeDto);
        unidade.persistAndFlush();
        return mapper.toDto(unidade);
    }

    @Transactional
    public UnidadeResponseDto editar(Long id, UnidadeRequestDto unidadeDto) {
        var unidade = Unidade.<Unidade>findByIdOptional(id).orElseThrow(() -> new NotFoundException("Unidade não encontrada!"));
        mapper.partialUpdate(unidadeDto, unidade);
        unidade.persistAndFlush();
        return mapper.toDto(unidade);
    }

    public UnidadeResponseDto findById(Long id) {
        Unidade unidade = Unidade.<Unidade>findByIdOptional(id).orElseThrow(() -> new NotFoundException("Unidade não encontrada!"));
        return mapper.toDto(unidade);
    }

    @Transactional
    public void deleteById(Long id) {
        Unidade.deleteById(id);
    }

    public PagedResponse<UnidadeResponseDto> findByNomeOuSigla(PageRequest pageRequest, String nome, String sigla) {
        PanacheQuery<Unidade> query = Unidade.findByNomeOuSigla(nome, sigla).page(pageRequest.toPage());
        return new PagedResponse<>(query, pageRequest, mapper::toDto);
    }
}
