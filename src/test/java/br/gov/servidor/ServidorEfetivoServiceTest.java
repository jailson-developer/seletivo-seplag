package br.gov.servidor;

import br.gov.servidor.modules.servidor.dtos.ServidorEfetivoRequestDto;
import br.gov.servidor.modules.servidor.dtos.ServidorEfetivoResponseDto;
import br.gov.servidor.modules.servidor.models.ServidorEfetivo;
import br.gov.servidor.modules.servidor.services.ServidorEfetivoService;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class ServidorEfetivoServiceTest {

    @Inject
    ServidorEfetivoService servico;

    @Inject
    EntityManager em;

    @Test
    @TestTransaction
    public void testSalvar() {
        ServidorEfetivoRequestDto requestDto = new ServidorEfetivoRequestDto();
        String matricula = StringUtils.substringBefore(UUID.randomUUID().toString(), "-");
        requestDto.setMatricula(matricula);
        requestDto.setNome("Servidor Teste");

        ServidorEfetivoResponseDto responseDto = servico.salvar(requestDto);

        assertNotNull(responseDto);
        assertEquals(matricula, responseDto.getMatricula());

        ServidorEfetivo entity = em.createQuery("SELECT s FROM ServidorEfetivo s WHERE s.matricula = :matricula", ServidorEfetivo.class)
                                   .setParameter("matricula", matricula)
                                   .getSingleResult();

        assertNotNull(entity);
    }

    @Test
    @TestTransaction
    public void testAtualizar() {
        ServidorEfetivo entity = new ServidorEfetivo();
        entity.setMatricula("12345");
        entity.setNome("Servidor Original");
        em.persist(entity);

        ServidorEfetivoRequestDto requestDto = new ServidorEfetivoRequestDto();
        requestDto.setNome("Servidor Atualizado");

        ServidorEfetivoResponseDto responseDto = servico.atualizar(entity.getId(), requestDto);

        assertNotNull(responseDto);
        assertEquals("Servidor Atualizado", responseDto.getNome());

        ServidorEfetivo updatedEntity = em.find(ServidorEfetivo.class, entity.getId());
        assertNotNull(updatedEntity);
        assertEquals("Servidor Atualizado", updatedEntity.getNome());
    }

    @Test
    @TestTransaction
    public void testExcluir() {
        ServidorEfetivo entity = new ServidorEfetivo();
        entity.setMatricula("12345");
        entity.setNome("Servidor para Exclusão");
        em.persist(entity);

        Long id = entity.getId();

        ServidorEfetivo existingEntity = em.find(ServidorEfetivo.class, id);
        assertNotNull(existingEntity);

        servico.excluir(id);

        ServidorEfetivo deletedEntity = em.find(ServidorEfetivo.class, id);
        assertNull(deletedEntity);
    }


    @Test
    @TestTransaction
    public void testConsultaCompleta() {
        ServidorEfetivo entity = new ServidorEfetivo();
        entity.setMatricula("12345");
        entity.setNome("Servidor Completo");
        em.persist(entity);

        Long id = entity.getId();

        ServidorEfetivoResponseDto responseDto = servico.consultaCompleta(id);

        assertNotNull(responseDto);
        assertEquals("12345", responseDto.getMatricula());
    }
}
