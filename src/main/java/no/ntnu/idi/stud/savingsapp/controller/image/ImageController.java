package no.ntnu.idi.stud.savingsapp.controller.image;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idi.stud.savingsapp.model.image.Image;
import no.ntnu.idi.stud.savingsapp.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Controller for handling image upload and retrieval.
 */
@CrossOrigin
@RestController
@Validated
@RequestMapping("/api/image")
@EnableAutoConfiguration
@Tag(name = "Image")
public class ImageController {

	@Autowired
	private ImageService imageService;

	/**
	 * Uploads an image to the server and stores it. The image data is extracted from a
	 * multipart file upload.
	 * @param file The MultipartFile object containing the image to be uploaded.
	 * @return ResponseEntity containing the unique ID of the stored image.
	 * @throws IOException If an error occurs while reading the file data.
	 */
	@Operation(summary = "Upload an image", description = "Upload an image to the server")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully uploaded the image") })
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/upload")
	public ResponseEntity<Long> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
		byte[] imageBytes = file.getBytes();
		Image image = imageService.saveImage(file.getName(), imageBytes);
		return ResponseEntity.ok().body(image.getId());
	}

	/**
	 * Retrieves an image from the server based on its unique ID. Returns the image data
	 * as a byte array, suitable for direct display in web browsers or image views.
	 * @param id The unique identifier of the image to retrieve.
	 * @return ResponseEntity containing the raw image data as a byte array.
	 */
	@Operation(summary = "Retrieve an image", description = "Retrieve an image from the server")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved the image") })
	@GetMapping(value = "/{id}")
	public ResponseEntity<byte[]> getImage(@PathVariable long id) {
		Image image = imageService.getImage(id);
		return ResponseEntity.ok().body(image.getData());
	}

}
