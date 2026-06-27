package com.example.prompt_war.repository;

import com.example.prompt_war.model.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
    List<MealPlan> findAllByOrderByCreatedAtDesc();
}
