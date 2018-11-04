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
import bean.Bill;
import bean.Book;
import bean.Customer;
import bo.BillBo;
import bo.BookBo;

/**
 * Servlet implementation class CartController
 */
@WebServlet(name = "BillController", urlPatterns = { "/bill" })
public class BillController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BookBo bookBo = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BillController() {
		super();
		bookBo = new BookBo();
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
		request.getSession().setAttribute("prevPage", "bill");
		
// Lấy giỏ hàng
		Bill bill = null;
		// Neu chua dang nhap thi lay tu session
		if(request.getSession().getAttribute("customer") == null) {
			bill = (Bill) request.getSession().getAttribute("bill");
			if(bill == null) bill = new Bill();
		}
		// Neu da dang nhap thi lay hoa don cua khach hang chua thanh toan tu csdl
		else {
			Customer customer = (Customer) request.getSession().getAttribute("customer");
			try {
				BillBo billBo = new BillBo();
				bill = billBo.getBill(customer.getId());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		request.getSession().setAttribute("bill", bill);

// Xử lý nghiệp vụ =================
		String command = request.getParameter("command");
		if (command == null) {
			request.getRequestDispatcher("cart.jsp").forward(request, response);
		} 
		else {
			switch (command) {
			
			// Thêm một sản phẩm vào giỏ hàng
			case "add": {
				try {
					addToBill(request, response);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			
			// Cập nhật số lượng của sản phẩm trong giỏ hàng
			case "modify": {
				try {
					updateBill(request, response);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			
			// Quản lý đơn hàng
			case "billHistory": {
				try {
					showBillHistory(request, response);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			
			// Thanh toán đơn hàng
			case "pay": {
				try {
					pay(request, response);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			
			// Xóa hóa đơn
			case "delete": {
				try {
					deleteBill(request, response);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				break;
			}

			//
			default:
				break;
			}
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
	
	/*
	 * Thêm vào hóa đơn
	 */
	private void addToBill(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException, SQLException {
		BillBo billBo = new BillBo();
		Bill bill;
		int customerId = -1;
		String bookId = request.getParameter("bookId");
		Book book = bookBo.getById(bookId);
		
		// Neu chua dang nhap thi luu vao session
		if(request.getSession().getAttribute("customer") == null) {
			bill = (Bill) request.getSession().getAttribute("bill");
			try {
				billBo.setBill(bill);
				billBo.addToBill(book, false, customerId);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		// Nguoc lai lay tu csdl
		else {
			Customer customer = (Customer) request.getSession().getAttribute("customer");
			customerId = customer.getId();
			billBo.addToBill(book, true, customerId);
			bill = billBo.getBill(customerId);
		}
		request.getSession().setAttribute("bill", bill);
		request.getRequestDispatcher("cart.jsp").forward(request, response);
	}
	
	/*
	 * Cập nhật hóa đơn
	 */
	private void updateBill(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException, SQLException {
		Bill bill = (Bill) request.getSession().getAttribute("bill");
		BillBo billBo = new BillBo(bill);
		int customerId = -1;
		String bookId = request.getParameter("bookId");
				
		// Cập nhật số lượng sách trong hóa đơn
		if (request.getParameter("updateBtn") != null) {
			int itemQuantity = Integer.parseInt(request.getParameter("itemQuantity"));
			if(request.getSession().getAttribute("customer") == null) {
				billBo.updateQuantity(bookId, itemQuantity, false, customerId);
			} else {
				Customer customer = (Customer) request.getSession().getAttribute("customer");
				customerId = customer.getId();
				billBo.updateQuantity(bookId, itemQuantity, true, customerId);
				bill = billBo.getBill(customerId);
			}
		}
		// Xóa sách khỏi hóa đơn
		else if (request.getParameter("removeBtn") != null) {
			if(request.getSession().getAttribute("customer") == null) {
				billBo.removeBook(0, bookId, false);
			} else {
				Customer customer = (Customer) request.getSession().getAttribute("customer");
				customerId = customer.getId();
				billBo.removeBook(customerId, bookId, true);
				bill = billBo.getBill(customerId);
			}
		}
		
		// Định dạnh hiển thị tiền =================
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vie", "VN"));

		request.getSession().setAttribute("bill", bill);
		
		// Trả kết quả về ( ajax )
		response.getWriter().println(nf.format(bill.getTotalPrice()) + ";");
		response.getWriter().println(bill.getTotalItem());
	}
	
	/*
	 * Thanh toán hóa đơn
	 */
	private void pay(HttpServletRequest request, HttpServletResponse response) 
			throws ClassNotFoundException, SQLException, ServletException, IOException {
		// Nếu chưa đăng nhập thì chuyển sang trang account
		Object customer = request.getSession().getAttribute("customer");
		if (customer == null) {
			// Lưu lại trang trước khi đăng nhập
			request.getSession().setAttribute("prevCommand", "pay");
			response.sendRedirect("account?command=login");
		
		} else {
			int customerId = ((Customer) customer).getId();
			BillBo billBo = new BillBo();
			boolean result = billBo.payBill(customerId);
			if(result) {
				request.getSession().setAttribute("bill", billBo.getBill(customerId));
				request.getRequestDispatcher("paySuccess.jsp").forward(request, response);
			} else {
				//request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		}
	}
	
	/*
	 * Xem lịch sử mua hàng
	 */
	private void showBillHistory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException, SQLException {
		
		// Nếu chưa đăng nhập thì chuyển sang trang account
		Object customer = request.getSession().getAttribute("customer");
		if (customer == null) {
			
			// Lưu lại trang trước khi đăng nhập
			request.getSession().setAttribute("prevCommand", "billHistory");
			response.sendRedirect("account?command=login");
		
		} else {
			BillBo billBo = new BillBo();
			int customerId = ((Customer) customer).getId();
			ArrayList<Bill> billList = billBo.getListByCustomerId(customerId);
			
			request.getSession().removeAttribute("prevCommand");
			request.setAttribute("billList", billList);
			request.getRequestDispatcher("billHistory.jsp").forward(request, response);
		}
	}
	
	
	private void deleteBill(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
		BillBo billBo = new BillBo();
		int billId = Integer.parseInt(request.getParameter("billId"));
		billBo.deleteBill(billId);
		
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		int customerId = customer.getId();
		ArrayList<Bill> listBill = billBo.getListByCustomerId(customerId);
		
		request.setAttribute("billList", listBill);
		request.getRequestDispatcher("billHistory.jsp").forward(request, response);
	}
}
