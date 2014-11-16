package com.rikardholm.insurance.admin.tasks;

import org.activiti.engine.repository.ProcessDefinition;

public class Process {

    private String id;
    private String key;
    private String name;
    private String description;

    public Process(String id, String key, String name, String description) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static Process convert(ProcessDefinition processDefinition) {
        return new Process(processDefinition.getId(), processDefinition.getKey(), processDefinition.getName(), processDefinition.getDescription());
    }
}
