package org.example.repository;

import org.example.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Candidate_Repository extends JpaRepository<Candidate, Integer> {
}
