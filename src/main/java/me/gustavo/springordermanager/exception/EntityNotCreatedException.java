package me.gustavo.springordermanager.exception;

public class EntityNotCreatedException extends RuntimeException {
    private String entityName;

    public EntityNotCreatedException(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
}
