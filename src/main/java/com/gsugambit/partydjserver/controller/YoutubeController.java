package com.gsugambit.partydjserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gsugambit.partydjserver.dto.YoutubeSearchResultDto;
import com.gsugambit.partydjserver.service.YoutubeService;

@Profile("youtube")
@RestController
@RequestMapping("/api/v1/youtube")
public class YoutubeController {
	
	private final YoutubeService youtubeService;

	@Autowired
	public YoutubeController(YoutubeService youtubeService) {
		this.youtubeService = youtubeService;
	}
	
	@GetMapping("/search")
	public List<YoutubeSearchResultDto> search(@RequestParam("v") String v) {
		return youtubeService.search(v);
	}
}
