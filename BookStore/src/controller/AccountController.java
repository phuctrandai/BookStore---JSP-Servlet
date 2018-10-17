package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AccountController
 */
@WebServlet(name = "AccountController", urlPatterns = { "/account" })
public class AccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String command = request.getParameter("command");
		if(command != null) {
			// Login ============
			if(command.equals("login")) {
				String userName = request.getParameter("userName");
				String password = request.getParameter("password");
				
				if(userName.equals("Hello") && password.equals("123")) {
					request.getSession().setAttribute("userName", userName);
					response.sendRedirect("home");
				}
				else {
					response.getWriter().append("Login faile -- Served at: ").append(request.getContextPath());
				}
			}
			// Logout ===========
			else if(command.equals("logout")) {
				request.getSession().removeAttribute("userName");
				response.sendRedirect("home");
			}
		}
		else {
			request.getRequestDispatcher("home").forward(request, response);
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
