package us.takiyo.foods;

import us.takiyo.extensions.Food;
import us.takiyo.extensions.Ingredient;
import us.takiyo.ingredients.Bun;
import us.takiyo.ingredients.Cheese;
import us.takiyo.ingredients.Patty;

public class CheeseBurger extends Food {
	public CheeseBurger() {
		super("cheese burger", new Ingredient[] {new Bun(), new Cheese(), new Patty(), new Bun()});
	}
}
