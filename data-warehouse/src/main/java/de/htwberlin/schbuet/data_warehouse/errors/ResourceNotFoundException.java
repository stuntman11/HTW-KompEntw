package de.htwberlin.schbuet.data_warehouse.errors;

import java.util.UUID;

public class ResourceNotFoundException extends Exception {
	private static final long serialVersionUID = -1630408228269192039L;

	public ResourceNotFoundException(UUID uuid) {
        super("The requested resource (ID:" + uuid + ") could not be found");
    }
}