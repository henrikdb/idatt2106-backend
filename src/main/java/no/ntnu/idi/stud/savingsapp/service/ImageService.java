package no.ntnu.idi.stud.savingsapp.service;

import no.ntnu.idi.stud.savingsapp.model.image.Image;
import org.springframework.stereotype.Service;

/**
 * Service interface for image-related operations.
 */
@Service
public interface ImageService {

  /**
   * Saves an image in the data store.
   *
   * @param name The name of the image file.
   * @param data The binary data of the image.
   * @return The Image object representing the saved image, including its metadata.
   */
  Image saveImage(String name, byte[] data);

  /**
   * Retrieves an image from the data store by its unique identifier.
   *
   * @param id The unique identifier of the image to retrieve.
   * @return The Image object containing all relevant data about the image,
   *         including its binary data.
   */
  Image getImage(long id);
}
