package com.sc.expenseservice.repository;

import com.sc.expenseservice.dto.ExpenseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseDTO, Long> {

    List<ExpenseDTO> findByUserId(String userId);

    List<ExpenseDTO> findByUserIdAndCreatedAtBetween(
        String userId, Timestamp startTime, Timestamp endTime);

    Optional<ExpenseDTO> findByUserIdAndExternalId(String userId, String externalId);
}