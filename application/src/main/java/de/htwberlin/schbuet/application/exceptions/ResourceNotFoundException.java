package de.htwberlin.schbuet.application.exceptions;

import java.util.UUID;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(UUID uuid) {
        super("The requested resource (ID:" + uuid + ") could not be found");
    }
}