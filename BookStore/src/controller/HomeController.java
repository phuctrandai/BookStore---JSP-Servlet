package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Book;
import bean.Loai;
import bo.BookBo;
import bo.LoaiBo;

/**
 * Servlet implementation class HomeController
 */
@WebServlet(name = "HomeController", urlPatterns = { "/home" })
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final int BOOK_PER_PAGE = 24;

	private LoaiBo loaiBo = null;
	private BookBo bookBo = null;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HomeController() {
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
		
		// Get Books ======================
		String categoryId = request.getParameter("categoryId");
		int pageNumber = 1;
		HashMap<String, Book> bookList = null;
		
		if(request.getParameter("pageNumber") != null) {
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}
		
		try {
			if(categoryId == null) {
				bookList = bookBo.getBookList(pageNumber, BOOK_PER_PAGE);
				request.setAttribute("totalPage", bookBo.getTotalPage(BOOK_PER_PAGE));
			}
			else {
				bookList = bookBo.getBooksByCategoryId(pageNumber, BOOK_PER_PAGE, categoryId);
				request.setAttribute("totalPage", bookBo.getTotalPage(BOOK_PER_PAGE, categoryId));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("bookList", bookList);
		
		// Get book category =================
		ArrayList<Loai> loaiList = null;
		try {
	   		loaiList = loaiBo.getLoai();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		request.setAttribute("loaiList", loaiList);
				
		// Forward ========================
		request.getRequestDispatcher("home.jsp").forward(request, response);
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

}
