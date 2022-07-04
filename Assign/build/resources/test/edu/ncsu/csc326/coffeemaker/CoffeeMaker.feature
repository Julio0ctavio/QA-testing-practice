Feature: CoffeeMakerFeature

In this feature, we are going to test the user stories and use cases for the CoffeeMaker
Example.  We have provided a CoffeeMakerMain.java file that you can use to examine the 
modal behavior in the coffee maker and issue UI commands to use it, so that we can 
adequately test the user stories.

Hints: to catch the mistakes, you might need to add out of range cases for 
recipes and amounts of ingredients.  Also, be sure to check machine state
after running the user story:
  - Are the amounts of ingredients correct?
  - Is the system in the right mode?
  - Is the status what you expect?
  - Is the change produced correct?
  - etc.
	
Scenario Outline: Waiting State
      Priority: 1 Story Points: 2
      If the Coffee Maker is not in use it waits for user input. There are six different 
      options of user input: 1) add recipe, 2) delete a recipe, 3) edit a recipe, 
      4) add inventory, 5) check inventory, and 6) purchase beverage.
      
      For this scenario, what we will do is try each of the six user inputs and make sure 
      that the coffee maker ends up in the appropriate mode.  This would be a good place
      for a scenario outline with a table that described user inputs and expected final states.
      
      You might also want to try a couple of exceptional values to see what the 
      Coffee Maker does.
      
        
   Given a default recipe book
   When user selects the key <key>
   Then the current mode is "<mode>"
   
   Examples:
	   | key | mode              |
	   | 1   | ADD_RECIPE        |
	   | 2   | DELETE_RECIPE     |
	   | 3   | EDIT_RECIPE       |
	   | 4   | ADD_INVENTORY     |
	   | 5   | CHECK_INVENTORY   |
	   | 6   | PURCHASE_BEVERAGE |
      
   
Scenario: Add a Recipe
      Priority: 1 Story Points: 2

      Only three recipes may be added to the CoffeeMaker. A recipe consists of a name, 
      price, units of coffee, units of milk, units of sugar, and units of chocolate. 
      Each recipe name must be unique in the recipe list. Price must be handled as an 
      integer. A status message is printed to specify if the recipe was successfully 
      added or not. Upon completion, the CoffeeMaker is returned to the waiting state.   
      
      For this scenario, you should try to add a recipe to the recipe book, and check to
      see whether the coffee maker returns to the Waiting state.  
      
   Given an empty recipe book
   When user selects add_recipe
   And user puts the name of the recipe "MokaLoka" 
   And user puts the rest of the recipe elements
     | 45 |
     | 5  |
     | 3  | 
     | 3  |
     | 5  |
   Then main menu raises up 
   
Scenario: Delete a Recipe
      Priority: 2 Story Points: 1

      A recipe may be deleted from the CoffeeMaker if it exists in the list of recipes in the
      CoffeeMaker. The recipes are listed by their name. Upon completion, a status message is
      printed and the Coffee Maker is returned to the waiting state.  

   Given a default recipe book
   When user selects delete_recipe
   And looking for the recipe "Latte"
   And having deleted the recipe "Latte" 
   Then main menu raises up
   
   
Scenario: Delete an empty recipe
   Given an empty recipe book
   When user selects delete_recipe
   And looking for the recipe "Latte"
   Then main menu raises up
   

Scenario: Edit a Recipe
      Priority: 2 Story Points: 1

      A recipe may be edited in the CoffeeMaker if it exists in the list of recipes in the
      CoffeeMaker. The recipes are listed by their name. After selecting a recipe to edit, the user
      will then enter the new recipe information. A recipe name may not be changed. Upon
      completion, a status message is printed and the Coffee Maker is returned to the waiting
      state.  

   Given a default recipe book
   When user selects edit_recipe
   And user selects the recipe No 1 
   And user updates the recipe selected
     | 1  |
     | 40 |
     | 3  |
     | 2  | 
     | 2  |
     | 1  |
   Then main menu raises up
   
  
Scenario: Edit a recipe with wrong values when you look on the list
	Given a default recipe book
   	When user selects edit_recipe
   	And user selects the recipe No 5 
    Then main menu raises up
    
   
Scenario: Add Inventory
      Priority: 1 Story Points: 2

      Inventory may be added to the machine at any time from the main menu, and is added to
      the current inventory in the CoffeeMaker. The types of inventory in the CoffeeMaker are
      coffee, milk, sugar, and chocolate. The inventory is measured in integer units. Inventory
      may only be removed from the CoffeeMaker by purchasing a beverage. Upon completion, a
      status message is printed and the CoffeeMaker is returned to the waiting state.   
      
   Given a default recipe book
   When user selects add_inventory
   And user types the quantities
     | 8  |
     | 10 |
     | 6  |
     | 6  |
   Then main menu raises up
   
Scenario: Check Inventory 
      Priority: 2 Story Points: 1
    
      Inventory may be checked at any time from the main menu. The units of each item in the
      inventory are displayed. Upon completion, the Coffee Maker is returned to the waiting state.  
      
   Given a default recipe book
   When user selects check_inventory
   And inventory info raises up
   Then main menu raises up   
   
Scenario: Purchase Beverage
      Priority: 1 Story Points: 2

      The user selects a beverage and inserts an amount of money. The money must be an
      integer. If the beverage is in the RecipeBook and the user paid enough money the
      beverage will be dispensed and any change will be returned. The user will not be able to
      purchase a beverage if they do not deposit enough money into the CoffeeMaker. A user's
      money will be returned if there is not enough inventory to make the beverage. Upon
      completion, the Coffee Maker displays a message about the purchase status and is
      returned to the main menu.  
      
   Given a default recipe book
   When user selects purchase_coffee
   And user validate purchase 2
   Then main menu raises up
   When user selects insert_money
   And user insert_money 75
   Then main menu raises up
   When user selects purchase_coffee
   And user validate purchase 2
   Then main menu raises up


Scenario: Adding a bunch of recipes
  	Given an empty recipe book
  	When user selects add_recipe
  	And user prompts a set of mexican recipes
	  | name   		  | price | amtCoffee | amtMilk | amtSugar | amtChocolate |
	  | Choco_Grandma | 60    |  10       |  2      | 2        | 2            |
	  | Azteca        | 85    |  12       |  2      | 0        | 2            |
	  | Moreliano     | 80    |   12      |  4      | 5        | 2            |
	  | De_Olla       | 50    |    6      |  0      | 2        | 0            |
	Then main menu raises up 


Scenario: Adding a recipe that exists
  	Given a default recipe book
	When user selects add_recipe
    And user puts the name of the recipe "Mocha" 
    And user puts the rest of the recipe elements
      | 45 |
      | 5  |
      | 3  | 
      | 3  |
      | 5  |
    Then main menu raises up
    
    
Scenario: Adding a recipe in a full list
  	Given a default recipe book
	When user selects add_recipe
    And user puts the name of the recipe "Black_Horse" 
    And user puts the rest of the recipe elements
      | 65 |
      | 3  |
      | 9  | 
      | 3  |
      | 5  |
    Then main menu raises up

