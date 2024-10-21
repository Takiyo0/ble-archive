package us.takiyo.foods;

import us.takiyo.extensions.Food;
import us.takiyo.extensions.Ingredient;
import us.takiyo.ingredients.Bacon;
import us.takiyo.ingredients.Bun;
import us.takiyo.ingredients.Lettuce;
import us.takiyo.ingredients.Patty;
import us.takiyo.ingredients.Tomato;

public class BaconBurger extends Food {
	public BaconBurger() {
		super("bacon burger", new Ingredient[] {new Bun(), new Bacon(), new Patty(), new Lettuce(), new Tomato(), new Bun()});
	}
}
