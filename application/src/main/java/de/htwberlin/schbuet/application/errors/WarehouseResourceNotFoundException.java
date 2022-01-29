package de.htwberlin.schbuet.application.errors;

import java.util.UUID;

public class WarehouseResourceNotFoundException extends Exception {
	private static final long serialVersionUID = 5127597983815265458L;

	public WarehouseResourceNotFoundException(UUID uuid) {
        super("The requested warehouse resource (ID:" + uuid + ") could not be found");
    }
}