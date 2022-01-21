#Recipes Web Services

The program is a multi-user web service based on Spring Boot that allows storing, retrieving, updating, and deleting recipes.


##Endpoints:
* __POST /api/register__ receives a JSON object with two fields: email (string), and password (string).
Both fields are required and must be valid: email should contain @ and . symbols,
password should contain at least 8 characters and shouldn't be blank.
* __POST /api/recipe/new__ receives a recipe as a JSON object and returns a JSON object with one __id__ field.
This is a uniquely generated number by which we can identify and retrieve a recipe later.
All fields are required, string fields can't be blank, arrays should have at least one item.
* __PUT /api/recipe/{id}__ receives a recipe as a JSON object and updates a recipe with a specified __id__.
Also, update the date field too. All fields are required, string fields can't be blank,
arrays should have at least one item).
* __GET /api/recipe__ returns all recipes as a JSON object.
* __GET /api/recipe/{id}__ returns a recipe with a specified id as a JSON object.
* __GET /api/recipe/search__ takes one of the two mutually exclusive query parameters:
  * __category__ – if this parameter is specified, it returns a JSON array of all recipes of the specified category.
  Search is case-insensitive, sort the recipes by date (newer first).
  * __name__ – if this parameter is specified, it returns a JSON array of all recipes with the names that contain the specified parameter.
  Search is case-insensitive, sort the recipes by date (newer first).
* __DELETE /api/recipe/{id}__ deletes a recipe with a specified __id__.

##Examples:
__POST /api/register__

    {
        "email": "Cook_Programmer@somewhere.com",
        "password": "RecipeInBinary"
    }
    

__POST /api/recipe/new__

    {
       "name": "Fresh Mint Tea",
       "category": "beverage",
       "description": "Light, aromatic and refreshing beverage, ...",
       "ingredients": ["boiled water",
                       "honey",
                       "fresh mint leaves"],
       "directions": ["Boil water",
                      "Pour boiling hot water into a mug",
                      "Add fresh mint leaves",
                      "Mix and let the mint leaves seep for 3-5 minutes",
                      "Add honey and mix again"]
    }
    

__PUT /api/recipe/1__

    {
       "name": "Warming Ginger Tea",
       "category": "beverage",
       "description": "Ginger tea is a warming drink for cool weather, ...",
       "ingredients": ["1 inch ginger root, minced",
                       "1/2 lemon, juiced",
                       "1/2 teaspoon manuka honey"],
       "directions": ["Place all ingredients in a mug and fill with warm water (not too hot so you keep the beneficial honey compounds in tact)",
                      "Steep for 5-10 minutes",
                      "Drink and enjoy"]
    }
    