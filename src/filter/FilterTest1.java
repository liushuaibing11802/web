package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import vo.User;
import dao.UserDao;

public class FilterTest1 implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// ��ServletRequest���Ͳ���ת��ΪHttpServletRequest����
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
/*		String URL = "/exercise1/servlet/DownloadController;/exercise1/download.jsp;"
				+ "/exercise1/servlet/GetDownloadListController";*/
		// 1.�û�δ��¼
		if (session.getAttribute("currentUser") == null) {
			request.setAttribute("info", "���ȵ�¼���ٽ��з��ʣ�");
			String forwardPath = "/error.jsp";
			request.getRequestDispatcher(forwardPath).forward(request, resp);
			;
		}
		// 2.�û��ѵ�¼
		else {
			// ��ͨ�û�ֻ���������ͨ�û��Ľ���
			// ����Ա���������ͨ�û��͹���Ա�Ľ���
			String url = request.getRequestURI();
			User user=(User) session.getAttribute("currentUser");
			UserDao dao=new UserDao();
			String URL=dao.Check(user.getUserName());
			if (!URL.contains(url)) {
				request.setAttribute("info", "���ã�������ͨ�û������߱����ʹ���Ա�����Ȩ�ޣ�");
				String forwardPath = "/error.jsp";
				request.getRequestDispatcher(forwardPath)
						.forward(request, resp);
				;
			} else {
				chain.doFilter(req, resp);
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
