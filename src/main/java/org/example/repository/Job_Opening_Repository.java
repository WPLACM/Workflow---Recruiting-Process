package org.example.repository;

import org.example.entity.Job_Opening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Job_Opening_Repository extends JpaRepository<Job_Opening, Integer> {
    public List<Job_Opening> findAllByOrderByJobOpeningIdDesc();
    public Job_Opening findByJobOpeningId(Integer id);
}
