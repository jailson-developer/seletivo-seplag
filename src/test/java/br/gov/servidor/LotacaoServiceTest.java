package br.gov.servidor;

import br.gov.servidor.modules.servidor.dtos.LotacaoRequestDto;
import br.gov.servidor.modules.servidor.dtos.LotacaoResponseDto;
import br.gov.servidor.modules.servidor.models.Lotacao;
import br.gov.servidor.modules.servidor.services.LotacaoService;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class LotacaoServiceTest {

    @Inject
    LotacaoService service;


    private LotacaoRequestDto lotacaoDto;

    @BeforeEach
    void setup() {
        lotacaoDto = new LotacaoRequestDto();
        lotacaoDto.setDataLotacao(LocalDate.of(2020, 2, 6));
    }

    @Test
    @TestTransaction
    void salvar_devePersistirLotacaoERetornarDto() {
        LotacaoResponseDto response = service.salvar(lotacaoDto);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getDataLotacao()).isEqualTo(lotacaoDto.getDataLotacao());

        Lotacao lotacaoSalva = Lotacao.findById(response.getId());
        assertThat(lotacaoSalva).isNotNull();
    }

    @Test
    @TestTransaction
    void editar_deveAtualizarLotacaoExistente() {
        LotacaoResponseDto saved = service.salvar(lotacaoDto);
        Long id = saved.getId();

        LotacaoRequestDto updateDto = new LotacaoRequestDto();
        updateDto.setDataLotacao(LocalDate.now());

        LotacaoResponseDto updated = service.editar(id, updateDto);

        assertThat(updated).isNotNull();
        assertThat(updated.getId()).isEqualTo(id);
        assertThat(updated.getDataLotacao()).isEqualTo(LocalDate.of(2020, 2, 6));

        Lotacao lotacaoAtualizada = Lotacao.findById(id);
        assertThat(lotacaoAtualizada.getDataLotacao()).isEqualTo(LocalDate.of(2020, 2, 6));
    }

    @Test
    @TestTransaction
    void findById_deveRetornarLotacaoQuandoExistir() {
        LotacaoResponseDto saved = service.salvar(lotacaoDto);

        LotacaoResponseDto found = service.findById(saved.getId());

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(saved.getId());
        assertThat(found.getDataLotacao()).isEqualTo(saved.getDataLotacao());
    }

    @Test
    @TestTransaction
    void deleteById_deveRemoverLotacao() {
        LotacaoResponseDto saved = service.salvar(lotacaoDto);
        Long id = saved.getId();

        service.deleteById(id);

        Lotacao lotacaoRemovida = Lotacao.findById(id);
        assertThat(lotacaoRemovida).isNull();
    }

    @Test
    @TestTransaction
    void findByParams_deveRetornarListaPaginada() {
        service.salvar(lotacaoDto);

        List<LotacaoResponseDto> result = service.findByParams(null, null).getContent();

        assertThat(result).isNotEmpty();
        assertThat(result.getFirst().getDataLotacao()).isEqualTo(lotacaoDto.getDataLotacao());
    }
}
