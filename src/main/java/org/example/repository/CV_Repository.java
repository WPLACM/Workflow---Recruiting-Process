package org.example.repository;

import org.example.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CV_Repository extends JpaRepository<Candidate, Integer> {
}
