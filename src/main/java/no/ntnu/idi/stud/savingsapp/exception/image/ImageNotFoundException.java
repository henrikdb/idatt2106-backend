package no.ntnu.idi.stud.savingsapp.exception.image;

/**
 * Exception thrown when attempting to retrieve an image that does not exist in the
 * system.
 */
public final class ImageNotFoundException extends RuntimeException {

	/**
	 * Constructs a ImageNotFoundException with the default message.
	 */
	public ImageNotFoundException() {
		super("Image not found");
	}

}
