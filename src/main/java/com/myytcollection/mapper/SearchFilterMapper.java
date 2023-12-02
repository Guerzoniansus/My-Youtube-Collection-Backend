package com.myytcollection.mapper;

import com.myytcollection.dto.SearchFilterDTO;
import com.myytcollection.model.SearchFilter;
import com.myytcollection.model.Tag;
import com.myytcollection.repository.TagRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SearchFilterMapper {
    private final TagMapper tagMapper;

    public SearchFilterMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    /**
     * Maps a search filter DTO to a SearchFilter.
     * @param searchFilterDTO The dto to map.
     * @return A SearchFilter with the tag DTOs replaced with Tag models.
     */
    public SearchFilter toModel(SearchFilterDTO searchFilterDTO) {
        Set<Tag> tags = searchFilterDTO.getTags() == null
                ? new HashSet<>()
                : searchFilterDTO.getTags().stream().map(tagMapper::fromDatabase).collect(Collectors.toSet());
        return new SearchFilter(searchFilterDTO.getQuery(), tags, searchFilterDTO.getPageNumber(), searchFilterDTO.getPageSize());
    }
}
