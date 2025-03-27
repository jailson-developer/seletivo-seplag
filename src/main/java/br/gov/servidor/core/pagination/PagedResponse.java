package br.gov.servidor.core.pagination;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.function.Function;

@Getter
@Setter
public class PagedResponse<T> {

    private long totalElements;
    private int totalPages;
    private int size;
    private int page;
    private List<T> content;

    public <J, D> PagedResponse(List<T> content, long totalElements, int totalPages, int size, int page, Function<J, D> mapper) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.size = size;
        this.page = page;
    }

    public <A> PagedResponse(PanacheQuery<A> query, PageRequest pageRequest, Function<A, T> mapper) {
        List<A> list = query.page(pageRequest.toPage()).list();
        this.page = pageRequest.getPage();
        this.totalElements = query.count();
        this.totalPages = query.pageCount();
        this.size = pageRequest.getSize();
        this.content = list.stream()
                .map(mapper)
                .toList();
    }
}
