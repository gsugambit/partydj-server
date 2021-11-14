package com.gsugambit.partydjserver.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.VideoListResponse;
import com.gsugambit.partydjserver.dto.YoutubeSearchResultDto;

@Profile("youtube")
@Service
public class YoutubeService {

	private static final long NUMBER_OF_VIDEOS_RETURNED = 10;

	private final String apiKey;
	private final YouTube youtube;

	@Autowired
	public YoutubeService(@Value("${youtube.api-key}") String apiKey, NetHttpTransport netHttpTransport,
			JacksonFactory jacksonFactory, HttpRequestInitializer httpRequestInitializer) {
		this.apiKey = apiKey;
		youtube = new YouTube.Builder(netHttpTransport, jacksonFactory, httpRequestInitializer)
				.setApplicationName("youtube-cmdline-search-sample").build();
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(YoutubeService.class);

	public List<YoutubeSearchResultDto> search(String searchTerm) {
		LOGGER.info("Searching for videos with title: {} with apiKey: {}", searchTerm, apiKey);
		try {
			// Define the API request for retrieving search results.
			YouTube.Search.List search = youtube.search().list("id,snippet");
			search.setKey(apiKey);
			search.setQ(searchTerm);
			// Restrict the search results to only include videos. See:
			// https://developers.google.com/youtube/v3/docs/search/list#type
			search.setType("video");
			search.setVideoEmbeddable("true");
			// To increase efficiency, only retrieve the fields that the
			// application uses.
			search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default)");
			search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

			// Call the API and print results.
			SearchListResponse searchResponse = search.execute();
			List<SearchResult> searchResultList = searchResponse.getItems();
			if (!CollectionUtils.isEmpty(searchResultList)) {
				List<YoutubeSearchResultDto> dtos = searchResultList.stream()
						.map(result -> new YoutubeSearchResultDto(result.getSnippet().getThumbnails().getDefault(),
								result.getSnippet().getTitle(),
								result.getId().getVideoId()))								
						.collect(Collectors.toList());
				
				String ids = dtos.stream().map(dto -> dto.getVideoId()).collect(Collectors.joining(","));
				
				com.google.api.services.youtube.YouTube.Videos.List videoSearch = youtube.videos().list("id,contentDetails");
				videoSearch.setKey(apiKey);
				videoSearch.setId(ids);
				videoSearch.setFields("items(id,contentDetails/duration)");
				
				VideoListResponse videoListResponse = videoSearch.execute();
				videoListResponse.getItems().forEach(video -> {
					LOGGER.info("{}: {}", video.getId(), video.toString());
					String videoId = video.getId();
					var optional = dtos.stream().filter(dto -> dto.getVideoId().equals(videoId)).findFirst();
					if(optional.isPresent()) {
						optional.get().setDuration(video.getContentDetails().getDuration());
					}
				});
				return dtos;
			} else {
				new ArrayList<>();
			}
			
			
		} catch (GoogleJsonResponseException e) {
			System.err.println(
					"There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
		} catch (IOException e) {
			System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		return new ArrayList<YoutubeSearchResultDto>();
	}
}
