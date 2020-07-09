package org.example.repository;

import org.example.entity.Job_Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Job_Profile_Repository extends JpaRepository<Job_Profile, Integer> {
    Job_Profile findByJobProfileId(Integer id);
}
