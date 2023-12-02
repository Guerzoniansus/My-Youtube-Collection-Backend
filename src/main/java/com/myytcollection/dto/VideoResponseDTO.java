package com.myytcollection.dto;

import java.util.List;

public class VideoResponseDTO {
    private List<VideoDTO> videos;
    private int totalVideos;

    public VideoResponseDTO() {
    }

    public VideoResponseDTO(List<VideoDTO> videos, int totalVideos) {
        this.videos = videos;
        this.totalVideos = totalVideos;
    }

    public List<VideoDTO> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoDTO> videos) {
        this.videos = videos;
    }

    public int getTotalVideos() {
        return totalVideos;
    }

    public void setTotalVideos(int totalVideos) {
        this.totalVideos = totalVideos;
    }
}
