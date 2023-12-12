package com.myytcollection.dto;

import java.util.Set;

public class SearchFilterDTO {
    private String query;
    private Set<TagDTO> tags;
    private int page;
    private int pageSize;

    public SearchFilterDTO(String query, Set<TagDTO> tags, int page, int pageSize) {
        this.query = query;
        this.tags = tags;
        this.page = page;
        this.pageSize = pageSize;
    }

    public SearchFilterDTO() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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
                ", page=" + page +
                ", pageSize=" + pageSize +
                '}';
    }
}
