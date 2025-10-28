package com.deliverytech.delivery.exception;

public class EntityNotFoundException extends BusinessException {

    private final String entityName;
    private final Object entityId;

    // Construtor com nome da entidade e ID
    public EntityNotFoundException(String entityName, Object entityId) {
        super(String.format("%s com ID %s não foi encontrado(a)", entityName, entityId));
        this.entityName = entityName;
        this.entityId = entityId;
        this.setErrorCode("ENTITY_NOT_FOUND");
    }

    // Construtor com mensagem personalizada
    public EntityNotFoundException(String message) {
        super(message);
        this.entityName = null;
        this.entityId = null;
        this.setErrorCode("ENTITY_NOT_FOUND");
    }

    // Getters
    public String getEntityName() {
        return entityName;
    }

    public Object getEntityId() {
        return entityId;
    }
}
