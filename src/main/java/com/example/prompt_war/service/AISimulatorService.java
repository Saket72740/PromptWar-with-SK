package com.example.prompt_war.service;

import com.example.prompt_war.model.MealPlan;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AISimulatorService {

    public static class Recipe {
        public String title;
        public String description;
        public List<String> instructions;
        public Map<String, Double> ingredients; // Ingredient name -> Cost
        public int calories;

        public Recipe(String title, String description, List<String> instructions, Map<String, Double> ingredients, int calories) {
            this.title = title;
            this.description = description;
            this.instructions = instructions;
            this.ingredients = ingredients;
            this.calories = calories;
        }
    }

    private static final Map<String, List<Recipe>> BREAKFASTS = new HashMap<>();
    private static final Map<String, List<Recipe>> LUNCHES = new HashMap<>();
    private static final Map<String, List<Recipe>> DINNERS = new HashMap<>();

    static {
        // --- BREAKFASTS ---
        BREAKFASTS.put("VEGAN", Arrays.asList(
            new Recipe("Avocado & Smashed Chickpea Toast", "Creamy avocado paired with seasoned chickpeas on sourdough toast.",
                Arrays.asList("Toast sourdough bread until golden.", "Mash avocado with chickpeas, lemon juice, salt, and pepper.", "Spread onto toast and garnish with red chili flakes."),
                createMap("Sourdough Bread", 1.50, "Avocado", 1.20, "Canned Chickpeas", 0.80, "Lemon & Spices", 0.50), 340),
            new Recipe("Berry Almond Protein Smoothie", "A quick, antioxidant-rich smoothie packed with vegan almond protein.",
                Arrays.asList("Add almond milk, vegan protein powder, and frozen mixed berries to a blender.", "Blend on high speed until completely smooth.", "Pour into a glass and top with chia seeds."),
                createMap("Almond Milk", 1.00, "Vegan Protein Powder", 2.20, "Frozen Berries", 1.50, "Chia Seeds", 0.40), 290),
            new Recipe("Tofu Scramble with Baby Spinach", "Scrambled firm tofu with baby spinach and nutritional yeast.",
                Arrays.asList("Crumble firm tofu into a hot non-stick skillet with a splash of olive oil.", "Season with turmeric, garlic powder, salt, and nutritional yeast.", "Toss in baby spinach and cook until wilted."),
                createMap("Firm Tofu", 1.80, "Baby Spinach", 1.00, "Olive Oil & Spices", 0.60, "Nutritional Yeast", 0.50), 260)
        ));
        BREAKFASTS.put("VEGETARIAN", BREAKFASTS.get("VEGAN"));

        BREAKFASTS.put("KETO", Arrays.asList(
            new Recipe("Bacon & Cheddar Spinach Omelette", "A low-carb, high-fat starter featuring crispy bacon and melted cheddar.",
                Arrays.asList("Whisk eggs with a splash of heavy cream, salt, and pepper.", "Cook in a buttered skillet over medium heat.", "Add cooked bacon bits, shredded cheddar, and baby spinach, then fold in half."),
                createMap("Organic Eggs", 1.20, "Bacon Strips", 2.50, "Cheddar Cheese", 1.00, "Spinach & Butter", 0.80), 480),
            new Recipe("Avocado & Baked Egg Cups", "Avocado halves stuffed with eggs and baked to warm, buttery perfection.",
                Arrays.asList("Slice avocado in half and scoop out a bit of the center.", "Crack an egg into each half, season with salt and pepper.", "Bake for 15 minutes at 400°F."),
                createMap("Avocado", 1.20, "Organic Eggs", 0.80, "Salt & Pepper", 0.40), 320)
        ));

        BREAKFASTS.put("GLUTEN_FREE", Arrays.asList(
            new Recipe("Banana & Almond Butter Oatmeal (GF)", "Warm gluten-free rolled oats cooked in almond milk, topped with banana.",
                Arrays.asList("Combine gluten-free oats and almond milk in a pot.", "Bring to a boil, then simmer for 5 minutes, stirring occasionally.", "Top with sliced banana, almond butter, and maple syrup."),
                createMap("Gluten-Free Oats", 1.20, "Almond Milk", 1.00, "Banana", 0.50, "Almond Butter", 1.20), 380),
            new Recipe("Sweet Potato & Egg Breakfast Hash", "Sautéed sweet potato cubes fried with peppers and topped with runny eggs.",
                Arrays.asList("Sauté diced sweet potatoes and bell peppers in olive oil until tender.", "Make two wells in the hash and crack an egg into each.", "Cover and cook until egg whites are set."),
                createMap("Sweet Potatoes", 1.00, "Bell Pepper", 0.90, "Organic Eggs", 0.80, "Olive Oil & Spices", 0.50), 310)
        ));

        BREAKFASTS.put("NONE", Arrays.asList(
            new Recipe("Classic French Toast with Berries", "Thick slices of brioche dipped in egg-cinnamon wash and pan-fried.",
                Arrays.asList("Whisk eggs, milk, cinnamon, and vanilla in a shallow bowl.", "Dip brioche slices in the mixture, coating both sides.", "Grill in butter until golden, then serve with maple syrup and strawberries."),
                createMap("Brioche Bread", 1.60, "Organic Eggs", 0.80, "Maple Syrup & Milk", 1.20, "Fresh Strawberries", 1.80), 420),
            new Recipe("Loaded Breakfast Burrito", "Fluffy scrambled eggs, sausage, and cheese wrapped in a warm flour tortilla.",
                Arrays.asList("Brown breakfast sausage in a skillet; drain excess fat.", "Scramble eggs in the same pan with shredded cheese.", "Warm a flour tortilla, fill with eggs, sausage, and salsa, then roll."),
                createMap("Flour Tortillas", 0.80, "Breakfast Sausage", 2.20, "Organic Eggs", 0.80, "Cheese & Salsa", 1.20), 510)
        ));

        // --- LUNCHES ---
        LUNCHES.put("VEGAN", Arrays.asList(
            new Recipe("Mediterranean Quinoa Salad Bowl", "Fluffy quinoa tossed with cherry tomatoes, cucumbers, olives, and herb vinaigrette.",
                Arrays.asList("Boil quinoa in water until tender.", "Toss with halved cherry tomatoes, diced cucumber, and olives.", "Drizzle with olive oil, lemon juice, dried oregano, and salt."),
                createMap("Quinoa", 1.10, "Cherry Tomatoes", 1.40, "Cucumber & Olives", 1.50, "Olive Oil & Lemon", 0.70), 390),
            new Recipe("Spicy Chickpea & Avocado Wrap", "Smashed seasoned chickpeas and fresh avocado rolled in a spinach tortilla.",
                Arrays.asList("Drain and rinse chickpeas, then mash slightly with a fork.", "Mix in chili powder, garlic powder, and lime juice.", "Spread avocado on a tortilla, add chickpeas and spinach, then fold."),
                createMap("Spinach Tortilla", 0.90, "Canned Chickpeas", 0.80, "Avocado", 1.20, "Lime & Spices", 0.40), 410)
        ));
        LUNCHES.put("VEGETARIAN", LUNCHES.get("VEGAN"));

        LUNCHES.put("KETO", Arrays.asList(
            new Recipe("Chicken Bacon Ranch Lettuce Wraps", "Crispy butter lettuce cups filled with grilled chicken breast, bacon, and ranch.",
                Arrays.asList("Dice cooked chicken breast and crispy bacon.", "Toss with ranch dressing and chopped chives.", "Spoon into large butter lettuce leaves."),
                createMap("Chicken Breast", 3.50, "Bacon Strips", 2.00, "Butter Lettuce", 1.20, "Ranch Dressing", 0.80), 520),
            new Recipe("Creamy Salmon & Avocado Salad", "Flaked wild-caught salmon mixed with avocado, red onion, and olive oil mayo.",
                Arrays.asList("Mix canned or grilled salmon with avocado oil mayonnaise.", "Gently fold in diced avocado, minced red onion, and fresh dill.", "Serve on a bed of fresh mixed greens."),
                createMap("Salmon", 4.50, "Avocado", 1.20, "Mayonnaise & Greens", 1.00, "Red Onion & Dill", 0.40), 460)
        ));

        LUNCHES.put("GLUTEN_FREE", Arrays.asList(
            new Recipe("Roasted Chickpea & Cauliflower Salad", "Crunchy roasted chickpeas and spiced cauliflower on baby spinach.",
                Arrays.asList("Toss chickpeas and cauliflower in olive oil, cumin, and paprika.", "Roast at 400°F (200°C) for 20 minutes.", "Serve over baby spinach with a light tahini drizzle."),
                createMap("Canned Chickpeas", 0.80, "Cauliflower", 1.50, "Baby Spinach", 1.00, "Tahini & Spices", 1.20), 380),
            new Recipe("Grilled Chicken & Quinoa Salad", "Herb-marinated chicken breast served over a fresh quinoa salad.",
                Arrays.asList("Grill chicken breast in a skillet with olive oil and rosemary.", "Let rest, then slice thinly.", "Serve over cooked quinoa mixed with diced tomatoes and cucumbers."),
                createMap("Chicken Breast", 3.50, "Quinoa", 1.10, "Cucumber & Tomatoes", 1.40, "Olive Oil & Herbs", 0.60), 450)
        ));

        LUNCHES.put("NONE", Arrays.asList(
            new Recipe("Ultimate Turkey Club Sandwich", "Toasted club sandwich with turkey breast, bacon, lettuce, tomato, and mayo.",
                Arrays.asList("Toast slices of bread.", "Spread mayonnaise on bread slices.", "Layer sliced turkey, bacon, lettuce, and tomato slices."),
                createMap("White Bread", 0.60, "Turkey Breast", 2.80, "Bacon Strips", 1.50, "Tomato, Lettuce, Mayo", 1.00), 540),
            new Recipe("Creamy Chicken Alfredo Pasta", "Fettuccine pasta coated in a rich parmesan cream sauce with grilled chicken.",
                Arrays.asList("Boil fettuccine pasta in salted water.", "Melt butter, add heavy cream, garlic, and grated parmesan; simmer.", "Toss pasta and sliced chicken breast in the sauce."),
                createMap("Fettuccine Pasta", 0.90, "Chicken Breast", 3.50, "Heavy Cream & Butter", 1.80, "Parmesan Cheese", 1.50), 680)
        ));

        // --- DINNERS ---
        DINNERS.put("VEGAN", Arrays.asList(
            new Recipe("Thai Green Curry with Crispy Tofu", "Crispy pan-fried organic tofu simmered in green curry coconut milk with bell peppers.",
                Arrays.asList("Press tofu to remove water, cube, and pan-sear until golden.", "Sauté green curry paste, pour in coconut milk and simmer.", "Add bell peppers and tofu; simmer and serve with jasmine rice."),
                createMap("Firm Tofu", 1.80, "Coconut Milk", 1.50, "Green Curry Paste", 1.00, "Bell Pepper & Rice", 1.40), 520),
            new Recipe("Garlic Herb Mushroom Pasta (Vegan)", "Spaghetti tossed with sautéed cremini mushrooms, garlic, parsley, and olive oil.",
                Arrays.asList("Boil spaghetti in salted water.", "Sauté sliced mushrooms and minced garlic in olive oil.", "Toss spaghetti with mushrooms, fresh parsley, and lemon juice."),
                createMap("Spaghetti Pasta", 0.80, "Cremini Mushrooms", 2.20, "Garlic & Parsley", 0.60, "Olive Oil & Lemon", 0.80), 460)
        ));
        DINNERS.put("VEGETARIAN", DINNERS.get("VEGAN"));

        DINNERS.put("KETO", Arrays.asList(
            new Recipe("Pan-Seared Ribeye Steak with Broccoli", "Thick ribeye steak pan-seared with garlic butter, served with broccoli.",
                Arrays.asList("Season steak with salt and pepper.", "Sear in a hot cast-iron skillet, basting with butter, garlic, and rosemary.", "Steam broccoli and toss with butter."),
                createMap("Ribeye Steak", 11.50, "Broccoli Head", 1.20, "Butter & Garlic", 0.90, "Rosemary & Herbs", 0.40), 720),
            new Recipe("Lemon Garlic Butter Baked Salmon", "Rich salmon fillet baked with lemon juice, fresh dill, and melted garlic butter.",
                Arrays.asList("Place salmon on a lined baking sheet.", "Drizzle with melted butter, minced garlic, and lemon juice.", "Bake for 12-15 minutes until salmon flakes easily."),
                createMap("Salmon Fillet", 8.50, "Butter & Lemon", 1.00, "Garlic & Dill", 0.50), 550)
        ));

        DINNERS.put("GLUTEN_FREE", Arrays.asList(
            new Recipe("Sesame Beef & Broccoli Stir-Fry (GF)", "Tender beef slices stir-fried with broccoli in a ginger gluten-free tamari sauce.",
                Arrays.asList("Slice beef into thin strips. Toss in cornstarch.", "Whisk gluten-free tamari, sesame oil, honey, ginger, and garlic.", "Stir-fry beef, add broccoli and sauce, and cook until thickened."),
                createMap("Beef Sirloin", 6.80, "Broccoli Head", 1.20, "GF Tamari & Sesame Oil", 1.50, "Ginger, Garlic & Honey", 0.80), 530),
            new Recipe("Lemon Herb Grilled Chicken & Asparagus", "Juicy grilled chicken breasts marinated in lemon and herbs, served with tender asparagus.",
                Arrays.asList("Marinate chicken in olive oil, lemon juice, oregano, and garlic.", "Grill chicken for 6-7 minutes per side.", "Toss asparagus in olive oil and grill alongside the chicken."),
                createMap("Chicken Breasts", 4.50, "Asparagus Bunch", 2.20, "Olive Oil & Lemon", 0.90, "Garlic & Oregano", 0.40), 480)
        ));

        DINNERS.put("NONE", Arrays.asList(
            new Recipe("Garlic Butter Steak Bites with Potatoes", "Juicy seared steak cubes tossed in garlic butter, served with roasted baby potatoes.",
                Arrays.asList("Toss baby potatoes with olive oil and rosemary; roast for 20 minutes.", "Cut sirloin steak into cubes and sear in a hot skillet.", "Reduce heat, add butter and garlic, tossing steak bites to coat."),
                createMap("Sirloin Steak", 7.50, "Baby Potatoes", 1.40, "Butter & Garlic", 0.80, "Olive Oil & Rosemary", 0.60), 610),
            new Recipe("Classic Spaghetti Bolognese", "Rich, ground beef and tomato sauce served over spaghetti.",
                Arrays.asList("Brown ground beef with onions and garlic in a pot.", "Pour in crushed tomatoes, paste, Italian seasoning, and simmer.", "Serve hot over spaghetti, topped with parmesan."),
                createMap("Ground Beef", 4.50, "Spaghetti Pasta", 0.80, "Crushed Tomatoes & Paste", 1.50, "Onion, Garlic & Spices", 0.80, "Parmesan Cheese", 1.00), 580)
        ));
    }

    private static Map<String, Double> createMap(Object... keysAndValues) {
        Map<String, Double> map = new LinkedHashMap<>();
        for (int i = 0; i < keysAndValues.length; i += 2) {
            map.put((String) keysAndValues[i], (Double) keysAndValues[i + 1]);
        }
        return map;
    }

    public MealPlan generateMealPlan(String dayType, String dietPreference, String timeConstraint, Double targetBudget, String fridgeIngredientsRaw) {
        String dietKey = dietPreference != null ? dietPreference.toUpperCase().replace("-", "_") : "NONE";
        if (!BREAKFASTS.containsKey(dietKey)) {
            dietKey = "NONE";
        }

        List<Recipe> breakfastPool = BREAKFASTS.get(dietKey);
        List<Recipe> lunchPool = LUNCHES.get(dietKey);
        List<Recipe> dinnerPool = DINNERS.get(dietKey);

        // Parse user-supplied base ingredients
        Set<String> ownedIngredients = new HashSet<>();
        if (fridgeIngredientsRaw != null && !fridgeIngredientsRaw.trim().isEmpty()) {
            ownedIngredients = Arrays.stream(fridgeIngredientsRaw.split(","))
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toSet());
        }

        int maxCookTime = 60;
        try {
            maxCookTime = Integer.parseInt(timeConstraint);
        } catch (NumberFormatException e) {
            // Keep default
        }

        // Select recipes prioritizing ingredient matches
        Recipe breakfast = selectBestMatchingRecipe(breakfastPool, dayType, maxCookTime, ownedIngredients, 0);
        Recipe lunch = selectBestMatchingRecipe(lunchPool, dayType, maxCookTime, ownedIngredients, 1);
        Recipe dinner = selectBestMatchingRecipe(dinnerPool, dayType, maxCookTime, ownedIngredients, 2);

        int totalCalories = breakfast.calories + lunch.calories + dinner.calories;

        // Build Master Grocery List and calculate costs
        Map<String, Double> aggregatedGroceries = new LinkedHashMap<>();
        addIngredients(aggregatedGroceries, breakfast.ingredients);
        addIngredients(aggregatedGroceries, lunch.ingredients);
        addIngredients(aggregatedGroceries, dinner.ingredients);

        double totalCost = 0.0;
        List<String> groceryItemsFormatted = new ArrayList<>();
        
        for (Map.Entry<String, Double> entry : aggregatedGroceries.entrySet()) {
            String name = entry.getKey();
            double price = entry.getValue();
            boolean inFridge = isMatched(name, ownedIngredients);
            
            double costToAdd = inFridge ? 0.0 : price;
            totalCost += costToAdd;

            // Formatted as: Name|OriginalPrice|IsOwned
            groceryItemsFormatted.add(name + "|" + String.format(Locale.US, "%.2f", price) + "|" + (inFridge ? "true" : "false"));
        }

        MealPlan mealPlan = new MealPlan();
        mealPlan.setDayType(dayType);
        mealPlan.setDietPreference(dietPreference);
        mealPlan.setTimeConstraint(timeConstraint);
        
        // Budget Config
        mealPlan.setTargetBudget(targetBudget);
        mealPlan.setEstimatedCost(totalCost);

        // Calorie Counts
        mealPlan.setBreakfastCalories(breakfast.calories);
        mealPlan.setLunchCalories(lunch.calories);
        mealPlan.setDinnerCalories(dinner.calories);
        mealPlan.setTotalCalories(totalCalories);

        // Recipe Details
        mealPlan.setBreakfastTitle(breakfast.title);
        mealPlan.setBreakfastDescription(breakfast.description);
        mealPlan.setBreakfastInstructions(String.join("\n", breakfast.instructions));
        mealPlan.setBreakfastIngredients(serializeIngredients(breakfast.ingredients, ownedIngredients));

        mealPlan.setLunchTitle(lunch.title);
        mealPlan.setLunchDescription(lunch.description);
        mealPlan.setLunchInstructions(String.join("\n", lunch.instructions));
        mealPlan.setLunchIngredients(serializeIngredients(lunch.ingredients, ownedIngredients));

        mealPlan.setDinnerTitle(dinner.title);
        mealPlan.setDinnerDescription(dinner.description);
        mealPlan.setDinnerInstructions(String.join("\n", dinner.instructions));
        mealPlan.setDinnerIngredients(serializeIngredients(dinner.ingredients, ownedIngredients));

        // Master lists
        mealPlan.setGroceryListRaw(String.join(";", groceryItemsFormatted));

        // Substitutions & Feasibility notes
        double diff = totalCost - targetBudget;
        String status;
        String advice;
        List<String> substitutions = new ArrayList<>();

        if (diff <= 0) {
            status = "Within Budget";
            double savings = Math.abs(diff);
            advice = String.format(Locale.US, "Within Budget! Your meal plan saves you $%.2f compared to your limit. " +
                    "Pro tip: Your base ingredients in stock kept your out-of-pocket costs extremely competitive.", savings);
            
            substitutions.add("Swap Fresh Strawberries for Frozen Mixed Berries to save $0.80");
            substitutions.add("Swap Organic Eggs for Conventional Eggs to save $0.40");
        } else {
            status = "Over Budget";
            advice = String.format(Locale.US, "This configuration is $%.2f over your target limit of $%.2f. " +
                    "Applying our recommended ingredient swaps below will help bring your spending down.", diff, targetBudget);

            if (aggregatedGroceries.containsKey("Ribeye Steak")) {
                substitutions.add("Swap Ribeye Steak ($11.50) for Sirloin Steak ($7.50) to save $4.00");
                substitutions.add("Swap Ribeye Steak ($11.50) for Chicken Breasts ($4.50) to save $7.00");
            }
            if (aggregatedGroceries.containsKey("Salmon Fillet")) {
                substitutions.add("Swap Salmon Fillet ($8.50) for Canned Tuna ($2.00) to save $6.50");
            }
            if (aggregatedGroceries.containsKey("Fresh Strawberries")) {
                substitutions.add("Swap Fresh Strawberries ($1.80) for Apple Slices ($0.50) to save $1.30");
            }
            
            if (substitutions.isEmpty()) {
                substitutions.add("Swap fresh herbs for dried variants to save approximately $1.50 across shopping items.");
            }
        }

        mealPlan.setBudgetStatus(status);
        mealPlan.setBudgetFeasibilityNote(advice);
        mealPlan.setSubstitutionsRaw(String.join(";", substitutions));

        return mealPlan;
    }

    private Recipe selectBestMatchingRecipe(List<Recipe> pool, String dayType, int maxCookTime, Set<String> ownedIngredients, int offset) {
        if (ownedIngredients.isEmpty()) {
            return selectDefaultRecipe(pool, dayType, maxCookTime, offset);
        }

        List<Recipe> sortedByMatch = new ArrayList<>(pool);
        sortedByMatch.sort((r1, r2) -> {
            long score1 = r1.ingredients.keySet().stream().filter(ing -> isMatched(ing, ownedIngredients)).count();
            long score2 = r2.ingredients.keySet().stream().filter(ing -> isMatched(ing, ownedIngredients)).count();
            
            if (score1 != score2) {
                return Long.compare(score2, score1);
            }
            
            boolean r1Compat = isDayTypeCompatible(r1, dayType);
            boolean r2Compat = isDayTypeCompatible(r2, dayType);
            if (r1Compat && !r2Compat) return -1;
            if (!r1Compat && r2Compat) return 1;
            return 0;
        });

        return sortedByMatch.get(0);
    }

    private Recipe selectDefaultRecipe(List<Recipe> pool, String dayType, int maxCookTime, int offset) {
        List<Recipe> filtered = new ArrayList<>(pool);
        if ("busy".equalsIgnoreCase(dayType)) {
            filtered.sort(Comparator.comparingInt(r -> r.instructions.size()));
        } else if ("post-workout".equalsIgnoreCase(dayType)) {
            filtered.sort((r1, r2) -> {
                boolean r1p = r1.title.toLowerCase().contains("protein") || r1.title.toLowerCase().contains("chicken") || r1.title.toLowerCase().contains("steak") || r1.title.toLowerCase().contains("egg");
                boolean r2p = r2.title.toLowerCase().contains("protein") || r2.title.toLowerCase().contains("chicken") || r2.title.toLowerCase().contains("steak") || r2.title.toLowerCase().contains("egg");
                if (r1p && !r2p) return -1;
                if (!r1p && r2p) return 1;
                return 0;
            });
        }
        int index = Math.abs(offset) % filtered.size();
        return filtered.get(index);
    }

    private boolean isDayTypeCompatible(Recipe r, String dayType) {
        if ("busy".equalsIgnoreCase(dayType)) {
            return r.instructions.size() <= 3;
        } else if ("post-workout".equalsIgnoreCase(dayType)) {
            String title = r.title.toLowerCase();
            return title.contains("steak") || title.contains("chicken") || title.contains("salmon") || title.contains("egg") || title.contains("protein") || title.contains("tofu");
        }
        return false;
    }

    private boolean isMatched(String ingredientName, Set<String> ownedIngredients) {
        String lowerIng = ingredientName.toLowerCase();
        for (String owned : ownedIngredients) {
            if (lowerIng.contains(owned) || owned.contains(lowerIng)) {
                return true;
            }
        }
        return false;
    }

    private void addIngredients(Map<String, Double> target, Map<String, Double> ingredientsToAdd) {
        for (Map.Entry<String, Double> entry : ingredientsToAdd.entrySet()) {
            target.put(entry.getKey(), target.getOrDefault(entry.getKey(), 0.0) + entry.getValue());
        }
    }

    private String serializeIngredients(Map<String, Double> recipeIngredients, Set<String> ownedIngredients) {
        // Serialized as: Name|Price|status;Name|Price|status
        return recipeIngredients.entrySet().stream()
                .map(entry -> entry.getKey() + "|" + String.format(Locale.US, "%.2f", entry.getValue()) + "|" + (isMatched(entry.getKey(), ownedIngredients) ? "owned" : "extra"))
                .collect(Collectors.joining(";"));
    }
}
