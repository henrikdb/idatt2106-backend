package no.ntnu.idi.stud.savingsapp.exception.image;

public class ImageNotFoundException extends RuntimeException {

	/**
	 * Constructs a ImageNotFoundException with the default message.
	 */
	public ImageNotFoundException() {
		super("Image not found");
	}

}
