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
		// Set tiếng việt =============
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		// Lấy danh sách loại =================
		getDanhSachLoai(request, response);
		
		// Lấy lệnh xử lý ===========
		String command = request.getParameter("command");

		try {
			if (command != null) {
				// Tìm kiếm ================
				if (command.equals("search")) {
					timKiemSach(request, response);
				}
				else if(command.equals("")) {
					
				}
			}
			// Lấy tất cả sách =========
			else {
				getSach(request, response);
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void getDanhSachLoai(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<Loai> loaiList = null;
		try {
			loaiList = loaiBo.getLoai();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		request.setAttribute("loaiList", loaiList);
	}

	private void getSach(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException {
		HashMap<String, Book> bookList = null;
		String categoryId = request.getParameter("categoryId");
		// Phân trang
		int pageNumber = 1;
		if (request.getParameter("pageNumber") != null) {
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}
		// Lấy hết sách
		if (categoryId == null) {
			bookList = bookBo.getBookList(pageNumber, BOOK_PER_PAGE);
			request.setAttribute("totalPage", bookBo.getTotalPage(BOOK_PER_PAGE));
		}
		// Lấy sách theo loại =========
		else {
			bookList = bookBo.getBooksByCategoryId(pageNumber, BOOK_PER_PAGE, categoryId);
			request.setAttribute("totalPage", bookBo.getTotalPage(BOOK_PER_PAGE, categoryId));
		}
		request.setAttribute("bookList", bookList);
	}

	private void timKiemSach(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException, SQLException {
		// Tiêu chí tìm kiếm
		String optionSearch = request.getParameter("optionSearch");
		HashMap<String, Book> bookList = null;
		// Tìm theo tựa/tên sách
		if (optionSearch.equals("0")) {
			bookList = bookBo.findByName(request.getParameter("keyWord"));
		}
		request.setAttribute("totalPage", 1);
		request.setAttribute("bookList", bookList);
	}
}
