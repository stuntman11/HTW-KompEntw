package de.htwberlin.schbuet.application.errors;

import java.util.UUID;

public class StockCreationFailedException extends Exception {
	private static final long serialVersionUID = 6901863162419639926L;
	private UUID projectId;
	
	public StockCreationFailedException(UUID productId) {
		this(productId, null);
    }
	
    public StockCreationFailedException(UUID productId, Throwable cause) {
        super(String.format("Failed to create warehouse stock for product id: '%s'", productId), cause);
        this.projectId = productId;
    }
    
    public UUID getProjectId() {
		return projectId;
	}
}
