package br.gov.servidor;

import br.gov.servidor.core.pagination.PageRequest;
import br.gov.servidor.core.pagination.PagedResponse;
import br.gov.servidor.modules.servidor.models.Unidade;
import br.gov.servidor.modules.servidor.models.UnidadeRequestDto;
import br.gov.servidor.modules.servidor.models.UnidadeResponseDto;
import br.gov.servidor.modules.servidor.services.UnidadeService;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class UnidadeServiceTest {

    @Inject
    UnidadeService unidadeService;

    @Inject
    EntityManager em;

    @Test
    @TestTransaction
    public void testSalvar() {
        UnidadeRequestDto unidadeRequestDto = new UnidadeRequestDto();
        unidadeRequestDto.setNome("Unidade Teste");
        unidadeRequestDto.setSigla("UT");

        UnidadeResponseDto responseDto = unidadeService.salvar(unidadeRequestDto);

        assertNotNull(responseDto);
        assertEquals("Unidade Teste", responseDto.getNome());
        assertEquals("UT", responseDto.getSigla());

        Unidade unidade = em.createQuery("SELECT u FROM Unidade u WHERE u.nome = :nome", Unidade.class)
                .setParameter("nome", "Unidade Teste")
                .getResultList().getFirst();

        assertNotNull(unidade);
        assertEquals("Unidade Teste", unidade.getNome());
        assertEquals("UT", unidade.getSigla());
    }

    @Test
    @TestTransaction
    public void testEditar() {
        Unidade unidade = new Unidade();
        unidade.setNome("Unidade Original");
        unidade.setSigla("UO");
        em.persist(unidade);

        UnidadeRequestDto unidadeRequestDto = new UnidadeRequestDto();
        unidadeRequestDto.setNome("Unidade Atualizada");
        unidadeRequestDto.setSigla("UA");

        UnidadeResponseDto responseDto = unidadeService.editar(unidade.getId(), unidadeRequestDto);

        assertNotNull(responseDto);
        assertEquals("Unidade Atualizada", responseDto.getNome());
        assertEquals("UA", responseDto.getSigla());

        Unidade updatedUnidade = em.find(Unidade.class, unidade.getId());
        assertNotNull(updatedUnidade);
        assertEquals("Unidade Atualizada", updatedUnidade.getNome());
        assertEquals("UA", updatedUnidade.getSigla());
    }

    @Test
    @TestTransaction
    public void testFindById() {
        Unidade unidade = new Unidade();
        unidade.setNome("Unidade para Consulta");
        unidade.setSigla("UC");
        em.persist(unidade);

        Long id = unidade.getId();

        UnidadeResponseDto responseDto = unidadeService.findById(id);

        assertNotNull(responseDto);
        assertEquals("Unidade para Consulta", responseDto.getNome());
        assertEquals("UC", responseDto.getSigla());

        assertThrows(NotFoundException.class, () -> {
            unidadeService.findById(999L);
        });
    }

    @Test
    @TestTransaction
    public void testDeleteById() {
        Unidade unidade = new Unidade();
        unidade.setNome("Unidade para Exclusão");
        unidade.setSigla("UE");
        em.persist(unidade);

        Long id = unidade.getId();

        Unidade existingUnidade = em.find(Unidade.class, id);
        assertNotNull(existingUnidade);

        unidadeService.deleteById(id);

        Unidade deletedUnidade = em.find(Unidade.class, id);
        assertNull(deletedUnidade);
    }

    @Test
    @TestTransaction
    public void testFindByNomeOuSigla() {
        Unidade unidade1 = new Unidade();
        unidade1.setNome("Unidade A");
        unidade1.setSigla("UA");
        em.persist(unidade1);

        Unidade unidade2 = new Unidade();
        unidade2.setNome("Unidade B");
        unidade2.setSigla("UB");
        em.persist(unidade2);

        PageRequest pageRequest = new PageRequest();
        PagedResponse<UnidadeResponseDto> response = unidadeService.findByNomeOuSigla(pageRequest, "Unidade", "UA");

        assertNotNull(response);
        assertTrue(response.getTotalElements() > 0);
        assertFalse(response.getContent().isEmpty());

        assertTrue(response.getContent().stream().anyMatch(dto -> dto.getNome().contains("Unidade")));
        assertTrue(response.getContent().stream().anyMatch(dto -> dto.getSigla().equals("UA")));
    }
}
