package com.amyria.gameshelf.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amyria.gameshelf.dto.GameImageRequest;
import com.amyria.gameshelf.dto.GameImageResponse;
import com.amyria.gameshelf.service.GameImageService;

/**
 * REST controller for managing the images associated with games in the GameShelf library
 * 
 * Handles HTTP requests for uploading, deleting and retrieving images. Note that getGameImage retrieves the image details and not the image itself.
 * @author Amyria-NS
 */

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
	
	@DeleteMapping("/{gameId}")
	public ResponseEntity<GameImageResponse> deleteGameImage(@PathVariable Integer gameId){
		try {
			return ResponseEntity.ok().body(gameImageService.deleteImageForGame(gameId));
		}
		catch (IOException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/{gameId}")
	public ResponseEntity<GameImageResponse> getGameImage(@PathVariable Integer gameId){
		Optional<GameImageResponse> gameImage = gameImageService.getImageForGame(gameId);
		if (gameImage.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		else {
			return ResponseEntity.ok(gameImage.get());
		}
	}

}
