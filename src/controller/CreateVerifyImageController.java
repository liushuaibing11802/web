package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CreateImage;

//@WebServlet(urlPatterns="/servlet/CreateVerifyImageController")
public class CreateVerifyImageController extends HttpServlet {

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
		
		//����֤��ͼƬ���ɵĹ��̷�װ��CreateImage����
		CreateImage createVCodeImage=new CreateImage();
		//������λ����ַ�
		String vCode=createVCodeImage.createCode();
		//��ȡHttpSession����
		HttpSession session=request.getSession();
		//����������λ����ַ��������session��(�����session�е�������һ���Ự��Χ�ڣ��������ȫ�ֹ���)���Ա���֤
		session.setAttribute("verify", vCode);
		//���÷��ص�����
		response.setContentType("img/jpeg");
		//�������������Ӧ����--��֤��ͼƬ����һ�ξ�Ҫˢ��һ�Σ����Բ����л������
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		//������֤��ʧЧʱ��
		response.setDateHeader("Expires", 0);
		//���ֽ�������ȥ������img��src����ȥ��������
		BufferedImage image=createVCodeImage.CreateImage(vCode);
		//��ȡ�ֽ����������
		ServletOutputStream out=response.getOutputStream();
		ImageIO.write(image, "JPEG", out);
		out.flush();
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
/*
		//����֤��ͼƬ���ɵĹ��̷�װ��CreateImage����
		CreateImage createVCodeImage=new CreateImage();
		//������λ����ַ�
		String vCode=createVCodeImage.createCode();
		//��ȡHttpSession����
		HttpSession session=request.getSession();
		//����������λ����ַ��������session��(�����session�е�������һ���Ự��Χ�ڣ��������ȫ�ֹ���)���Ա���֤
		session.setAttribute("verify", vCode);
		//���÷��ص�����
		response.setContentType("img/jpeg");
		//�������������Ӧ����--��֤��ͼƬ����һ�ξ�Ҫˢ��һ�Σ����Բ����л������
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		//������֤��ʧЧʱ��
		response.setDateHeader("Expires", 0);
		//���ֽ�������ȥ������img��src����ȥ��������
		BufferedImage image=createVCodeImage.CreateImage(vCode);
		//��ȡ�ֽ����������
		ServletOutputStream out=response.getOutputStream();
		ImageIO.write(image, "JPEG", out);
		out.flush();
		out.close();*/
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
