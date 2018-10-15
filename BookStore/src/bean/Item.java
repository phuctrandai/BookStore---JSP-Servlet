package bean;

public class Item {
	private Book book;
	private int quality;
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getQuality() {
		return quality;
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}
	
	public Item(Book book, int quality) {
		super();
		this.book = book;
		this.quality = quality;
	}
	public Item() {
	}
}
