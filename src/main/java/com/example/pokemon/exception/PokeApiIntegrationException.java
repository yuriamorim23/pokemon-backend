package com.example.pokemon.exception;

public class PokeApiIntegrationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public PokeApiIntegrationException(String message) {
        super(message);
    }

    public PokeApiIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
