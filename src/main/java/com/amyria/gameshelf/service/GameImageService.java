package com.amyria.gameshelf.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amyria.gameshelf.dto.GameImageResponse;
import com.amyria.gameshelf.exception.GameNotFoundException;
import com.amyria.gameshelf.exception.InvalidGameException;
import com.amyria.gameshelf.exception.InvalidImageException;
import com.amyria.gameshelf.model.Game;
import com.amyria.gameshelf.model.GameImage;
import com.amyria.gameshelf.repository.GameImageRepository;
import com.amyria.gameshelf.repository.GameRepository;

@Service
public class GameImageService {
	
	private final GameImageRepository gameImageRepository;
	private final GameRepository gameRepository;
	private final Set<String> validImageExtensions = new HashSet<String>(Arrays.asList("jpg", "jpeg", "png", "webp"));
	
	@Value("${file.upload-dir}")
	private String uploadDir;
	
	public GameImageService(GameImageRepository gameImageRepository, GameRepository gameRepository) {
		this.gameImageRepository = gameImageRepository;
		this.gameRepository = gameRepository;
	}
	
	@Transactional
	public GameImageResponse uploadImage(MultipartFile img, Integer gameId) throws IOException {
		
		Path path = Paths.get(uploadDir);
		
		//Create the path if it isn't there already
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
		
		//Check that the gameId is valid
		Optional<Game> game = gameRepository.findById(gameId);
		if (game.isEmpty()) {
			throw new InvalidImageException("Invalid image - game with ID " + gameId + " does not exist.");
		}
		
		//Check if file is empty
		if (img.isEmpty()) {
			throw new InvalidImageException("Invalid image - file is empty");
		}
		
		//Check if filename is null
		String originalFileName = img.getOriginalFilename();
		if (originalFileName == null) {
			throw new InvalidImageException("Invalid image - file name is null");
		}
		
		//Validate the content type. Non-images should be rejected
		String contentType = img.getContentType();
		if (contentType == null || !contentType.startsWith("image/")) {
			throw new InvalidImageException("Invalid image - the file is not an image file");
		}
		
		//Check if file extension matches the content type
		String contentTypeExtension = contentType.substring(contentType.lastIndexOf("/") + 1).toLowerCase();
		int fileNameExtensionIndex = originalFileName.lastIndexOf(".");
		//Handle no file extension 
		if(fileNameExtensionIndex == -1) {
			throw new InvalidImageException("Invalid image - file has no extension");
		}
		String fileNameExtension = originalFileName.substring(fileNameExtensionIndex + 1).toLowerCase();
		if (!contentTypeExtension.equals(fileNameExtension)) {
			throw new InvalidImageException("Invalid image - content type and file extension do not match.");
		}
		if (!validImageExtensions.contains(fileNameExtension)) {
			throw new InvalidImageException("Invalid image - unsupported file extension. Valid types are: png, jpg, jpeg, webp");
		}
		
		//Construct the filename. format is <UUID>.jpg (or whichever extension is being used)
		String fileName = UUID.randomUUID().toString() + "." + contentTypeExtension;
		Path target = path.resolve(fileName.toString());
		Files.copy(img.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
		
		GameImage gameImg;
		
		//Check if the game already has an image
		Optional<GameImage> existingImg = gameImageRepository.findByGameId(gameId);
		boolean imageExists = existingImg.isPresent();
		String oldImagePath = null;
		if (imageExists) {
			gameImg = existingImg.get();
			oldImagePath = gameImg.getImage_path();
		}
		else {
			gameImg = new GameImage();
			gameImg.setGame(game.get());
		}
		gameImg.setImage_path(uploadDir+"/"+fileName);
		
		try {
			gameImageRepository.saveAndFlush(gameImg);
		}
		catch (Exception e) {
			Files.deleteIfExists(target);
			throw e;
		}
		
		if(imageExists) {
			Files.deleteIfExists(Paths.get(oldImagePath));
		}
		
		return new GameImageResponse(gameImg);
		
	}
	
	public GameImageResponse deleteImageForGame(int gameId) throws IOException {
		
		Optional<Game> optionalGame = gameRepository.findById(gameId);
		if(optionalGame.isEmpty()) {
			throw new GameNotFoundException("Delete failed - game with ID " + gameId + " does not exist.");
		}
		Optional<GameImage> optionalGameImage = gameImageRepository.findByGameId(gameId);
		if(optionalGameImage.isEmpty()) {
			throw new InvalidGameException("Delete failed - game with ID " + gameId + " has no associated image.");
		}
		GameImageResponse response = new GameImageResponse(optionalGameImage.get());
		
		//Delete the image from disk
		Path filePath = Paths.get(optionalGameImage.get().getImage_path());
		if (!Files.deleteIfExists(filePath)) {
			gameImageRepository.delete(optionalGameImage.get());
			// This could be regarded as a success but I am leaving it for now; it will be clearer if this situation occurs
			throw new InvalidImageException("No image was found to be deleted. Database cleared");
		}
		//Then delete from DB
		gameImageRepository.delete(optionalGameImage.get());
		return response;	
	}
	
	public Optional<GameImageResponse> getImageForGame(int gameId) {
		Optional<Game> optionalGame = gameRepository.findById(gameId);
		if (optionalGame.isEmpty()) {
			throw new GameNotFoundException("Cannot get image - game with ID " + gameId + " does not exist");
		}
		return gameImageRepository.findByGameId(gameId).map(GameImageResponse::new);
	}

}
