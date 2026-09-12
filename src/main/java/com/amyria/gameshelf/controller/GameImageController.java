package com.amyria.gameshelf.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amyria.gameshelf.dto.GameImageRequest;
import com.amyria.gameshelf.dto.GameImageResponse;
import com.amyria.gameshelf.service.GameImageService;

@RestController
@RequestMapping("api/games/images")
@CrossOrigin(origins = "*") 
public class GameImageController {
	
	private final GameImageService gameImageService;
	
	public GameImageController(GameImageService gameImageService) {
		this.gameImageService = gameImageService;
	}
	
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<GameImageResponse> uploadGameImage(@RequestPart("file") MultipartFile img, @RequestPart("request") GameImageRequest request){
		try {
			return ResponseEntity.ok().body(gameImageService.uploadImage(img, request.getGameId()));
		}
		catch(IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}

}
