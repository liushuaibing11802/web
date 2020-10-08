package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vo.User;
import dao.UserDao;

public class LoginController extends HttpServlet {

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		String code=request.getParameter("userCode");
		HttpSession session=request.getSession();
		String output="";
		RequestDispatcher reqDispatcher =this.getServletContext().getRequestDispatcher("/error.jsp");
		if(code.equals(session.getAttribute("verify"))){
			output+="ok";
		}
		else{
			reqDispatcher.forward(request, response);
			//output+="no";
		}
		response.setCharacterEncoding("utf-8");// ���ý��ַ���"UTF-8"����������ͻ��������
		// ͨ��������Ӧͷ�����������UTF-8�ı�����ʾ���ݣ����������仰����ô�������ʾ�Ľ�������
		response.setHeader("content-type", "text/html;charset=utf-8");
		
		PrintWriter out=response.getWriter();
		out.write(output);
		out.flush();
		out.close();*/
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		String vcode=request.getParameter("vcode");
		HttpSession session=request.getSession();
		String saveVcode=(String) session.getAttribute("verify");
		String forwardPath="";
		if(!vcode.equals(saveVcode)){
			request.setAttribute("info", "��֤�벻��ȷ��");
			forwardPath="/error.jsp";
		}else{
			UserDao userDao=new UserDao();
			if(userDao.get(userName)==null){
				request.setAttribute("info", "��������û��������ڣ�");
				forwardPath="/error.jsp";
			}else{
				User currentUser=userDao.get(userName);
				if(!currentUser.getPassword().equals(password)){
					request.setAttribute("info", "����������벻��ȷ��");
					forwardPath="/error.jsp";
				}else{
					//һ�������½
				//	request.getAttribute("autologin").equals("true")
					if("true".equals(request.getParameter("autologin"))){
						Cookie cookie1=new Cookie("userName",userName);
						Cookie cookie2=new Cookie("password",password);
						cookie1.setPath(request.getContextPath());
						cookie2.setPath(request.getContextPath());
						cookie1.setMaxAge(60*60*24*7);
						cookie2.setMaxAge(60*60*24*7);
						response.addCookie(cookie1);
						response.addCookie(cookie2);
					}
					session.setAttribute("currentUser", currentUser);
					forwardPath="/main.jsp";
				}
			}
		}
		RequestDispatcher rd =request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
