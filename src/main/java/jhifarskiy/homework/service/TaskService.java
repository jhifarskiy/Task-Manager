package jhifarskiy.homework.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jhifarskiy.homework.constant.TaskStatus;
import jhifarskiy.homework.dto.TaskSearchFilter;
import jhifarskiy.homework.entity.Task;
import jhifarskiy.homework.entity.TaskEntity;
import jhifarskiy.homework.mapper.TaskMapper;
import jhifarskiy.homework.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TaskService {
    private final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository repository;
    private final TaskMapper mapper;

    public TaskService(TaskRepository repository, TaskMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Autowired


    public List<Task> getAllTasks() {
        return repository.findAll().stream()
                .map(e -> mapper.toDomain(e)).toList();
    }

    public Task getTaskById(Long id) {
        return repository.findById(id).map(e -> mapper.toDomain(e))
                .orElseThrow(() -> new EntityNotFoundException("Task by ID: " + id + " not found"));
    }

    public Task createTask(Task taskRequest) {
        if (taskRequest.getId() != null) {
            throw new IllegalArgumentException("ID should be null");
        }
        if (taskRequest.getStatus() != null) {
            throw new IllegalStateException("Status should be null");
        }
        TaskEntity entityToSave = mapper.toEntity(taskRequest);
        entityToSave.setStatus(TaskStatus.CREATED);
        repository.save(entityToSave);
        return mapper.toDomain(entityToSave);
    }

    public Task updateTask(Long id, Task updateData) {
        TaskEntity taskEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't update" +
                        "task by ID: " + id + " not found"));

        if (taskEntity.getStatus() == TaskStatus.DONE) {
            if (updateData.getStatus() != TaskStatus.IN_PROGRESS) {
                throw new IllegalStateException("Cant' update finished task. " +
                        "Beside update status to IN_PROGRESS");
            }
        }
        TaskEntity entityToUpdate = mapper.toEntity(updateData);
        entityToUpdate.setId(id);
        entityToUpdate.setStatus(updateData.getStatus());
        return mapper.toDomain(entityToUpdate);
    }

    public void deleteTask(Long id) {
        TaskEntity taskEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't delete: " +
                        "task by ID: " + id + " not found"));

        repository.delete(taskEntity);
    }

    @Transactional
    public Task statusToIn_progress(Long id) {
        TaskEntity taskEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't update status: " +
                        "task by ID: " + id + " not found"));

        if (taskEntity.getStatus() == TaskStatus.IN_PROGRESS){
            return mapper.toDomain(taskEntity);
        }


        Long assignedUserId = taskEntity.getAssignedUserId();
        if (assignedUserId == null) {
            throw new IllegalArgumentException("assignedUserId should be filled in");
        }
        Long amountIn_progressStatus = repository.findAllByAssignedUserId(assignedUserId, TaskStatus.IN_PROGRESS);
        if (amountIn_progressStatus >= 5) {
            throw new IllegalArgumentException("User with ID: " +  assignedUserId + " should have less 5 tasks");
        }

        taskEntity.setStatus(TaskStatus.IN_PROGRESS);

        repository.save(taskEntity);
        return mapper.toDomain(taskEntity);
    }

    @Transactional
    public Task completeTask(Long id) {
        TaskEntity taskEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Невозможно обновить статус: " +
                        "задача с ID " + id + " не существует."));

        if (taskEntity.getAssignedUserId() == null || taskEntity.getDeadlineDate() == null) {
            throw new IllegalArgumentException("Fields assignedUserId and deadlineDate should be filled in");
        }
        taskEntity.setDoneDateTime(LocalDateTime.now());
        taskEntity.setStatus(TaskStatus.DONE);
        return mapper.toDomain(taskEntity);
    }


    public List<Task> searchAllByFilter(TaskSearchFilter filter) {
        int pageSize = filter.getPageSize() != null
                ? filter.getPageSize() : 10; // сколько отобразить
        int pageNumber = filter.getPageNumber() != null
                ? filter.getPageNumber() : 0; // номер страницы

        Pageable pageable = Pageable
                .ofSize(pageSize)
                .withPage(pageNumber);
        return repository.searchAllByFilter(
                        filter.getCreatorId(),
                        filter.getAssignedUserId(),
                        filter.getStatus(),
                        filter.getPriority(),
                        pageable
                )
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}