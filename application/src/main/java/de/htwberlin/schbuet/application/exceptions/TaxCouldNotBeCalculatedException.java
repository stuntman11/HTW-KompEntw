package de.htwberlin.schbuet.application.exceptions;

public class TaxCouldNotBeCalculatedException extends Exception{

    public TaxCouldNotBeCalculatedException() {
        super("Taxes could not be calculated");
    }
}
