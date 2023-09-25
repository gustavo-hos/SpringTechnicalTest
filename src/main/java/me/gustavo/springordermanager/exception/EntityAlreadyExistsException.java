package me.gustavo.springordermanager.exception;

public class EntityAlreadyExistsException extends RuntimeException {

    private String entityName;
    private String property;
    private String value;

    public EntityAlreadyExistsException(String entityName, String property, String value) {
        this.entityName = entityName;
        this.property = property;
        this.value = value;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
