package com.sideagroup.academy.repository;

import com.sideagroup.academy.model.Celebrity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CelebrityRepository extends JpaRepository<Celebrity, String> {
    Page<Celebrity> findByPrimaryNameContainingIgnoreCase(String name, Pageable pageable);
}
