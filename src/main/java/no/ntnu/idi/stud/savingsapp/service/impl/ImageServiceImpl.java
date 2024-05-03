package no.ntnu.idi.stud.savingsapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.stud.savingsapp.exception.image.ImageNotFoundException;
import no.ntnu.idi.stud.savingsapp.model.image.Image;
import no.ntnu.idi.stud.savingsapp.repository.ImageRepository;
import no.ntnu.idi.stud.savingsapp.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the ImageService interface for handling image storage and retrieval.
 */
@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageRepository imageRepository;

	/**
	 * Saves an image to the database.
	 * @param name The name of the image file.
	 * @param data The binary data of the image.
	 * @return The persisted Image object, containing the image's metadata and identifier.
	 */
	@Override
	public Image saveImage(String name, byte[] data) {
		Image image = new Image();
		image.setName(name);
		image.setData(data);
		return imageRepository.save(image);
	}

	/**
	 * Retrieves an image from the database by its ID.
	 * @param id The unique identifier of the image to be retrieved.
	 * @return The Image object containing all relevant data.
	 * @throws ImageNotFoundException if no image is found with the provided ID.
	 */
	@Override
	public Image getImage(long id) {
		Optional<Image> optionalImage = imageRepository.findById(id);
		if (optionalImage.isPresent()) {
			return optionalImage.get();
		}
		else {
			log.error("[ImageService:getImage] Image is not found, id: {}", id);
			throw new ImageNotFoundException();
		}
	}

}
