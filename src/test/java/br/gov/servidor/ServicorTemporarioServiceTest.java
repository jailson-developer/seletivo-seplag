package br.gov.servidor;

import br.gov.servidor.core.dtos.EnderecoRequestDto;
import br.gov.servidor.modules.servidor.dtos.ServidorTemporarioRequestDto;
import br.gov.servidor.modules.servidor.dtos.ServidorTemporarioResponseDto;
import br.gov.servidor.modules.servidor.enums.ESexo;
import br.gov.servidor.modules.servidor.models.ServidorTemporario;
import br.gov.servidor.modules.servidor.services.ServidorTemporarioService;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class ServicorTemporarioServiceTest {

    @Inject
    ServidorTemporarioService servico;

    @Inject
    EntityManager em;

    @Test
    @TestTransaction
    public void testSalvar() {
        ServidorTemporarioRequestDto requestDto = new ServidorTemporarioRequestDto();
        requestDto.setNome("Servidor Teste");
        requestDto.setDataNascimento(LocalDate.of(1985, 6, 15));
        requestDto.setSexo(ESexo.MASCULINO);
        requestDto.setMae("Maria da Silva");
        requestDto.setPai("Carlos da Silva");
        requestDto.setDataAdmissao(LocalDate.of(2020, 1, 1));
        requestDto.setDataDemissao(LocalDate.of(2020, 12, 31));

        ServidorTemporarioResponseDto responseDto = servico.salvar(requestDto);

        assertNotNull(responseDto);
        assertEquals("Servidor Teste", responseDto.getNome());

        ServidorTemporario entity = em.createQuery("SELECT s FROM ServidorTemporario s WHERE s.nome = :nome", ServidorTemporario.class)
                                   .setParameter("nome", "Servidor Teste")
                                   .getSingleResult();

        assertNotNull(entity);
    }

    @Test
    @TestTransaction
    public void testAtualizar() {
        ServidorTemporario entity = new ServidorTemporario();
        entity.setNome("Servidor Original");
        em.persist(entity);

        ServidorTemporarioRequestDto requestDto = new ServidorTemporarioRequestDto();
        requestDto.setNome("Servidor Atualizado");

        ServidorTemporarioResponseDto responseDto = servico.atualizar(entity.getId(), requestDto);

        assertNotNull(responseDto);
        assertEquals("Servidor Atualizado", responseDto.getNome());

        ServidorTemporario updatedEntity = em.find(ServidorTemporario.class, entity.getId());
        assertNotNull(updatedEntity);
        assertEquals("Servidor Atualizado", updatedEntity.getNome());
    }

    @Test
    @TestTransaction
    public void testExcluir() {
        ServidorTemporario entity = new ServidorTemporario();
        entity.setNome("Servidor para Exclusão");
        em.persist(entity);

        Long id = entity.getId();

        ServidorTemporario existingEntity = em.find(ServidorTemporario.class, id);
        assertNotNull(existingEntity);

        servico.excluir(id);

        ServidorTemporario deletedEntity = em.find(ServidorTemporario.class, id);
        assertNull(deletedEntity);
    }


    @Test
    @TestTransaction
    public void testConsultaCompleta() {
        ServidorTemporario entity = new ServidorTemporario();
        entity.setNome("Servidor Completo");
        em.persist(entity);

        Long id = entity.getId();

        ServidorTemporarioResponseDto responseDto = servico.consultaCompleta(id);

        assertNotNull(responseDto);
        assertEquals("Servidor Completo", responseDto.getNome());
    }
}
