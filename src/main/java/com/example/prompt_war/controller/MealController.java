package com.example.prompt_war.controller;

import com.example.prompt_war.model.MealPlan;
import com.example.prompt_war.repository.MealPlanRepository;
import com.example.prompt_war.service.AISimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class MealController {

    private final AISimulatorService aiSimulatorService;
    private final MealPlanRepository mealPlanRepository;

    @Autowired
    public MealController(AISimulatorService aiSimulatorService, MealPlanRepository mealPlanRepository) {
        this.aiSimulatorService = aiSimulatorService;
        this.mealPlanRepository = mealPlanRepository;
    }

    /**
     * Renders the main dashboard page, passing historical plans.
     */
    @GetMapping
    public String index(Model model) {
        List<MealPlan> history = mealPlanRepository.findAllByOrderByCreatedAtDesc();
        model.addAttribute("history", history);
        return "index";
    }

    /**
     * Endpoint to generate a meal plan. Saves it to database and returns as JSON.
     */
    @PostMapping("/generate")
    @ResponseBody
    public ResponseEntity<MealPlan> generate(
            @RequestParam String dayType,
            @RequestParam String dietPreference,
            @RequestParam String timeConstraint,
            @RequestParam Double targetBudget,
            @RequestParam(required = false, defaultValue = "") String fridgeIngredients) {

        MealPlan plan = aiSimulatorService.generateMealPlan(dayType, dietPreference, timeConstraint, targetBudget, fridgeIngredients);
        MealPlan savedPlan = mealPlanRepository.save(plan);
        return ResponseEntity.ok(savedPlan);
    }

    /**
     * Endpoint to fetch a past meal plan details as JSON.
     */
    @GetMapping("/plan/{id}")
    @ResponseBody
    public ResponseEntity<MealPlan> getPlan(@PathVariable Long id) {
        Optional<MealPlan> planOpt = mealPlanRepository.findById(id);
        return planOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to delete a meal plan.
     */
    @PostMapping("/delete/{id}")
    public String deletePlan(@PathVariable Long id) {
        mealPlanRepository.deleteById(id);
        return "redirect:/";
    }

    /**
     * Alternative REST endpoint to delete a meal plan via AJAX.
     */
    @DeleteMapping("/api/plan/{id}")
    @ResponseBody
    public ResponseEntity<Void> deletePlanApi(@PathVariable Long id) {
        mealPlanRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
