package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.Download;
import dao.DownloadDao;

public class DownloadController extends HttpServlet {

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
	/*	String path=request.getParameter("path");
		response.setCharacterEncoding("utf-8");// ���ý��ַ���"UTF-8"����������ͻ��������
		// ͨ��������Ӧͷ�����������UTF-8�ı�����ʾ���ݣ����������仰����ô�������ʾ�Ľ�������
		response.setHeader("content-type", "text/html;charset=utf-8");
		
		PrintWriter out=response.getWriter();
		out.write(path);
		out.flush();
		out.close();*/
		//1.��ȡҪ���ص��ļ��ľ���·��
	//	String ph=request.getParameter("path");
		String id=request.getParameter("id");
		DownloadDao dao=new DownloadDao();
		Download dd=dao.get(Integer.parseInt(id));
		String path=request.getServletContext().getRealPath(dd.getPath());
		//2.��ȡҪ���ص��ļ���
		String fileName=path.substring(path.lastIndexOf("\\")+1);
		//3.����content-disposition��Ӧͷ��������������ص���ʽ���ļ�
		response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
		//4.��ȡҪ���ص��ļ�������
		InputStream in=new FileInputStream(path);
		int len=0;
		//5.�������ݻ�����
		byte[] buffer=new byte[1024];
		//6.ͨ��response�����ȡOutputStream��
		ServletOutputStream out=response.getOutputStream();
		//7.��FileInputStream��д�뵽buffer������
		while((len=in.read(buffer))!=-1){
			//8.ʹ��OutputStream��������������������ͻ��������
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
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
