package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Book;
import bean.Cart;
import bo.BookBo;

/**
 * Servlet implementation class CartController
 */
@WebServlet(name = "CartController", urlPatterns = { "/cart" })
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BookBo bookBo = null;
    	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartController() {
        super();
        bookBo = new BookBo();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get general ==================
		HttpSession session = request.getSession();
		String command = request.getParameter("command");
		
		// Get cart or create if it's null =============
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
	        cart = new Cart();
	        session.setAttribute("cart", cart);
	    }
		
		// Currency format =================
		Locale locale = new Locale("vie", "VN");
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
		
		if(command != null) {
			// Get book by id
			String bookId = request.getParameter("bookId");
			Book book = null;
			try {
				book = bookBo.getBookById(bookId);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
			switch (command) {
			// Insert an item into cart
			case "add": {
				cart.addToCart(book);
				session.setAttribute("cart", cart);
				request.getRequestDispatcher("cart.jsp").forward(request, response);
				break;
			}
			// Update quantity of the item in cart
			case "modify": {
				if(request.getParameter("updateBtn") != null) {
					int itemQuantity = Integer.parseInt(request.getParameter("itemQuality"));
					cart.updateQuantity(bookId, itemQuantity);
				}
				else if(request.getParameter("removeBtn") != null) {
					cart.removeFromCart(bookId);
				}
				session.setAttribute("cart", cart);
				response.getWriter().println(nf.format(cart.getTotalPrice()) + ";");
				response.getWriter().println(cart.getTotalItem());
			}
			default : break;
			}
		}
		else {
			session.setAttribute("cart", cart);
			request.getRequestDispatcher("cart.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
}
