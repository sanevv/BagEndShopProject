package com.github.semiprojectshop.repository.aery.productSearch.domain;

public class ProductSearchVO {

    private String keyword;  // 검색어
    private int currentPage; // 현재 페이지 번호
    private int sizePerPage; // 페이지당 보여줄 개수
    private String sortType; // 정렬 조건

    public ProductSearchVO() {}

    public ProductSearchVO(String keyword, int currentPage, int sizePerPage, String sortType) {
        this.keyword = keyword;
        this.currentPage = currentPage;
        this.sizePerPage = sizePerPage;
        this.sortType = sortType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getSizePerPage() {
        return sizePerPage;
    }

    public void setSizePerPage(int sizePerPage) {
        this.sizePerPage = sizePerPage;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    // Offset 계산 (페이징용)
    public int getOffset() {
        return (currentPage - 1) * sizePerPage;
    }

}