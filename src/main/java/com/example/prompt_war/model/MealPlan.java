package com.example.prompt_war.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "meal_plans")
public class MealPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dayType;
    private String dietPreference;
    private String timeConstraint;
    
    // Budget & Cost Configuration
    private Double targetBudget;
    private Double estimatedCost;
    private String budgetStatus;
    @Column(columnDefinition = "TEXT")
    private String budgetFeasibilityNote;

    // Calories Configuration
    private Integer breakfastCalories;
    private Integer lunchCalories;
    private Integer dinnerCalories;
    private Integer totalCalories;

    // Breakfast
    private String breakfastTitle;
    @Column(columnDefinition = "TEXT")
    private String breakfastDescription;
    @Column(columnDefinition = "TEXT")
    private String breakfastInstructions;
    @Column(columnDefinition = "TEXT")
    private String breakfastIngredients;

    // Lunch
    private String lunchTitle;
    @Column(columnDefinition = "TEXT")
    private String lunchDescription;
    @Column(columnDefinition = "TEXT")
    private String lunchInstructions;
    @Column(columnDefinition = "TEXT")
    private String lunchIngredients;

    // Dinner
    private String dinnerTitle;
    @Column(columnDefinition = "TEXT")
    private String dinnerDescription;
    @Column(columnDefinition = "TEXT")
    private String dinnerInstructions;
    @Column(columnDefinition = "TEXT")
    private String dinnerIngredients;

    // Grocery List Raw and Substitutions
    @Column(columnDefinition = "TEXT")
    private String groceryListRaw;
    
    @Column(columnDefinition = "TEXT")
    private String substitutionsRaw;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDayType() {
        return dayType;
    }

    public void setDayType(String dayType) {
        this.dayType = dayType;
    }

    public String getDietPreference() {
        return dietPreference;
    }

    public void setDietPreference(String dietPreference) {
        this.dietPreference = dietPreference;
    }

    public String getTimeConstraint() {
        return timeConstraint;
    }

    public void setTimeConstraint(String timeConstraint) {
        this.timeConstraint = timeConstraint;
    }

    public Double getTargetBudget() {
        return targetBudget;
    }

    public void setTargetBudget(Double targetBudget) {
        this.targetBudget = targetBudget;
    }

    public Double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public String getBudgetStatus() {
        return budgetStatus;
    }

    public void setBudgetStatus(String budgetStatus) {
        this.budgetStatus = budgetStatus;
    }

    public String getBudgetFeasibilityNote() {
        return budgetFeasibilityNote;
    }

    public void setBudgetFeasibilityNote(String budgetFeasibilityNote) {
        this.budgetFeasibilityNote = budgetFeasibilityNote;
    }

    public Integer getBreakfastCalories() {
        return breakfastCalories;
    }

    public void setBreakfastCalories(Integer breakfastCalories) {
        this.breakfastCalories = breakfastCalories;
    }

    public Integer getLunchCalories() {
        return lunchCalories;
    }

    public void setLunchCalories(Integer lunchCalories) {
        this.lunchCalories = lunchCalories;
    }

    public Integer getDinnerCalories() {
        return dinnerCalories;
    }

    public void setDinnerCalories(Integer dinnerCalories) {
        this.dinnerCalories = dinnerCalories;
    }

    public Integer getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(Integer totalCalories) {
        this.totalCalories = totalCalories;
    }

    public String getBreakfastTitle() {
        return breakfastTitle;
    }

    public void setBreakfastTitle(String breakfastTitle) {
        this.breakfastTitle = breakfastTitle;
    }

    public String getBreakfastDescription() {
        return breakfastDescription;
    }

    public void setBreakfastDescription(String breakfastDescription) {
        this.breakfastDescription = breakfastDescription;
    }

    public String getBreakfastInstructions() {
        return breakfastInstructions;
    }

    public void setBreakfastInstructions(String breakfastInstructions) {
        this.breakfastInstructions = breakfastInstructions;
    }

    public String getBreakfastIngredients() {
        return breakfastIngredients;
    }

    public void setBreakfastIngredients(String breakfastIngredients) {
        this.breakfastIngredients = breakfastIngredients;
    }

    public String getLunchTitle() {
        return lunchTitle;
    }

    public void setLunchTitle(String lunchTitle) {
        this.lunchTitle = lunchTitle;
    }

    public String getLunchDescription() {
        return lunchDescription;
    }

    public void setLunchDescription(String lunchDescription) {
        this.lunchDescription = lunchDescription;
    }

    public String getLunchInstructions() {
        return lunchInstructions;
    }

    public void setLunchInstructions(String lunchInstructions) {
        this.lunchInstructions = lunchInstructions;
    }

    public String getLunchIngredients() {
        return lunchIngredients;
    }

    public void setLunchIngredients(String lunchIngredients) {
        this.lunchIngredients = lunchIngredients;
    }

    public String getDinnerTitle() {
        return dinnerTitle;
    }

    public void setDinnerTitle(String dinnerTitle) {
        this.dinnerTitle = dinnerTitle;
    }

    public String getDinnerDescription() {
        return dinnerDescription;
    }

    public void setDinnerDescription(String dinnerDescription) {
        this.dinnerDescription = dinnerDescription;
    }

    public String getDinnerInstructions() {
        return dinnerInstructions;
    }

    public void setDinnerInstructions(String dinnerInstructions) {
        this.dinnerInstructions = dinnerInstructions;
    }

    public String getDinnerIngredients() {
        return dinnerIngredients;
    }

    public void setDinnerIngredients(String dinnerIngredients) {
        this.dinnerIngredients = dinnerIngredients;
    }

    public String getGroceryListRaw() {
        return groceryListRaw;
    }

    public void setGroceryListRaw(String groceryListRaw) {
        this.groceryListRaw = groceryListRaw;
    }

    public String getSubstitutionsRaw() {
        return substitutionsRaw;
    }

    public void setSubstitutionsRaw(String substitutionsRaw) {
        this.substitutionsRaw = substitutionsRaw;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
