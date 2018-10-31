package bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Bill {
	private HashMap<String, Item> items;
	private int id;
	private int customerId;
	private Date dOP;
	private boolean paid;
	
	public HashMap<String, Item> getItems() {
		return items;
	}
	public void setItems(HashMap<String, Item> items) {
		this.items = items;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Date getDOP() {
		return dOP;
	}
	public void setDOP(Date dOP) {
		this.dOP = dOP;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	
	public Bill() {
		this.items = new HashMap<>();
	}
	
	public Bill(int id, int customerId, Date dOP, boolean paid, HashMap<String, Item> items) {
		super();
		this.items = items;
		this.id = id;
		this.customerId = customerId;
		this.dOP = dOP;
		this.paid = paid;
	}
	
	public int getTotalItem() {
		return this.getItems().size();
	}
	
	public long getTotalPrice() {
		long totalPrice = 0;
		for (Map.Entry<String, Item> s : this.getItems().entrySet()) {
			totalPrice += s.getValue().getQuantity() * s.getValue().getBook().getPrice();
		}
		return totalPrice;
	}
}
