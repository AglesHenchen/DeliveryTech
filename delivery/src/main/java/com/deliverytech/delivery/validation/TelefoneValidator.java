package com.deliverytech.delivery.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelefoneValidator implements ConstraintValidator<ValidTelefone, String> {

    @Override
    public void initialize(ValidTelefone constraintAnnotation) {
        // Inicialização, se necessária
    }

    @Override
    public boolean isValid(String telefone, ConstraintValidatorContext context) {
        if (telefone == null || telefone.trim().isEmpty()) {
            return false; // Retorna false se estiver vazio ou nulo
        }

        // Remove todos os caracteres que não sejam dígitos
        String cleanTelefone = telefone.replaceAll("[^\\d]", "");

        // Verifica se possui 10 ou 11 dígitos (DDD + número)
        return cleanTelefone.length() == 10 || cleanTelefone.length() == 11;
    }
}
