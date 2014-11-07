package com.rikardholm.insurance.admin.tasks;

import com.google.common.collect.FluentIterable;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rikardholm.insurance.infrastructure.fake.FakeSparService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private FormService formService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    @Autowired
    private FakeSparService fakeSparService;

    @RequestMapping(value = "/register",method = POST)
    public void register(@RequestParam String personalIdentifier, @RequestParam(required = false) String address) {
        fakeSparService.makeUnavailable();

        Map<String, String> properties = new HashMap<>();
        if (isNotBlank(personalIdentifier)) {
            properties.put("personalIdentifier", personalIdentifier);
        }

        if (isNotBlank(address)) {
            properties.put("address", address);
        }

        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("register-insurance")
                .singleResult();

        formService.submitStartFormData(processDefinition.getId(), properties);
    }

    @RequestMapping(method = GET)
    public List<MyTask> tasks() {
        List<Task> tasks = taskService.createTaskQuery()
                .list();

        return FluentIterable.from(tasks)
                .transform(input -> new MyTask(input.getName(), input.getDescription()))
                .toList();
    }

    @RequestMapping(value = "/group/{group}", method = GET)
    public List<MyTask> groupTasks(@PathVariable String group) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(group)
                .list();

        return FluentIterable.from(tasks)
                .transform(input -> new MyTask(input.getName(), input.getDescription()))
                .toList();
    }

    @RequestMapping(value = "/process/{process}", method = GET)
    public List<MyTask> processTasks(@PathVariable String process) {
        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey(process)
                .list();

        return FluentIterable.from(tasks)
                .transform(input -> new MyTask(input.getName(), input.getDescription()))
                .toList();
    }

    public static class MyTask {
        private String name;
        private String description;

        public MyTask(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }

}
