package com.deliverytech.delivery.exception;

public class BusinessException extends RuntimeException {

    private String errorCode;

    // Construtor básico com mensagem
    public BusinessException(String message) {
        super(message);
    }

    // Construtor com mensagem e código de erro
    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    // Construtor com mensagem e causa
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    // Construtor completo: mensagem, código de erro e causa
    public BusinessException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    // Getter do código de erro
    public String getErrorCode() {
        return errorCode;
    }

    // Setter do código de erro
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
