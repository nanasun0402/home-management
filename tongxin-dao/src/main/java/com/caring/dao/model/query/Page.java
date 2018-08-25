package com.caring.dao.model.query;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author james
 */
public class Page<T> {

    private List<T> content;
    private int size;
    private long total;
    private long number;
    private long pages;

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public Page<T> withSize(int size) {
        setSize(size);
        return this;
    }

    public Page<T> withNumber(long number) {
        setNumber(number);
        return this;
    }

    public Page<T> withTotal(long total) {
        setTotal(total);
        return this;
    }

    public Page<T> withPages(long pages) {
        setPages(pages);
        return this;
    }

    public Page<T> withContent(List<T> content) {
        setContent(content);
        return this;
    }

    public static <T> Page<T> create(List<T> content) {
        Page<T> page = new Page<>();
        page.setContent(content);
        page.setNumber(content.size());
        page.setPages(1);
        page.setTotal(content.size());
        page.setSize(content.size());
        return page;
    }

    public <S extends Object> Page<S> map(Function<? super T, ? extends S> mapper) {
        return new Page<S>()
                .withNumber(this.getNumber())
                .withPages(this.getPages())
                .withSize(this.getSize())
                .withTotal(this.getTotal())
                .withContent(this.getContent().stream().map(mapper).collect(Collectors.toList()));
    }
}
