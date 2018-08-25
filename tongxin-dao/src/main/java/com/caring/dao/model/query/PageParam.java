package com.caring.dao.model.query;

/**
 *
 * @author james
 */
public class PageParam<T> {

    private boolean detail;
    private int size;
    private int number;
    private T filter;

    public PageParam() {
    }

    public PageParam(PageParam param, T filter) {
        if (param != null) {
            this.size = param.getSize();
            this.number = param.getNumber();
        }
        this.filter = filter;
    }

    public boolean isDetail() {
        return detail;
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public T getFilter() {
        return filter;
    }

    public void setFilter(T filter) {
        this.filter = filter;
    }

    public boolean hasFilter() {
        return filter != null;
    }

}
