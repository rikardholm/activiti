package com.rikardholm.insurance.admin.tasks;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/processes")
public class ProcessController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private FormService formService;

    @RequestMapping(method = GET)
    public List<String> processes() {
        return repositoryService.createProcessDefinitionQuery()
                .list().stream()
                .map(processDefinition -> processDefinition.getKey())
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{key}", method = GET)
    public String processDefinition(@PathVariable String key) {
        return getProcessDefinition(key).getName();
    }

    @RequestMapping(value = "/{key}/form", method = GET)
    public Form processForm(@PathVariable String key) {
        ProcessDefinition processDefinition = getProcessDefinition(key);

        StartFormData startFormData = formService.getStartFormData(processDefinition.getId());

        return Form.convert(startFormData);
    }

    private ProcessDefinition getProcessDefinition(String key) {
        return repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(key)
                .singleResult();
    }

    @RequestMapping(value = "/{key}/form", method = POST)
    public void startForm(@PathVariable String key, @RequestParam Map<String,String> parameters) {
        ProcessDefinition processDefinition = getProcessDefinition(key);

        formService.submitStartFormData(processDefinition.getId(), parameters);
    }

    @RequestMapping(value = "/{key}/form", params = "businessKey", method = POST)
    public void startFormWithBusinessKey(@PathVariable String key, @RequestParam String businessKey, @RequestParam Map<String,String> parameters) {
        ProcessDefinition processDefinition = getProcessDefinition(key);

        formService.submitStartFormData(processDefinition.getId(), businessKey, parameters);
    }

}
