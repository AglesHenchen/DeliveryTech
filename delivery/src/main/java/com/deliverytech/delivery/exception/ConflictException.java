package com.deliverytech.delivery.exception;

public class ConflictException extends BusinessException {

    private final String conflictField;
    private final Object conflictValue;

    // Construtor com apenas mensagem
    public ConflictException(String message) {
        super(message);
        this.conflictField = null;
        this.conflictValue = null;
        this.setErrorCode("CONFLICT");
    }

    // Construtor com mensagem, campo e valor em conflito
    public ConflictException(String message, String conflictField, Object conflictValue) {
        super(message);
        this.conflictField = conflictField;
        this.conflictValue = conflictValue;
        this.setErrorCode("CONFLICT");
    }

    // Getters
    public String getConflictField() {
        return conflictField;
    }

    public Object getConflictValue() {
        return conflictValue;
    }
}
