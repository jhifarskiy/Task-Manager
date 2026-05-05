package jhifarskiy.homework.repository;

import jakarta.transaction.Transactional;
import jhifarskiy.homework.constant.TaskPriority;
import jhifarskiy.homework.constant.TaskStatus;
import jhifarskiy.homework.entity.Task;
import jhifarskiy.homework.entity.TaskEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Transactional
    @Query("""
            SELECT COUNT(t) FROM TaskEntity t
            WHERE t.assignedUserId = :assignedUserId
            AND t.status = :status
            """)
    Long findAllByAssignedUserId(@Param("assignedUserId") Long assignedUserId,
                                             @Param("status") TaskStatus status);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tasks SET status = :status WHERE id = :id", nativeQuery = true)
    void updateStatus(@Param("id") Long id, @Param("status") String status);


    @Query("""
    SELECT t FROM TaskEntity t 
    WHERE (:creatorId IS NULL OR t.creatorId = :creatorId) 
      AND (:assignedUserId IS NULL OR t.assignedUserId = :assignedUserId)
      AND (:status IS NULL OR t.status = :status)
      AND (:priority IS NULL OR t.priority = :priority)
    """)
    List<TaskEntity> searchAllByFilter(@Param("creatorId") Long creatorId,
                                       @Param("assignedUserId") Long assignedUserId,
                                       @Param("status") TaskStatus status,
                                       @Param("priority") TaskPriority priority,
                                       Pageable pageable);
}
