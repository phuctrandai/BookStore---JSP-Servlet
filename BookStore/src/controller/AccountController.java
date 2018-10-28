package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Account;
import bean.Customer;
import bean.Loai;
import bo.AccountBo;
import bo.CustomerBo;
import bo.LoaiBo;

/**
 * Servlet implementation class AccountController
 */
@WebServlet(name = "AccountController", urlPatterns = { "/account" })
public class AccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private LoaiBo loaiBo = null;
	private CustomerBo customerBo = null;
	private AccountBo accountBo = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccountController() {
		super();

		loaiBo = new LoaiBo();
		customerBo = new CustomerBo();
		accountBo = new AccountBo();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// set tiếng việt =====
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		// Lấy danh sách loại =================
		getCategoryList(request, response);
		
		// Lấy lệnh xử lý ==========
		String command = request.getParameter("command");
		
		// Xử lý ===========
		if (command != null) {
			// Đăng nhập ============
			if (command.equals("login")) {
				String userName = request.getParameter("userName");
				String password = request.getParameter("password");
				login(userName, password, request, response);
			}
			// Đăng xuất ===========
			else if (command.equals("logout")) {
				logOut(request, response);
			}
			// Đăng ký ============
			else if (command.equals("signUp")) {
				signUp(request, response);
			}
		} else {
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void getCategoryList(HttpServletRequest request, HttpServletResponse response) {

		ArrayList<Loai> loaiList = null;
		try {
			loaiList = loaiBo.getLoai();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		request.setAttribute("loaiList", loaiList);
	}

	private void login(String userName, String password, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {		
		try {
			Account account = accountBo.GetAccount(userName, password);
			
			// Nếu đăng nhập thành công
			if (account != null) {
				request.getSession().setAttribute("userName", userName);
				
				Customer customer = customerBo.GetCustomer(account.getUserName());
				request.getSession().setAttribute("customer", customer);
				
				// Trở lại trang trước đó
				request.setAttribute("loginResult", true);
				String prevPage = (String) request.getSession().getAttribute("prevPage");
				response.sendRedirect(prevPage);
			}
			// Nếu đăng nhập thất bại - đi đến trang login
			else {
				request.setAttribute("loginResult", false);
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void logOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute("userName");
		request.getSession().removeAttribute("customer");
		request.getSession().removeAttribute("cart");
		response.sendRedirect("home");
	}
	
	private void signUp(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String 	name = request.getParameter("customerName"),
				address = request.getParameter("customerAddress"),
				phoneNumber = request.getParameter("customerPhoneNumber"),
				email = request.getParameter("customerEmailAddress"),
				userName = request.getParameter("accountName"),
				password = request.getParameter("accountPassword"),
				role = request.getParameter("accountRole");
		try {
			boolean result = customerBo.AddCustomer(name, address, phoneNumber, email, userName, password, role);
			if (result) {
				login(userName, password, request, response);
				response.sendRedirect("home");
			} else {
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
