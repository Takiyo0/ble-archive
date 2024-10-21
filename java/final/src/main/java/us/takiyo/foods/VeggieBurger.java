package us.takiyo.foods;

import us.takiyo.extensions.Food;
import us.takiyo.extensions.Ingredient;
import us.takiyo.ingredients.Bun;
import us.takiyo.ingredients.Lettuce;
import us.takiyo.ingredients.Onion;
import us.takiyo.ingredients.Pickle;
import us.takiyo.ingredients.Tomato;

public class VeggieBurger extends Food {
	public VeggieBurger() {
		super("veggie burger", new Ingredient[] {new Bun(), new Lettuce(), new Tomato(), new Onion(), new Pickle(), new Bun()});
	}
}
