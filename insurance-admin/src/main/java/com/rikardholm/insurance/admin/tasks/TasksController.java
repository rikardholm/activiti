package com.rikardholm.insurance.admin.tasks;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private FormService formService;
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/register",method = POST)
    public void register(@RequestParam String personalIdentifier, @RequestParam String address) {
        Map<String, String> properties = new HashMap<>();
        properties.put("personalIdentifier", personalIdentifier);
        properties.put("address", address);

        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("register-insurance")
                .singleResult();

        formService.submitStartFormData(processDefinition.getId(), properties);
    }

    @RequestMapping(method = GET)
    public void tasks(Model model) {
        List<Task> tasks = taskService.createTaskQuery()
                .list();

        model.addAttribute("tasks", tasks);

        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .list();

        model.addAttribute("processes", processDefinitions);
    }

}
