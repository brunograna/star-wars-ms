package com.recruitment.challenge.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class PaginateDto<T> implements Page<T> {

    private int page;
    private int perPage;
    private int totalPages;
    private int countElements;
    private List<T> content;

    public PaginateDto(int page,
                       int perPage,
                       int totalPages,
                       int countElements,
                       List<T> content) {
        this.page = page;
        this.perPage = perPage;
        this.totalPages = totalPages;
        this.countElements = countElements;
        this.content = content;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public long getTotalElements() {
        return countElements;
    }

    @Override
    public int getNumber() {
        return page;
    }

    @Override
    public int getSize() {
        return perPage;
    }

    @Override
    public int getNumberOfElements() {
        return content.size();
    }

    @Override
    public List<T> getContent() {
        return content;
    }

    @Override
    public boolean hasContent() {
        return content != null && content.size() > 0;
    }

    @Override
    public Sort getSort() {
        return null;
    }

    @Override
    public boolean isFirst() {
        return page == 0;
    }

    @Override
    public boolean isLast() {
        return page == totalPages;
    }

    @Override
    public boolean hasNext() {
        return page != totalPages;
    }

    @Override
    public boolean hasPrevious() {
        return page != 0;
    }

    @Override
    public Pageable nextPageable() {
        return page == totalPages ? Pageable.unpaged() : PageRequest.of(page + 1, perPage);
    }

    @Override
    public Pageable previousPageable() {
        return page == 0 ? Pageable.unpaged() : PageRequest.of(page - 1, perPage);
    }

    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }

    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        return null;
    }
}
