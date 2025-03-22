package br.gov.servidor.modules.servidor.services;

import br.gov.servidor.core.exceptions.RegraNegocioException;
import br.gov.servidor.core.pagination.PageRequest;
import br.gov.servidor.core.pagination.PagedResponse;
import br.gov.servidor.modules.servidor.dtos.ServidorEfetivoRequestDto;
import br.gov.servidor.modules.servidor.dtos.ServidorEfetivoResponseDto;
import br.gov.servidor.modules.servidor.dtos.ServidorEfetivoResumoResponseDto;
import br.gov.servidor.modules.servidor.mappers.ServidorEfetivoMapper;
import br.gov.servidor.modules.servidor.models.FotoPessoa;
import br.gov.servidor.modules.servidor.models.ServidorEfetivo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ServicorEfetivoService {

    @Inject
    ServidorEfetivoMapper mapper;

    @Inject
    ServidorService servidorService;

    @Transactional
    public void salvar(ServidorEfetivoRequestDto servidorEfetivoRequestDto) {
        ServidorEfetivo.findByMatricula(servidorEfetivoRequestDto.getMatricula()).ifPresent((v) -> {
            throw new RegraNegocioException("Servidor já cadastrado");
        });
        ServidorEfetivo entity = mapper.toEntity(servidorEfetivoRequestDto);
        entity.persist();
    }

    @Transactional
    public void atualizar(Long id, ServidorEfetivoRequestDto servidorEfetivoRequestDto) {
        ServidorEfetivo entity = ServidorEfetivo.findById(id);
        if (entity == null) {
            throw new RegraNegocioException("Servidor não encontrado");
        }
        mapper.partialUpdate(servidorEfetivoRequestDto, entity);
        entity.persist();
    }

    @Transactional
    public void excluir(Long id) {
        ServidorEfetivo entity = ServidorEfetivo.findById(id);
        if (entity == null) {
            throw new RegraNegocioException("Servidor não encontrado");
        }
        entity.delete();
    }


    public ServidorEfetivoResponseDto consultaCompleta(Long id) {
        ServidorEfetivo entity = ServidorEfetivo.findById(id);
        if (entity == null) {
            throw new RegraNegocioException("Servidor não encontrado");
        }
        ServidorEfetivoResponseDto responseDto = mapper.toResponseDto(entity);
        for (FotoPessoa foto : entity.getFotos()) {
            responseDto.getFotos().add(servidorService.buscarFoto(foto));
        }
        return responseDto;
    }

    public PagedResponse<ServidorEfetivoResumoResponseDto> consulta(PageRequest pageRequest) {
        return new PagedResponse<>(ServidorEfetivo.findAll(), pageRequest, mapper::toResumoResponseDto);
    }

}
