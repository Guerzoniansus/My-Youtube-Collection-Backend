package com.myytcollection.mapper;

import com.myytcollection.dto.SearchFilterDTO;
import com.myytcollection.dto.TagDTO;
import com.myytcollection.model.SearchFilter;
import com.myytcollection.model.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchFilterMapperTest {

    @InjectMocks
    private SearchFilterMapper searchFilterMapper;
    @Mock
    private TagMapper tagMapper;

    @Test
    public void testToModel_Success() {
        TagDTO mockTagDTO = mock(TagDTO.class);
        Tag mockTag = mock(Tag.class);
        when(tagMapper.fromDatabase(mockTagDTO)).thenReturn(mockTag);

        Set<TagDTO> tagDTOs = new HashSet<>();
        tagDTOs.add(mockTagDTO);
        SearchFilterDTO searchFilterDTO = new SearchFilterDTO("query", tagDTOs, 1, 10);

        SearchFilter result = searchFilterMapper.toModel(searchFilterDTO);

        assertEquals("query", result.getQuery());
        assertEquals(1, result.getPage());
        assertEquals(10, result.getPageSize());
        assertEquals(Set.of(mockTag), result.getTags());
    }

    @Test
    public void testToModel_NullTags_ReturnsEmptyTags() {
        SearchFilterDTO searchFilterDTO = new SearchFilterDTO("query", null, 1, 10);
        SearchFilter result = searchFilterMapper.toModel(searchFilterDTO);

        assertEquals("query", result.getQuery());
        assertEquals(1, result.getPage());
        assertEquals(10, result.getPageSize());
        assert(result.getTags()).isEmpty();
    }

    @Test
    public void testToModel_EmptyTags() {
        SearchFilterDTO searchFilterDTO = new SearchFilterDTO("query", new HashSet<>(), 1, 10);
        SearchFilter result = searchFilterMapper.toModel(searchFilterDTO);

        assertEquals("query", result.getQuery());
        assertEquals(1, result.getPage());
        assertEquals(10, result.getPageSize());
        Collections Collections;
        assert(result.getTags()).isEmpty();
    }

}
