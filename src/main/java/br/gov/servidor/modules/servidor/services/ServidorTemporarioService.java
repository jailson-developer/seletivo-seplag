package br.gov.servidor.modules.servidor.services;

import br.gov.servidor.core.exceptions.RegraNegocioException;
import br.gov.servidor.core.pagination.PageRequest;
import br.gov.servidor.core.pagination.PagedResponse;
import br.gov.servidor.core.utils.Func;
import br.gov.servidor.modules.servidor.dtos.*;
import br.gov.servidor.modules.servidor.mappers.ServidorTemporarioMapper;
import br.gov.servidor.modules.servidor.mappers.ServidorTemporarioMapper;
import br.gov.servidor.modules.servidor.models.FotoPessoa;
import br.gov.servidor.modules.servidor.models.Lotacao;
import br.gov.servidor.modules.servidor.models.ServidorTemporario;
import br.gov.servidor.modules.servidor.models.ServidorTemporario;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ServidorTemporarioService {

    @Inject
    ServidorTemporarioMapper mapper;

    @Inject
    ServidorService servidorService;

    @Transactional
    public ServidorTemporarioResponseDto salvar(ServidorTemporarioRequestDto temporarioRequestDto) {
        ServidorTemporario entity = mapper.toEntity(temporarioRequestDto);
        entity.persist();
        return mapper.toResponseDto(entity);
    }

    @Transactional
    public ServidorTemporarioResponseDto atualizar(Long id, ServidorTemporarioRequestDto servidorTemporarioRequestDto) {
        ServidorTemporario entity = ServidorTemporario.findById(id);
        if (entity == null) {
            throw new RegraNegocioException("Servidor não encontrado");
        }
        mapper.partialUpdate(servidorTemporarioRequestDto, entity);
        entity.persist();
        return mapper.toResponseDto(entity);
    }

    @Transactional
    public void excluir(Long id) {
        ServidorTemporario.deleteById(id);
    }


    public ServidorTemporarioResponseDto consultaCompleta(Long id) {
        ServidorTemporario entity = ServidorTemporario.findById(id);
        if (entity == null) {
            throw new RegraNegocioException("Servidor não encontrado");
        }
        ServidorTemporarioResponseDto responseDto = mapper.toResponseDto(entity);
        for (FotoPessoa foto : entity.getFotos()) {
            responseDto.getFotos().add(servidorService.buscarFoto(foto));
        }
        return responseDto;
    }

}
