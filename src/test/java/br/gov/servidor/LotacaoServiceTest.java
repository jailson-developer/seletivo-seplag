package br.gov.servidor;

import br.gov.servidor.core.pagination.PageRequest;
import br.gov.servidor.core.pagination.PagedResponse;
import br.gov.servidor.modules.servidor.dtos.LotacaoFiltroParams;
import br.gov.servidor.modules.servidor.dtos.LotacaoRequestDto;
import br.gov.servidor.modules.servidor.dtos.LotacaoResponseDto;
import br.gov.servidor.modules.servidor.models.Lotacao;
import br.gov.servidor.modules.servidor.models.ServidorEfetivo;
import br.gov.servidor.modules.servidor.models.Unidade;
import br.gov.servidor.modules.servidor.services.LotacaoService;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class LotacaoServiceTest {

    @Inject
    LotacaoService lotacaoService;

    @Inject
    EntityManager em;

    private Unidade unidade;
    private ServidorEfetivo servidorEfetivo;

    @BeforeEach
    @Transactional
    public void setup() {
        unidade = new Unidade();
        unidade.setNome("Unidade Teste");
        unidade.setSigla("UT");
        unidade.persistAndFlush();

        servidorEfetivo = new ServidorEfetivo();
        servidorEfetivo.setMatricula("12345");
        servidorEfetivo.setNome("Servidor Teste");
        servidorEfetivo.persistAndFlush();
    }

    @Test
    @TestTransaction
    public void testSalvarLotacao() {
        LotacaoRequestDto lotacaoDto = new LotacaoRequestDto();
        lotacaoDto.setPessoaId(servidorEfetivo.getId());
        lotacaoDto.setUnidadeId(unidade.getId());

        LotacaoResponseDto response = lotacaoService.salvar(lotacaoDto);

        assertNotNull(response);
        Lotacao lotacaoPersistida = em.find(Lotacao.class, response.getId());
        assertNotNull(lotacaoPersistida);
        assertEquals(lotacaoPersistida.getUnidade().getId(), unidade.getId());
    }

    @Test
    @TestTransaction
    public void testEditarLotacao() {
        LotacaoRequestDto lotacaoDto = new LotacaoRequestDto();
        lotacaoDto.setPessoaId(servidorEfetivo.getId());
        lotacaoDto.setUnidadeId(unidade.getId());
        LotacaoResponseDto response = lotacaoService.salvar(lotacaoDto);

        LotacaoRequestDto updateDto = new LotacaoRequestDto();
        updateDto.setPessoaId(servidorEfetivo.getId());
        updateDto.setUnidadeId(unidade.getId());
        updateDto.setPortaria("Portaria123");

        LotacaoResponseDto updatedResponse = lotacaoService.editar(response.getId(), updateDto);

        assertNotNull(updatedResponse);
        assertEquals(updateDto.getPortaria(), updatedResponse.getPortaria());

        Lotacao lotacaoPersistida = em.find(Lotacao.class, updatedResponse.getId());
        assertNotNull(lotacaoPersistida);
    }

    @Test
    @TestTransaction
    public void testFindById() {
        LotacaoRequestDto lotacaoDto = new LotacaoRequestDto();
        lotacaoDto.setPessoaId(servidorEfetivo.getId());
        lotacaoDto.setUnidadeId(unidade.getId());
        LotacaoResponseDto savedResponse = lotacaoService.salvar(lotacaoDto);

        LotacaoResponseDto foundResponse = lotacaoService.findById(savedResponse.getId());

        assertNotNull(foundResponse);
        assertEquals(savedResponse.getId(), foundResponse.getId());
    }

    @Test
    @TestTransaction
    public void testDeleteById() {
        LotacaoRequestDto lotacaoDto = new LotacaoRequestDto();
        lotacaoDto.setPessoaId(servidorEfetivo.getId());
        lotacaoDto.setUnidadeId(unidade.getId());
        LotacaoResponseDto savedResponse = lotacaoService.salvar(lotacaoDto);

        lotacaoService.deleteById(savedResponse.getId());
        Lotacao lotacaoDeletada = em.find(Lotacao.class, savedResponse.getId());
        assertNull(lotacaoDeletada);
    }

    @Test
    @TestTransaction
    public void testFindByParams() {
        Lotacao lotacao = new Lotacao();
        lotacao.setPortaria("Portaria123");
        lotacao.setPessoa(servidorEfetivo);
        lotacao.setUnidade(unidade);
        em.persist(lotacao);

        LotacaoFiltroParams filtro = new LotacaoFiltroParams();
        filtro.setPortaria("Portaria123");

        PageRequest pageRequest = new PageRequest(0, 10);
        PagedResponse<LotacaoResponseDto> response = lotacaoService.findByParams(pageRequest, filtro);

        assertNotNull(response);
        assertFalse(response.getContent().isEmpty());
        assertEquals(unidade.getId(), response.getContent().getFirst().getId());
    }
}
