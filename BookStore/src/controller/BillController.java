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

import bean.Bill;
import bean.Customer;
import bo.BillBo;

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
		Bill bill = (Bill) session.getAttribute("bill");
		if (bill == null) {
			bill = new Bill();
			session.setAttribute("bill", bill);
		}
		BillBo cartBo = new BillBo(bill);
		
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

	private void checkOut(BillBo billBo, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException, SQLException, ServletException {
		// Nếu chưa đăng nhập thì chuyển sang trang account
		Object account = request.getSession().getAttribute("userName");
		if (account == null) {
			response.sendRedirect("account");
		} else {
			Customer customer = (Customer) request.getSession().getAttribute("customer");
			billBo.getBill().setCustomerId(customer.getId());

			billBo.saveBill();
			request.setAttribute("command", "checkout");
			request.getRequestDispatcher("bill.jsp").forward(request, response);
		}
	}
	
	private void showBillHistory(BillBo billBo, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
		// Nếu chưa đăng nhập thì chuyển sang trang account
		Object account = request.getSession().getAttribute("userName");
		if (account == null) {
			response.sendRedirect("account");
		} else {
			Customer customer = (Customer) request.getSession().getAttribute("customer");
			int customerId = customer.getId();
			
			ArrayList<Bill> listBill = billBo.getListByCustomerId(customerId);
			
			request.setAttribute("billList", listBill);
			request.setAttribute("command", "billHistory");
			request.getRequestDispatcher("bill.jsp").forward(request, response);
		}
	}
	
	private void pay(BillBo billBo, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		billBo.getBill().setCustomerId(customer.getId());
		
		boolean result = billBo.payBill();
		if(result) {
			request.getSession().removeAttribute("bill");
			request.getRequestDispatcher("paySuccess.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}
}
