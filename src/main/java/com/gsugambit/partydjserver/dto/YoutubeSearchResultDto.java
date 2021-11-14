package com.gsugambit.partydjserver.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.youtube.model.Thumbnail;
import com.gsugambit.partydjserver.utils.DurationUtils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Data
public class YoutubeSearchResultDto {

	private String duration;
	private ThumbnailDto thumbnail;
	private String title;
	private String videoId;
	
	public YoutubeSearchResultDto(String duration, Thumbnail thumbnail, String title, String videoId) {
		this.duration = duration;
		this.videoId = videoId;
		this.thumbnail = ThumbnailDto.convert(thumbnail);
		this.title = title;
	}

	
	public YoutubeSearchResultDto(Thumbnail thumbnail, String title, String videoId) {
		this.videoId = videoId;
		this.thumbnail = ThumbnailDto.convert(thumbnail);
		this.title = title;
	}

	public String getDurationConverted() {
		return duration == null ? null : DurationUtils.convert(duration);
	}
	
	@Builder
	@AllArgsConstructor(access = AccessLevel.PACKAGE)
	@NoArgsConstructor
	@Data
	static class ThumbnailDto {
		private long height;
		private String url;
		private long width;

		public static ThumbnailDto convert(Thumbnail thumbnail) {
			return new ThumbnailDto(thumbnail.getHeight(), thumbnail.getUrl(), thumbnail.getWidth());
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
