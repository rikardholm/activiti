package com.rikardholm.insurance.admin.tasks;

import org.activiti.engine.form.FormProperty;

public class Field {
    private String id;
    private String type;
    private String name;
    private String value;
    private boolean required;
    private boolean readable;
    private boolean writable;

    public Field(String id, String name, String type, String value, boolean required, boolean readable, boolean writable) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.value = value;
        this.required = required;
        this.readable = readable;
        this.writable = writable;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isReadable() {
        return readable;
    }

    public boolean isWritable() {
        return writable;
    }

    public static Field convert(FormProperty formProperty) {
        return new Field(formProperty.getId(), formProperty.getName(), formProperty.getType().getName(), formProperty.getValue(), formProperty.isRequired(), formProperty.isReadable(), formProperty.isWritable());
    }
}
