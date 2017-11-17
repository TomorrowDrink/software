package com.code.Entity;

import java.io.Serializable;
import java.util.List;

public class JsonResponse<T extends Serializable> {
    /**
     * Current page
     */
    private int page;

    /**
     * Total pages
     */
    private int total;

    /**
     * Total number of records
     */
    private int records;

    /**
     * Contains the actual data
     */
    private List<T> rows;

    public JsonResponse() {

    }

    public JsonResponse(List<T> rows, PageInfo page) {
        this.rows = rows;
        this.total = page.getTotalPage();
        this.records = page.getTotalResult();
        this.page = page.getCurrentPage();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
