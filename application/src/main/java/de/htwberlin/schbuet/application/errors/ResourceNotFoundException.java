package de.htwberlin.schbuet.application.errors;

import java.util.UUID;

public class ResourceNotFoundException extends Exception {
	private static final long serialVersionUID = 3179807692483504424L;

	public ResourceNotFoundException(UUID uuid) {
        super("The requested resource (ID:" + uuid + ") could not be found");
    }
}