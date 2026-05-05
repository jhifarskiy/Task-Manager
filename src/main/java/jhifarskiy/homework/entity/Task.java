package jhifarskiy.homework.entity;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jhifarskiy.homework.constant.TaskPriority;
import jhifarskiy.homework.constant.TaskStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task {
    private Long id;

    @NotNull
    private Long creatorId;
    private Long assignedUserId;
    private TaskStatus status;

    @NotNull
    private LocalDateTime createdDateTime;

    @NotNull
    @Future
    private LocalDate deadlineDate;

    @Future
    private LocalDateTime doneDateTime;

    @NotNull
    private TaskPriority taskPriority;

    public Task(Long id, Long creatorId, Long assignedUserId,
                TaskStatus status, LocalDateTime createdDateTime,
                LocalDate deadlineDate, TaskPriority taskPriority,
                LocalDateTime doneDateTime) {
        this.id = id;
        this.creatorId = creatorId;
        this.assignedUserId = assignedUserId;
        this.status = status;
        this.createdDateTime = createdDateTime;
        this.deadlineDate = deadlineDate;
        this.taskPriority = taskPriority;
        this.doneDateTime = doneDateTime;
    }

    public Long getId() {
        return id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public TaskPriority getPriority() {
        return taskPriority;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public void setPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public LocalDateTime getDoneDateTime() {
        return doneDateTime;
    }

    public void setDoneDateTime(LocalDateTime doneDateTime) {
        this.doneDateTime = doneDateTime;
    }
}