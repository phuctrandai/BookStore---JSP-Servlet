package bean;

import java.util.Date;

public class Book {
	private String id;
	private String name;
	private String author;
	private String image;
	private long price;
	private int chapNumber;
	private long quantity;
	private Date inputDate;
	private String categoryId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getChapNumber() {
		return chapNumber;
	}
	public void setChapNumber(int chapNumber) {
		this.chapNumber = chapNumber;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public Book() {
	}
	public Book(String id, String name, String author, String image, long price, int chapNumber, long quantity,
			Date inputDate, String categoryId) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.image = image;
		this.price = price;
		this.chapNumber = chapNumber;
		this.quantity = quantity;
		this.inputDate = inputDate;
		this.categoryId = categoryId;
	}
}
