package com.gsugambit.partydjserver.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.youtube.model.Thumbnail;
import com.gsugambit.partydjserver.utils.DurationUtils;

public class YoutubeSearchResultDto {

	private String duration;
	private ThumbnailDto thumbnail;
	private String title;
	private String videoId;
	
	
	public YoutubeSearchResultDto(String videoId, Thumbnail thumbnail, String title) {
		this.videoId = videoId;
		this.thumbnail = ThumbnailDto.convert(thumbnail);
		this.title = title;
	}

	public String getDurationConverted() {
		return duration == null ? null : DurationUtils.convert(duration);
	}
	
	public String getDuration() {
		return duration;
	}
	
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public ThumbnailDto getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(ThumbnailDto thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	static class ThumbnailDto {
		private long height;
		private String url;
		private long width;
		
		public ThumbnailDto(long height, String url, long width) {
			this.height = height;
			this.url = url;
			this.width = width;
		}

		public static ThumbnailDto convert(Thumbnail thumbnail) {
			return new ThumbnailDto(thumbnail.getHeight(), thumbnail.getUrl(), thumbnail.getWidth());
		}
		
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public long getHeight() {
			return height;
		}
		public void setHeight(long height) {
			this.height = height;
		}
		public long getWidth() {
			return width;
		}
		public void setWidth(long width) {
			this.width = width;
		}
		
		@Override
		public String toString() {
			try {
				return new ObjectMapper().writeValueAsString(this);
			} catch (JsonProcessingException e) {
				return super.toString();
			}
		}
		
	}
	
	@Override
	public String toString() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return super.toString();
		}
	}
}
