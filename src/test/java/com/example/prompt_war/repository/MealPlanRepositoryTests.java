package com.example.prompt_war.repository;

import com.example.prompt_war.model.MealPlan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MealPlanRepositoryTests {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Test
    public void testSaveAndRetrieveMealPlan() {
        MealPlan plan = new MealPlan();
        plan.setDayType("busy");
        plan.setDietPreference("vegan");
        plan.setTimeConstraint("30");
        plan.setTargetBudget(25.0);
        plan.setTotalCalories(1200);
        plan.setBreakfastTitle("Vegan Toast");
        plan.setLunchTitle("Quinoa Bowl");
        plan.setDinnerTitle("Tofu Curry");

        MealPlan saved = mealPlanRepository.save(plan);
        assertThat(saved.getId()).isNotNull();

        List<MealPlan> plans = mealPlanRepository.findAllByOrderByCreatedAtDesc();
        assertThat(plans).isNotEmpty();
        assertThat(plans.get(0).getBreakfastTitle()).isEqualTo("Vegan Toast");
        assertThat(plans.get(0).getTotalCalories()).isEqualTo(1200);
    }
}
