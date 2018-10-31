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
import bean.Category;
import bo.BookBo;
import bo.CategoryBo;

/**
 * Servlet implementation class HomeController
 */
@WebServlet(name = "HomeController", urlPatterns = { "/home" })
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public final int BOOK_PER_PAGE = 24;

	private CategoryBo categoryBo = null;
	private BookBo bookBo = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HomeController() {
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
		
		// Set tiếng việt =============
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		request.getSession().setAttribute("prevPage", "home");
		
		// Lấy danh sách loại sách =================
		getCategoryList(request, response);
		
		// Lấy lệnh xử lý ===========
		String command = request.getParameter("command");

		try {
			if (command != null) {
				// Tìm kiếm ================
				if (command.equals("search")) {
					findBook(request, response);
				}
				else if(command.equals("")) {
					
				}
			}
			// Lấy tất cả sách =========
			else {
				getBook(request, response);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		// Forward ========================
		request.getRequestDispatcher("home.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void getCategoryList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ArrayList<Category> categoryList = null;
		try {
			categoryList = categoryBo.getList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		request.setAttribute("categoryList", categoryList);
	}

	private void getBook(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {
		
		HashMap<String, Book> bookList = null;
		String categoryId = request.getParameter("categoryId");
		
		// Phân trang
		int pageNumber = 1; // Mặc định
		if (request.getParameter("pageNumber") != null) {
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}
		// Lấy hết sách
		if (categoryId == null) {
			bookList = bookBo.getList(pageNumber, BOOK_PER_PAGE);
			request.setAttribute("totalPage", bookBo.getTotalPage(BOOK_PER_PAGE));
		}
		// Lấy sách theo loại
		else {
			bookList = bookBo.getByCategoryId(categoryId, pageNumber, BOOK_PER_PAGE);
			request.setAttribute("totalPage", bookBo.getTotalPage(BOOK_PER_PAGE, categoryId));
		}
		request.setAttribute("bookList", bookList);
	}

	private void findBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException, SQLException {
		
		// Tiêu chí tìm kiếm
		String optionSearch = request.getParameter("optionSearch");
		String keyWord = request.getParameter("keyWord");
		HashMap<String, Book> bookList = null;
		
		if(keyWord != "") {
			// Tìm theo tựa/tên sách
			if (optionSearch.equals("0")) {	
				bookList = bookBo.findByName(keyWord);
			}
		}
		
		if(bookList != null) {
			request.setAttribute("totalPage", bookList.size() / BOOK_PER_PAGE);
		}
		request.setAttribute("bookList", bookList);
	}
}
