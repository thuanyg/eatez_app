package com.thuanht.eatez.model;

public class Pagination {
    private int currentPage;
    private int totalPage;
    private int totalItem;

    public Pagination(int currentPage, int totalPage, int totalItem) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.totalItem = totalItem;
    }

    public Pagination() {
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", totalItem=" + totalItem +
                '}';
    }
}
