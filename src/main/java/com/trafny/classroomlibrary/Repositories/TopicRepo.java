package com.trafny.classroomlibrary.Repositories;

import com.trafny.classroomlibrary.Entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepo extends JpaRepository<Topic, Long> {
}
