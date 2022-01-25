package de.htwberlin.schbuet.application.exceptions;

import java.util.UUID;

public class WarehouseResourceNotFoundException extends Exception {

    public WarehouseResourceNotFoundException(UUID uuid) {
        super("The requested warehouse resource (ID:" + uuid + ") could not be found");
    }
}