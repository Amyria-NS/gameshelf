package com.amyria.gameshelf.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amyria.gameshelf.dto.GameImageRequest;
import com.amyria.gameshelf.dto.GameImageResponse;
import com.amyria.gameshelf.exception.FileNotImageException;
import com.amyria.gameshelf.model.Game;
import com.amyria.gameshelf.model.GameImage;
import com.amyria.gameshelf.repository.GameImageRepository;
import com.amyria.gameshelf.repository.GameRepository;

@Service
public class GameImageService {
	
	private final GameImageRepository gameImageRepository;
	private final GameRepository gameRepository;
	
	@Value("${file.upload-dir}")
	private String uploadDir;
	
	public GameImageService(GameImageRepository gameImageRepository, GameRepository gameRepository) {
		this.gameImageRepository = gameImageRepository;
		this.gameRepository = gameRepository;
	}
	
	public GameImageResponse uploadImage(MultipartFile img, Integer gameId) throws IOException {
		
		Path path = Paths.get(uploadDir);
		
		//Just create the path if it isn't there already
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
		
		//Check that the gameId is valid
		Optional<Game> game = gameRepository.findById(gameId);
		if (game.isEmpty()) {
			//Throw exception
		}
		
		//Validate the content type. Non-images should be rejected
		String contentType = img.getContentType();
		if (contentType == null || !contentType.startsWith("image/")) {
			throw new FileNotImageException("Upload failed. The file must be an image file");
		}
		
		
		//Construct the filename. format is <UUID>.jpg (or whichever extension is being used)
		StringBuilder fileName = new StringBuilder(UUID.randomUUID().toString());
		String originalFileName = img.getOriginalFilename();
		int extensionIndex = originalFileName.lastIndexOf(".");
		fileName.append(originalFileName.substring(extensionIndex));
		
		Path target = path.resolve(fileName.toString());
		Files.copy(img.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
		
		GameImage gameImg = new GameImage();
		gameImg.setGame(game.get());
		gameImg.setImage_path(uploadDir+"/"+fileName.toString());
		gameImageRepository.save(gameImg);
		
		return new GameImageResponse(gameImg);
		
	}

}
