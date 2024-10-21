package us.takiyo.extensions;

public class Inventory {
	private String itemName;
	private int quantity;
	
	public Inventory(String itemName, int quantity) {
		this.setItemName(itemName);
		this.setQuantity(quantity);
	}
	
	public Inventory(String data) {
		String[] datas = data.split("#");
		try {
			this.itemName = datas[0];
			this.quantity = Integer.parseInt(datas[1]);
		} catch (Exception ignored) {
			System.out.println("Failed to load inventory");
		}
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getItemName() {
		return itemName;
	}

	private void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getSaveData() {
		return String.format("%s#%d", this.getItemName(), this.getQuantity());
	}
}