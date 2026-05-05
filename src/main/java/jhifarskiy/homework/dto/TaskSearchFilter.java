package jhifarskiy.homework.dto;

import jhifarskiy.homework.constant.TaskPriority;
import jhifarskiy.homework.constant.TaskStatus;

public class TaskSearchFilter {
    private Long creatorId;
    private Long assignedUserId;
    private TaskStatus status;
    private TaskPriority priority;
    private Integer pageSize;
    private Integer pageNumber;

    public TaskSearchFilter(Long creatorId, Long assignedUserId, TaskStatus status,
                            TaskPriority priority, Integer pageSize, Integer pageNumber) {
        this.creatorId = creatorId;
        this.assignedUserId = assignedUserId;
        this.status = status;
        this.priority = priority;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    public TaskSearchFilter() {
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
