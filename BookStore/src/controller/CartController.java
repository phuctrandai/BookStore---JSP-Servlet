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

import bean.Bill;
import bean.Book;
import bean.Category;
import bo.BillBo;
import bo.BookBo;
import bo.CategoryBo;

/**
 * Servlet implementation class CartController
 */
@WebServlet(name = "CartController", urlPatterns = { "/cart" })
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CategoryBo categoryBo = null;
	private BookBo bookBo = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CartController() {
		super();
		bookBo = new BookBo();
		categoryBo = new CategoryBo();
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
		request.getSession().setAttribute("prevPage", "cart");

		// ==================
		HttpSession session = request.getSession();
		String command = request.getParameter("command");

		// Lấy danh sách loại =================
		getListCategory(request, response);

		// Kiểm tra giỏ hàng, khỏi tạo nếu chưa có =============
		Bill bill = (Bill) session.getAttribute("bill");
		if (bill == null) {
			bill = new Bill();
			session.setAttribute("bill", bill);
		}
		BillBo cartBo = new BillBo(bill);

		// Xử lý nghiệp vụ =================
		if (command != null) {
			switch (command) {
			// Thêm một sản phẩm vào giỏ hàng
			case "add": {
				addToBill(cartBo, request, response);
				break;
			}
			// Cập nhật số lượng của sản phẩm trong giỏ hàng
			case "modify": {
				updateBill(cartBo, request, response);
				break;
			}
			default:
				break;
			}
		} else {
			session.setAttribute("cart", cartBo.getBill());
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
		ArrayList<Category> categoryList = null;
		try {
			categoryList = categoryBo.getList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		request.setAttribute("categoryList", categoryList);
	}

	private void addToBill(BillBo billBo, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Lấy sách theo mã
		String bookId = request.getParameter("bookId");
		Book book = null;
		try {
			book = bookBo.getById(bookId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		billBo.addToBill(book);
		request.getSession().setAttribute("bill", billBo.getBill());
		request.getRequestDispatcher("cart.jsp").forward(request, response);
	}

	private void updateBill(BillBo billBo, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// Lấy sách theo mã
		String bookId = request.getParameter("bookId");

		if (request.getParameter("updateBtn") != null) {
			int itemQuantity = Integer.parseInt(request.getParameter("itemQuality"));
			billBo.updateQuantity(bookId, itemQuantity);
		} else if (request.getParameter("removeBtn") != null) {
			billBo.removeFromBill(bookId);
		}
		// Currency format =================
		Locale locale = new Locale("vie", "VN");
		NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

		request.getSession().setAttribute("cart", billBo.getBill());
		response.getWriter().println(nf.format(billBo.getTotalPrice()) + ";");
		response.getWriter().println(billBo.getTotalItem());
	}
}
