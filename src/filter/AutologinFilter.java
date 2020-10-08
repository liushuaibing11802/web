package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import vo.User;

public class AutologinFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String userName = "";
		String password = "";
		// 1.ֻ��δ��¼���û������Զ���¼

		if ((req.getSession(false)==null)||(session.getAttribute("currentUser") == null)) {
			// 2.ֻ�д����Զ���¼cookie���û������Զ���¼
			Cookie[] cookies = req.getCookies();
			if (cookies != null) {
				for (Cookie c : cookies) {
					String name = c.getName();// ��ȡcookie����
					if ("userName".equals(name)) {
						userName = c.getValue();// ��ȡcookie��ֵ
					} else if ("password".equals(c.getName())) {
						password = c.getValue();
					}
				}
			} else {
				request.setAttribute("info", "���ȵ�¼���ٽ��з��ʣ�");
				request.getRequestDispatcher("/error.jsp").forward(request,
						response);
			}
			/*
			 * if(cookies==null||session.getAttribute("currenterUser")==null){
			 * request.setAttribute("info","���ȵ�¼���ٽ��з��ʣ�");
			 * request.getRequestDispatcher("/error.jsp").forward( request,
			 * response); }else{ for (Cookie c : cookies) { String name =
			 * c.getName();// ��ȡcookie���� if ("userName".equals(name)) { userName
			 * = c.getValue();// ��ȡcookie��ֵ } else if
			 * ("password".equals(c.getName())) { password = c.getValue(); } } }
			 */
			if (userName != null) {
				UserDao userDao = new UserDao();
				if (userDao.get(userName) != null) {
					User currentUser = userDao.get(userName);
					if (currentUser.getPassword().equals(password)) {
						session.setAttribute("currentUser", currentUser);
						request.getRequestDispatcher("/main.jsp").forward(
								request, response);
					}
				}
			}

		}
		chain.doFilter(request, response);
/*		request.setAttribute("info", "���ȵ�¼���ٽ��з��ʣ�");
		request.getRequestDispatcher("/error.jsp").forward(request, response);*/
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
