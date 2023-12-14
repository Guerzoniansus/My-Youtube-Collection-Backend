package com.myytcollection.model;

import java.util.Set;

public class SearchFilter {
    private String query;
    private Set<Tag> tags;
    private int page;
    private int pageSize;

    public SearchFilter(String query, Set<Tag> tags, int page, int pageSize) {
        this.query = query;
        this.tags = tags;
        this.page = page;
        this.pageSize = pageSize;
    }

    public SearchFilter() {
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
                ", page=" + page +
                ", pageSize=" + pageSize +
                '}';
    }
}
