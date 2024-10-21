package us.takiyo.extensions;

public class Food {
	private String name;
	TakiyoList<Ingredient> ingredients = new TakiyoList<Ingredient>();
	
	public Food(String name, Ingredient[] ingredients) {
		this.setName(name);
		for (Ingredient ing:ingredients) this.ingredients.add(ing);
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}
	
	public int ingredientsLength() {
		return this.ingredients.size();
	}
	
	public TakiyoList<Ingredient> getIngredients() {
		return this.ingredients;
	}
}
