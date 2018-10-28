package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Cart;
import bean.Customer;
import bo.CartBo;

/**
 * Servlet implementation class BillController
 */
@WebServlet(name = "BillController", urlPatterns = { "/bill" })
public class BillController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BillController() {
		super();
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

		// Lấy lệnh xử lý ==================
		HttpSession session = request.getSession();
		String command = request.getParameter("command");
		
		// Kiểm tra giỏ hàng, khỏi tạo nếu chưa có =============
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		CartBo cartBo = new CartBo(cart);
		
		// Xử lý nghiệp vụ ================
		if(command != null) {
			switch(command) {
			// Thanh toán đơn hàng ==========
			case "checkout": {
				try {
					checkOut(cartBo, request, response);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			case "billHistory": {
				try {
					showBillHistory(cartBo, request, response);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			case "pay": {
				try {
					pay(cartBo, request, response);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			default: 
				break;
			}
		} else {
			request.getRequestDispatcher("bill.jsp").forward(request, response);
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

	private void checkOut(CartBo cartBo, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException, SQLException, ServletException {
		// Nếu chưa đăng nhập thì chuyển sang trang account
		Object account = request.getSession().getAttribute("userName");
		if (account == null) {
			response.sendRedirect("account");
		} else {
			Customer customer = (Customer) request.getSession().getAttribute("customer");
			cartBo.getCart().setMaKhachHang(customer.getId());

			cartBo.luuHoaDon();
			request.setAttribute("command", "checkout");
			request.getRequestDispatcher("bill.jsp").forward(request, response);
		}
	}
	
	private void showBillHistory(CartBo cartBo, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
		// Nếu chưa đăng nhập thì chuyển sang trang account
		Object account = request.getSession().getAttribute("userName");
		if (account == null) {
			response.sendRedirect("account");
		} else {
			Customer customer = (Customer) request.getSession().getAttribute("customer");
			int maKhachHang = customer.getId();
			
			ArrayList<Cart> listBill = cartBo.getDanhSachHoaDon(maKhachHang);
			
			request.setAttribute("listBill", listBill);
			request.setAttribute("command", "billHistory");
			request.getRequestDispatcher("bill.jsp").forward(request, response);
		}
	}
	
	private void pay(CartBo cartBo, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		cartBo.getCart().setMaKhachHang(customer.getId());
		
		boolean result = cartBo.thanhToan();
		if(result) {
			request.getSession().removeAttribute("cart");
			request.getRequestDispatcher("paySuccess.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}
}
