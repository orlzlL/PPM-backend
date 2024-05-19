package org.omoknoone.ppm.domain.task.repository;

import org.omoknoone.ppm.domain.task.aggregate.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findTasksByTaskScheduleId(Long scheduleId);

    @Query("SELECT t" +
            " FROM Task t" +
            " WHERE t.projectId = :projectId")
    List<Task> findByProjectId(@Param("projectId") Long projectId);
}