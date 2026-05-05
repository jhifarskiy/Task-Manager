package jhifarskiy.homework.mapper;

import jhifarskiy.homework.entity.Task;
import jhifarskiy.homework.entity.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public Task toDomain(TaskEntity taskEntity) {
        return new Task(
                taskEntity.getId(),
                taskEntity.getCreatorId(),
                taskEntity.getAssignedUserId(),
                taskEntity.getStatus(),
                taskEntity.getCreatedDateTime(),
                taskEntity.getDeadlineDate(),
                taskEntity.getPriority(),
                taskEntity.getDoneDateTime()
        );
    }

    public TaskEntity toEntity(Task task) {
        return new TaskEntity(
                task.getId(),
                task.getCreatorId(),
                task.getAssignedUserId(),
                task.getStatus(),
                task.getCreatedDateTime(),
                task.getDeadlineDate(),
                task.getPriority(),
                task.getDoneDateTime()
        );
    }


}
