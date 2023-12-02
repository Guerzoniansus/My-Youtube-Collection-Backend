package com.myytcollection.repository;

import com.myytcollection.model.Tag;
import com.myytcollection.model.User;
import com.myytcollection.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface VideoRepository extends JpaRepository<Video, Integer> {
    Set<Video> findByUserOrderByDateCreatedDesc(User user);

    /**
     * Gets the videos of a user. The query and tags are optional, if you use null as argument then they will be ignored.
     * A video only needs to match ONE of the titles AND the tags.
     * @param user The user to retrieve video's from.
     * @param query Will be used for searching by title and alternative title. This is not case-insensitive.
     *              You can pass null or an empty string, in which case the query will simply be ignored.
     * @param tagIds The tags to search for. The video must include all of these tags.
     *               The query uses tagIds because regular tags don't work.
     *               If there are no tags, make sure to pass null, so they can be ignored.
     *               The query cannot check for an empty collection of tags.
     * @param pageable An object used to determine the page size and page number.
     * @return A list of videos, or null if no matches were found.
     */
    @Query("SELECT v FROM Video v " +
            "WHERE (:user IS NULL OR v.user = :user) " +
            "AND (:tagIds IS NULL " +
            "OR NOT EXISTS (SELECT t FROM Tag t WHERE t.tagID IN :tagIds AND NOT EXISTS (SELECT 1 FROM v.tags vt WHERE vt.tagID = t.tagID))) " +
            "AND (:query IS NULL OR :query = '' OR LOWER(v.title) LIKE %:query% OR LOWER(v.alternativeTitle) LIKE %:query%) " +
            "ORDER BY v.dateCreated DESC")
    Page<Video> getVideos(@Param("user") User user,
                          @Param("query") String query,
                          @Param("tagIds") Set<Integer> tagIds,
                          Pageable pageable);
}
