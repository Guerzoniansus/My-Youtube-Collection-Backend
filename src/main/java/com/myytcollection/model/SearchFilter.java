package com.myytcollection.model;

import com.myytcollection.dto.TagDTO;

import java.util.Set;

public class SearchFilter {
    private String query;
    private Set<Tag> tags;
    private int pageNumber;
    private int pageSize;

    public SearchFilter(String query, Set<Tag> tags, int pageNumber, int pageSize) {
        this.query = query;
        this.tags = tags;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public SearchFilter() {
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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "SearchFilter{" +
                "query='" + query + '\'' +
                ", tags=" + tags +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                '}';
    }
}
