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
	public final int BOOK_PER_PAGE = 24;

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
		// Set character encoding for Vietnamese =============
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		// Load sách lên ======================
		HashMap<String, Book> bookList = null;
		String categoryId = request.getParameter("categoryId");
		
		// Phân trang
		int pageNumber = 1;
		if(request.getParameter("pageNumber") != null) {
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}
		
		String command = request.getParameter("command");
		
		try {
			if(command != null) {
		// Tìm kiếm ================
				if(command.equals("search")) {
		// Tiêu chí tìm kiếm
					String optionSearch = request.getParameter("optionSearch");
		// Tìm theo tựa/tên sách
					if(optionSearch.equals("0")) {
						bookList = bookBo.findByName(request.getParameter("keyWord"));
					}
					request.setAttribute("totalPage", 1);
				}
			}
		// Lấy tất cả sách =========
			else if(categoryId == null) {
				bookList = bookBo.getBookList(pageNumber, BOOK_PER_PAGE);
				request.setAttribute("totalPage", bookBo.getTotalPage(BOOK_PER_PAGE));
			}
		// Lấy sách theo loại =========
			else {
				bookList = bookBo.getBooksByCategoryId(pageNumber, BOOK_PER_PAGE, categoryId);
				request.setAttribute("totalPage", bookBo.getTotalPage(BOOK_PER_PAGE, categoryId));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("bookList", bookList);
		
		// Lấy danh sách loại =================
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
