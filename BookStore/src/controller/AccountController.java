package controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Account;
import bean.Bill;
import bean.Customer;
import bo.AccountBo;
import bo.BillBo;
import bo.CustomerBo;

/**
 * Servlet implementation class AccountController
 */
@WebServlet(name = "AccountController", urlPatterns = { "/account" })
public class AccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CustomerBo customerBo = null;
	private AccountBo accountBo = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccountController() {
		super();

		customerBo = new CustomerBo();
		accountBo = new AccountBo();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set tiếng việt =====
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		// Lấy lệnh xử lý ==========
		String command = request.getParameter("command");
		
		// Xử lý ===========
		if (command != null) {
			
			// Đăng nhập ============
			if (command.equals("doLogin")) {
				String userName = request.getParameter("userName");
				String password = request.getParameter("password");
				login(userName, password, request, response);
			}
			
			// Đăng xuất ===========
			else if (command.equals("doLogout")) {
				logOut(request, response);
			}
			
			// Đăng ký ============
			else if (command.equals("doSignUp")) {
				signUp(request, response);
			}
			
			// Hiển thị form đăng nhập
			else {
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} else {
			request.getRequestDispatcher("error.jsp").forward(request, response);
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
	 * Đăng nhập
	 */
	private void login(String userName, String password, HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {		
		
		try {
			// Tìm tài khoản tương ứng
			Account account = accountBo.getAccount(userName, password);
			
			// Nếu có thì login thành công
			if (account != null) {
				
				// Lấy tài khoản khách hàng
				Customer customer = customerBo.getCustomer(account.getUserName());
				
				// Lưu các sản phẩm trong giỏ hàng vào csdl
				Object bill = request.getSession().getAttribute("bill");
				if(bill != null) {
					Bill b = (Bill) bill;
					b.setCustomerId(customer.getId());
					BillBo billBo = new BillBo(b);
					billBo.saveBill(customer.getId(), b.getItems());
				}
				
				// Lưu lại quyền đăng nhập
				request.getSession().setAttribute("userName", userName);
				request.getSession().setAttribute("customer", customer);
				
				// Trở lại trang trước đó
				String prevPage = (String) request.getSession().getAttribute("prevPage");
				String location = prevPage;
				if(prevPage.equals("bill")) {
					String prevCommand = (String) request.getSession().getAttribute("prevCommand");
					if(prevCommand != null) {
						if(prevCommand.equals("pay")) {
							
						} else {
							location += "?command=" + prevCommand;
						}
					}
				}
				response.sendRedirect(location);
			}
			
			// Nếu đăng nhập thất bại - đi đến trang login
			else {
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Đăng xuất
	 */
	private void logOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute("userName");
		request.getSession().removeAttribute("customer");
		request.getSession().removeAttribute("bill");
		request.getSession().removeAttribute("prevCommand");
		request.getSession().removeAttribute("prevPage");
		response.sendRedirect("home");
	}
	
	/*
	 * Đăng ký tài khoản khách hàng
	 */
	private void signUp(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String 	name = request.getParameter("customerName"),
				address = request.getParameter("customerAddress"),
				phoneNumber = request.getParameter("customerPhoneNumber"),
				email = request.getParameter("customerEmailAddress"),
				userName = request.getParameter("accountName"),
				password = request.getParameter("accountPassword"),
				role = request.getParameter("accountRole");
		try {
			boolean result = customerBo.addCustomer(name, address, phoneNumber, email, userName, password, role);
			if (result) {
				login(userName, password, request, response);
				//response.sendRedirect("home");
			} else {
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
