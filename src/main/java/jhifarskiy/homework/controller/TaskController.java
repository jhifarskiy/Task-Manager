package jhifarskiy.homework.controller;

import jakarta.validation.Valid;
import jhifarskiy.homework.constant.TaskPriority;
import jhifarskiy.homework.constant.TaskStatus;
import jhifarskiy.homework.dto.TaskSearchFilter;
import jhifarskiy.homework.entity.Task;
import jhifarskiy.homework.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private Logger log;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
        log = LoggerFactory.getLogger(TaskController.class.getName());
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAll() {
        log.info("Called getAll");
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id) {
        log.info("Called getById, ID: {}", id);
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<Task> create(@Valid
                                       @RequestBody Task task) {
        log.info("Called create, task: {}", task);
        return ResponseEntity.status(HttpStatus.OK).body(taskService.createTask(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id,
                                       @Valid
                                       @RequestBody Task task) {
        log.info("Called update, ID: {}, task: {}", id, task);
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(id, task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Called delete, ID: {}", id);
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<Task> statusToIn_progress(@PathVariable Long id) {
        log.info("Called statusToIn_progress, assignedUserId: {}", id);
        return ResponseEntity.ok(taskService.statusToIn_progress(id));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable Long id) {
        log.info("Called completeTask, ID: {}", id);
        return ResponseEntity.ok(taskService.completeTask(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchAllByFilter(
            @RequestParam(name = "creatorId", required = false) Long creatorId,
            @RequestParam(name = "assignedUserId", required = false) Long assignedUserId,
            @RequestParam(name = "status", required = false) TaskStatus status,
            @RequestParam(name = "priority", required = false) TaskPriority priority,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("pageNumber") Integer pageNumber) {
        TaskSearchFilter filter =
                new TaskSearchFilter(
                        creatorId,
                        assignedUserId,
                        status,
                        priority,
                        pageSize,
                        pageNumber);
        return ResponseEntity.status(HttpStatus.OK).body(taskService.searchAllByFilter(filter));
    }
}