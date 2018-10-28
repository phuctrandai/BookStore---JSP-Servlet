package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Book;
import bean.Cart;
import bean.Loai;
import bo.BookBo;
import bo.CartBo;
import bo.LoaiBo;

/**
 * Servlet implementation class CartController
 */
@WebServlet(name = "CartController", urlPatterns = { "/cart" })
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private LoaiBo loaiBo = null;
	private BookBo bookBo = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CartController() {
		super();
		bookBo = new BookBo();
		loaiBo = new LoaiBo();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set tiếng việt ======
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		// ==================
		HttpSession session = request.getSession();
		String command = request.getParameter("command");

		// Lấy danh sách loại =================
		getListCategory(request, response);

		// Kiểm tra giỏ hàng, khỏi tạo nếu chưa có =============
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		CartBo cartBo = new CartBo(cart);

		// Xử lý nghiệp vụ =================
		if (command != null) {
			switch (command) {
			// Thêm một sản phẩm vào giỏ hàng
			case "add": {
				addToCart(cartBo, request, response);
				break;
			}
			// Cập nhật số lượng của sản phẩm trong giỏ hàng
			case "modify": {
				updateCart(cartBo, request, response);
				break;
			}
			default:
				break;
			}
		} else {
			session.setAttribute("cart", cartBo.getCart());
			request.getRequestDispatcher("cart.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void getListCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<Loai> loaiList = null;
		try {
			loaiList = loaiBo.getLoai();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		request.setAttribute("loaiList", loaiList);
	}

	private void addToCart(CartBo cartBo, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Lấy sách theo mã
		String bookId = request.getParameter("bookId");
		Book book = null;
		try {
			book = bookBo.getBookById(bookId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		cartBo.addToCart(book);
		request.getSession().setAttribute("cart", cartBo.getCart());
		request.getRequestDispatcher("cart.jsp").forward(request, response);
	}

	private void updateCart(CartBo cartBo, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// Lấy sách theo mã
		String bookId = request.getParameter("bookId");

		if (request.getParameter("updateBtn") != null) {
			int itemQuantity = Integer.parseInt(request.getParameter("itemQuality"));
			cartBo.updateQuantity(bookId, itemQuantity);
		} else if (request.getParameter("removeBtn") != null) {
			cartBo.removeFromCart(bookId);
		}
		// Currency format =================
		Locale locale = new Locale("vie", "VN");
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

		request.getSession().setAttribute("cart", cartBo.getCart());
		response.getWriter().println(nf.format(cartBo.getTotalPrice()) + ";");
		response.getWriter().println(cartBo.getTotalItem());
	}
}
