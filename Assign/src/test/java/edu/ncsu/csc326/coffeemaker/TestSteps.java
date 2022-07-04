/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 * 
 * Permission has been explicitly granted to the University of Minnesota 
 * Software Engineering Center to use and distribute this source for 
 * educational purposes, including delivering online education through
 * Coursera or other entities.  
 * 
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including 
 * fitness for purpose.
 * 
 * 
 * Modified 20171114 by Ian De Silva -- Updated to adhere to coding standards.
 * 
 */
package edu.ncsu.csc326.coffeemaker;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import cucumber.api.DataTable;
import cucumber.api.Delimiter;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc326.coffeemaker.CoffeeMakerUI.Mode;
import edu.ncsu.csc326.coffeemaker.CoffeeMakerUI.Status;
import edu.ncsu.csc326.coffeemaker.UICmd.ChooseService;

/**
 * Contains the step definitions for the cucumber tests.  This parses the 
 * Gherkin steps and translates them into meaningful test steps.
 */
public class TestSteps {
	
	private Recipe recipe1;
	private Recipe recipe2;
	private Recipe recipe3;
	private Recipe recipe4;
	private Recipe recipe5;
	private Recipe new_recipe;
	private CoffeeMakerUI coffeeMakerMain;
	private CoffeeMaker coffeeMaker;
	private RecipeBook recipeBook;
	
	private void initialize() {
		recipeBook = new RecipeBook();
		coffeeMaker = new CoffeeMaker(recipeBook, new Inventory());
		coffeeMakerMain = new CoffeeMakerUI(coffeeMaker);
	}
	
	
	private boolean recipe_exists(String recipeName) {
    	for (Recipe i : recipeBook.getRecipes()) {
    		if (i.getName().toString().equals(recipeName)) {
    			return true;
    		}
    	}
		return false;
	}
	
	private boolean recipe_exists_int(int No_recipe) {
    	if (recipeBook.getRecipes()[No_recipe] != null ) {
    		return true;
    	}else {
    		return false;
    	}
	}
	

	private int look_pos_recipe(String recipeName) {
    	int pos = 0;
    	for (Recipe i : coffeeMaker.getRecipes()) {
    		if (i.getName().toString().equals(recipeName)) {
    			break;
    		}
    		pos++;
    	}
		return pos;
	}
	
	
    @Given("^an empty recipe book$")
    public void an_empty_recipe_book() throws Throwable {
        initialize();
    }


    @Given("a default recipe book")
	public void a_default_recipe_book() throws Throwable {
    	initialize();
    	
		//Set up for r1
		recipe1 = new Recipe();
		recipe1.setName("Coffee");
		recipe1.setAmtChocolate("0");
		recipe1.setAmtCoffee("3");
		recipe1.setAmtMilk("1");
		recipe1.setAmtSugar("1");
		recipe1.setPrice("50");
		
		//Set up for r2
		recipe2 = new Recipe();
		recipe2.setName("Mocha");
		recipe2.setAmtChocolate("20");
		recipe2.setAmtCoffee("3");
		recipe2.setAmtMilk("1");
		recipe2.setAmtSugar("1");
		recipe2.setPrice("75");
		
		//Set up for r3
		recipe3 = new Recipe();
		recipe3.setName("Latte");
		recipe3.setAmtChocolate("0");
		recipe3.setAmtCoffee("3");
		recipe3.setAmtMilk("3");
		recipe3.setAmtSugar("1");
		recipe3.setPrice("100");
		
		//Set up for r4
		recipe4 = new Recipe();
		recipe4.setName("Hot Chocolate");
		recipe4.setAmtChocolate("4");
		recipe4.setAmtCoffee("0");
		recipe4.setAmtMilk("1");
		recipe4.setAmtSugar("1");
		recipe4.setPrice("65");
		
		//Set up for r5 (added by MWW)
		recipe5 = new Recipe();
		recipe5.setName("Super Hot Chocolate");
		recipe5.setAmtChocolate("6");
		recipe5.setAmtCoffee("0");
		recipe5.setAmtMilk("1");
		recipe5.setAmtSugar("1");
		recipe5.setPrice("100");

		recipeBook.addRecipe(recipe1);
		recipeBook.addRecipe(recipe2);
		recipeBook.addRecipe(recipe3);
		recipeBook.addRecipe(recipe4);
		
	}
    
    
    @Then("^main menu raises up$")
    public void main_menu_raises() throws Throwable {
        assertEquals(coffeeMakerMain.getMode(), Mode.WAITING);
        System.out.println("Status: " + coffeeMakerMain.getStatus());
    }
    
    
    @When("^user selects the key ([1-6])$")
    public void user_selects_the_key(int key) throws Throwable {
    	System.out.println(String.format("Key # %d selected", key));
        coffeeMakerMain.UI_Input(new ChooseService(key));
    }
    
    
    @Then("^the current mode is \"([^\"]+)\"$")
    public void the_current_mode_is(String thisMode) throws Throwable {
    	System.out.println(String.format("You are in %s mode", coffeeMakerMain.getMode().toString()));
        assertEquals(coffeeMakerMain.getMode().toString(), thisMode);
    }
    
    
    @When("^user selects add_recipe$")
    public void user_select_add_recipe() throws Throwable {
    	System.out.println("Add Recipe option...");
        coffeeMakerMain.UI_Input(new ChooseService(1));
        new_recipe = new Recipe();
    }
    
    
    @And("^user puts the name of the recipe \"([^\"]+)\"$")
    public void user_puts_the_name_of_the_recipe(String name) throws Throwable {
    	new_recipe.setName(name);
    }
    
    
    @And("^user prompts a set of mexican recipes$")
    public void user_prompts_a_set_of_mexican_recipes(DataTable mexican_recipes) throws Throwable {
    	List<Map<String, String>> rows = mexican_recipes.asMaps(String.class, String.class);
        
    	boolean success = false;
        for (Map<String, String> columns : rows) {
            new_recipe.setName(columns.get("name"));
            new_recipe.setPrice(columns.get("price"));
            new_recipe.setAmtCoffee(columns.get("amtCoffee"));
            new_recipe.setAmtMilk(columns.get("amtMilk"));
            new_recipe.setAmtSugar(columns.get("amtSugar"));
            new_recipe.setAmtChocolate(columns.get("amtChocolate"));
            success = recipeBook.addRecipe(new_recipe);
            new_recipe = new Recipe();
        }
        if(success) {
        	System.out.println("All recipes: ");
            for (Recipe i : recipeBook.getRecipes()) {
            	System.out.println("Name => " + i.getName());
            }
            coffeeMakerMain.mode = Mode.WAITING;
        }
        
    }
    
    
    @And("^user puts the rest of the recipe elements$")
    public void user_puts_the_rest_of_the_recipe_elements(@Delimiter("") List<Integer> args) throws Throwable {
    	int[] arr = args.stream().mapToInt(Integer::intValue).toArray();
    	new_recipe.setPrice(String.format("%d", arr[0]));
    	new_recipe.setAmtCoffee(String.format("%d", arr[1]));  
    	new_recipe.setAmtMilk(String.format("%d", arr[2]));  
    	new_recipe.setAmtSugar(String.format("%d", arr[3]));  
    	new_recipe.setAmtChocolate(String.format("%d", arr[4]));
    	boolean success = recipeBook.addRecipe(new_recipe);
    	if (success) {
    		System.out.println("New recipe added => " + new_recipe.getName());
    		coffeeMakerMain.mode = Mode.WAITING;
    	}else {
    		int full = 0;
    		for (Recipe i : recipeBook.getRecipes()) {
    			if (i != null) {
    				if (i.getName().equals(new_recipe.getName())) {
    					break;
    				}else {
    					full++;
    				}
    			}
    		}
    		if (full < 4) {
    			System.out.println("Your recipe already exists, you must edit it => " + new_recipe.getName());
    		}else {
    			System.out.println("The recipe list is full, please remove one recipe before add this one.");
    		}
    		
    		coffeeMakerMain.mode = Mode.WAITING;
    	}
    }
    
    
	@When("^user selects delete_recipe$")
    public void user_selects_delete_recipe() throws Throwable {
    	System.out.println("Delete Recipe option...");
        coffeeMakerMain.UI_Input(new ChooseService(2));
    }
    
    
    @And("^looking for the recipe \"([^\"]+)\"$")
    public void user_looking_for_the_recipe(String recipeName) {
    	int empty = 0;
    	for (Recipe i : recipeBook.getRecipes()) {
			if (i == null) {
				empty++;
			}
		}
    	if (empty == 4) {
    		System.out.println("Recipe empty / Null");
    		coffeeMakerMain.mode = Mode.WAITING;
    	} else {
    		if (recipe_exists(recipeName) == false) {
    			System.out.println("Recipe doesn't exist!");
        		coffeeMakerMain.mode = Mode.WAITING;
    		} else {
    			assertTrue(recipe_exists(recipeName));
    		}
    	} 	  	
    }
    

	@And("^having deleted the recipe \"([^\"]+)\"$")
    public void having_deleted_the_recipe(String recipeName) {
		boolean success = false;
		String recipe_deleted = recipeBook.deleteRecipe(look_pos_recipe(recipeName));
		if (recipe_deleted != null) { 
			success = true;
			System.out.println("Recipe deleted: " + recipe_deleted);
		}
		if (success) {
			coffeeMakerMain.mode = Mode.WAITING;
		}
    }
	
	
	@When("^user selects edit_recipe$")
    public void user_selects_edit_recipe() throws Throwable {
    	System.out.println("Edit Recipe option...");
        coffeeMakerMain.UI_Input(new ChooseService(3));
        new_recipe = new Recipe();
    }
	
	
	@And("^user selects the recipe No (\\d+)$")
	public void user_selects_the_recipe_No(int No_recipe) {
		if (No_recipe > 4 || No_recipe <= 0) {
			System.out.println("Index Out, try with other number between 1-4.");
			coffeeMakerMain.mode = Mode.WAITING;
		} else {
			assertTrue(recipe_exists_int(No_recipe));
		}
		
	}
	
	
	@And("^user updates the recipe selected$")
    public void user_updates_the_recipe_selected(@Delimiter("") List<Integer> args) throws Throwable {
    	int[] arr = args.stream().mapToInt(Integer::intValue).toArray();
    	String recipe_to_edit = recipeBook.getRecipes()[arr[0]-1].getName();
    	new_recipe.setPrice(String.format("%d", arr[1]));
    	new_recipe.setAmtCoffee(String.format("%d", arr[2]));  
    	new_recipe.setAmtMilk(String.format("%d", arr[3]));  
    	new_recipe.setAmtSugar(String.format("%d", arr[4]));  
    	new_recipe.setAmtChocolate(String.format("%d", arr[5]));
    	String recipe_edited = recipeBook.editRecipe(arr[0]-1, new_recipe);
    	assertEquals(recipe_edited, recipe_to_edit);
    	if (recipeBook.getRecipes()[arr[0]-1].getName().equals("")) {
    		System.out.println("Recipe updated => " + recipe_edited);
    		coffeeMakerMain.mode = Mode.WAITING;
    	}
    }
	
	
	@When("^user selects add_inventory$")
	public void user_selects_add_inventory() throws Throwable {
		System.out.println("Add inventory option...");
        coffeeMakerMain.UI_Input(new ChooseService(4));
	}
	
	
	@And("^user types the quantities$")
	public void user_types_the_quantities(@Delimiter("") List<Integer> args) throws Throwable {
		int[] arr = args.stream().mapToInt(Integer::intValue).toArray();
		coffeeMaker.addInventory(String.format("%d", arr[0]), String.format("%d", arr[1]), String.format("%d", arr[2]), String.format("%d", arr[3]));
		System.out.println("Status message => " + coffeeMakerMain.getStatus());
		if (coffeeMakerMain.getStatus().toString().equals("OK")) {
			System.out.println("Inventory updated");
    		coffeeMakerMain.mode = Mode.WAITING;
		}
	}
	
	
	@When("^user selects check_inventory$")
	public void user_selects_check_inventory() throws Throwable {
		System.out.println("Check Inventory option...");
        coffeeMakerMain.UI_Input(new ChooseService(5));
	}
	
	
	@And("^inventory info raises up$")
	public void inventory_info_raises_up() throws Throwable {
		System.out.println("Inventory: \n" + coffeeMaker.checkInventory());
		coffeeMakerMain.mode = Mode.WAITING;
	}
	
	
	@When("^user selects purchase_coffee$")
	public void user_selects_purchase_coffee() throws Throwable {
		System.out.println("Purchase coffee option...");
        coffeeMakerMain.UI_Input(new ChooseService(6));
	}
	
	
	@And("^user validate purchase (\\d+)$")
	public void validate_purchase(int No_recipe) throws Throwable {
		System.out.println("Recipe No " + No_recipe + " selected: " + recipeBook.getRecipes()[No_recipe - 1].getName());
		int remainder = coffeeMaker.makeCoffee(No_recipe - 1, coffeeMakerMain.moneyInserted);
		System.out.println("moneyInserted: " + coffeeMakerMain.moneyInserted + " remainder: " + remainder);
		if (coffeeMakerMain.moneyInserted == remainder) {
			coffeeMakerMain.status = Status.INSUFFICIENT_FUNDS;
		}else {
			System.out.println("Beverage purchased => " + recipeBook.getRecipes()[No_recipe - 1].getName());
			coffeeMakerMain.status = Status.OK;
			coffeeMakerMain.moneyInTray += remainder;
			coffeeMakerMain.moneyInserted = 0;
		}
		coffeeMakerMain.mode = Mode.WAITING;
	}
	

	@When("^user selects insert_money$")
	public void user_selects_insert_money() {
		System.out.println("Inserting money option...");
        coffeeMakerMain.UI_Input(new ChooseService(7));
	}
	
	
	@And("^user insert_money (\\d+)$")
	public void user_insert_money(int amount) {
		coffeeMakerMain.moneyInserted = amount;
		System.out.println("moneyInserted: " + coffeeMakerMain.moneyInserted);
		coffeeMakerMain.status = Status.OK;
		coffeeMakerMain.mode = Mode.WAITING;
	}
	
}
