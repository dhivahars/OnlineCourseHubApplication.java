package com.onlinecoursehub.impl.repository;

import com.onlinecoursehub.impl.model.CompletionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletionRecordRepository extends JpaRepository<CompletionRecord,Long> {
}
