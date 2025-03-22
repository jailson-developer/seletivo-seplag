package br.gov.servidor.core.pagination;

import io.quarkus.panache.common.Page;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequest {

    @QueryParam("page")
    private int page;
    @QueryParam("size")
    private int size;

    public PageRequest() {
        this.page = 0;
        this.size = 10;
    }

    public PageRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public Page toPage() {
        validate();
        return Page.of(page, size);
    }

    public void validate() {
        if (page < 0) {
            throw new IllegalArgumentException("O número da página não pode ser negativo");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("O tamanho da página deve ser maior que zero");
        }
    }
}
