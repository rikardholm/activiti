package com.rikardholm.insurance.admin.tasks;

import org.activiti.engine.form.FormData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Form {

    private List<Field> fields = new ArrayList<>();

    public Form(List<Field> fields) {
        this.fields = fields;
    }

    public List<Field> getFields() {
        return fields;
    }

    public static Form convert(FormData formData) {
        List<Field> fields = formData.getFormProperties().stream().map(Field::convert).collect(Collectors.toList());
        return new Form(fields);
    }
}
