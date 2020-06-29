package org.example.repository;

import org.example.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Application_Repository extends JpaRepository<Application, Integer> {
}
