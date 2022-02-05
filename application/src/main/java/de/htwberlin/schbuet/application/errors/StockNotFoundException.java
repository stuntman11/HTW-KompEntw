package de.htwberlin.schbuet.application.errors;

import java.util.UUID;

public class StockNotFoundException extends Exception {
	private static final long serialVersionUID = 5127597983815265458L;

	public StockNotFoundException(UUID uuid) {
		this(uuid, null);
    }
	
	public StockNotFoundException(UUID uuid, Throwable inner) {
        super(String.format("The requested stock with id: '%s' could not be found", uuid), inner);
    }
}