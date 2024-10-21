package us.takiyo.foods;

import us.takiyo.extensions.Food;
import us.takiyo.extensions.Ingredient;
import us.takiyo.ingredients.Bacon;
import us.takiyo.ingredients.Bun;
import us.takiyo.ingredients.Cheese;
import us.takiyo.ingredients.Lettuce;
import us.takiyo.ingredients.Onion;
import us.takiyo.ingredients.Patty;
import us.takiyo.ingredients.Tomato;

public class DeluxeBurger extends Food {
	public DeluxeBurger() {
		super("deluxe burger", new Ingredient[] {new Bun(), new Cheese(), new Bacon(), new Patty(), new Lettuce(), new Tomato(), new Onion(), new Bun()});
	}
}
