package de.htwberlin.schbuet.data_warehouse.errors;

import java.util.UUID;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(UUID uuid) {
        super("The requested resource (ID:" + uuid + ") could not be found");
    }
}