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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		// Lấy danh sách loại =================
		ArrayList<Loai> loaiList = null;
		try {
	   		loaiList = loaiBo.getLoai();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		request.setAttribute("loaiList", loaiList);
		
		// Xử lý đăng nhập, đăng xuất, đăng ký ==============
		String command = request.getParameter("command");
		if(command != null) {
			String prevPage = (String) request.getSession().getAttribute("prevPage");
			// Đăng nhập ============
			if(command.equals("login")) {
				String userName = request.getParameter("userName");
				String password = request.getParameter("password");
				Account account = null;
				// Nếu đăng nhập thành công
				try {
					account = accountBo.GetAccount(userName, password);
					if(account != null) {
						request.getSession().setAttribute("userName", userName);
					// Kiểm tra vai trò tài khoản
						if(account.getRole().equals("Khách hàng")) {
							Customer customer = customerBo.GetCustomer(account.getUserName());
							request.getSession().setAttribute("customer", customer);
						}
						response.sendRedirect(prevPage);
					}
					// Nếu đăng nhập thất bại
					else {
						request.getRequestDispatcher("login.jsp").forward(request, response);
					}
				} catch (SQLException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			// Đăng xuất ===========
			else if(command.equals("logout")) {
				request.getSession().removeAttribute("userName");
				response.sendRedirect("home");
			}
			// Đăng ký ============
			else if(command.equals("signUp")) {
				String 	name = request.getParameter("customerName"),
						address = request.getParameter("customerAddress"),
						phoneNumber = request.getParameter("customerPhoneNumber"),
						email = request.getParameter("customerEmailAddress"),
						userName = request.getParameter("accountName"),
						password = request.getParameter("accountPassword"),
						role = request.getParameter("accountRole");
				try {
					boolean result = customerBo.AddCustomer(name, address, phoneNumber, email, userName, password, role);
					if(result)
						response.sendRedirect("home");
					else {
						request.getRequestDispatcher("login.jsp").forward(request, response);
					}
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		}
		// Đăng nhập thất bại ===============
		else {
			request.getRequestDispatcher("login.jsp").forward(request, response);
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
