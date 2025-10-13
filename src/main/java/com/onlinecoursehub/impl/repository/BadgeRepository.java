package com.onlinecoursehub.impl.repository;

import com.onlinecoursehub.impl.utils.Badge;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends JpaRepository<Badge,Long> {
    boolean existsByName(@NotBlank String name);
}
