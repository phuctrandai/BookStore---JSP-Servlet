package bean;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	private HashMap<String, Item> items;

	public HashMap<String, Item> getItems() {
		return items;
	}

	public void setItems(HashMap<String, Item> items) {
		this.items = items;
	}

	public Cart(HashMap<String, Item> items) {
		super();
		this.items = items;
	}

	public Cart() {
		super();
		items = new HashMap<String, Item>();
	}

	public void addToCart(Book book) {
		String bookId = book.getId();
		if(items.containsKey(bookId)) {
			int oldQuality = items.get(bookId).getQuality();
			items.get(bookId).setQuality(oldQuality + 1);
			//items.put(item.getBook().getId(), item);
		}
		else {
			items.put(book.getId(), new Item(book, 1));
		}
	}

	public void updateQuantity(String bookId, int quantity) {
		boolean check = items.containsKey(bookId);
		if (check) {
			items.get(bookId).setQuality(quantity);
		}
	}
	
	public void removeFromCart(String key) {
		if(items.containsKey(key)) {
			items.remove(key);
		}
	}
	
	public int getTotalItem() {
		return items.size();
	}
	
	public long getTotalPrice() {
		long totalPrice = 0;
		for(Map.Entry<String, Item> s : items.entrySet()) {
			totalPrice += s.getValue().getQuality() * s.getValue().getBook().getPrice();
		}
		return totalPrice;
	}
}
