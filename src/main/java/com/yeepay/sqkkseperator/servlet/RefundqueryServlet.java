package com.yeepay.sqkkseperator.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yeepay.sqkkseperator.config.Config;
import com.yeepay.sqkkseperator.service.YeepayService;


public class RefundqueryServlet extends HttpServlet {
	 String refundqueryUri=	Config.getInstance().getValue("refundqueryUri");
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		super.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		response.setContentType("html/text");
		PrintWriter pw=response.getWriter();
		
		String merchantno=Config.getInstance().getValue("merchantno");
		String requestno=format(request.getParameter("requestno"));
		String yborderid=format(request.getParameter("yborderid"));
		
		Map<String,String> map=new HashMap<String,String>();
		   map.put("merchantno", merchantno);
		   map.put("requestno", requestno);
		   map.put("yborderid", yborderid);
		  
		   Map<String,String> yopresponsemap=YeepayService.yeepayYOP(map,refundqueryUri);
			  
		   request.setAttribute("yopresponsemap",yopresponsemap==null?"系统异常":yopresponsemap);
	       RequestDispatcher view	= request.getRequestDispatcher("/jsp/45refundqueryresponse.jsp");
	       view.forward(request, response);
	}
	public static String format(String text){
		return text!=null?text.trim():"";
	}



}
