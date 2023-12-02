package com.myytcollection.dto;

import java.util.Set;

public class SearchFilterDTO {
    private String query;
    private Set<TagDTO> tags;
    private int pageNumber;
    private int pageSize;

    public SearchFilterDTO(String query, Set<TagDTO> tags, int pageNumber, int pageSize) {
        this.query = query;
        this.tags = tags;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public SearchFilterDTO() {
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "SearchFilterDTO{" +
                "query='" + query + '\'' +
                ", tags=" + tags +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                '}';
    }
}
