package de.htwberlin.schbuet.application.errors;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 3179807692483504424L;

	public ProductNotFoundException(UUID uuid) {
		this(uuid, null);
    }
	
	public ProductNotFoundException(UUID uuid, Throwable inner) {
        super(String.format("The requested product with id: '%s' could not be found", uuid), inner);
    }
}