package de.htwberlin.schbuet.application.errors;

import java.util.UUID;

public class WarehouseResourceNotFoundException extends Exception {

    public WarehouseResourceNotFoundException(UUID uuid) {
        super("The requested warehouse resource (ID:" + uuid + ") could not be found");
    }
}