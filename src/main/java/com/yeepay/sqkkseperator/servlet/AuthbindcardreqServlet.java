package com.yeepay.sqkkseperator.servlet;

import com.yeepay.sqkkseperator.config.Config;
import com.yeepay.sqkkseperator.service.YeepayService;
import util.DateUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class AuthbindcardreqServlet extends HttpServlet{
String  authbindcardreqUri=Config.getInstance().getValue("authbindcardreqUri");
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		super.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		
		String merchantno		=Config.getInstance().getValue("merchantno");
		String requestno 		=format(request.getParameter("requestno"));
		String identityid   		=format(request.getParameter("identityid"));		
		String identitytype  	=format(request.getParameter("identitytype"));
		String cardno   			=format(request.getParameter("cardno"));
		String idcardno  		=format(request.getParameter("idcardno"));
		String idcardtype   		=format(request.getParameter("idcardtype"));		
		String username  		=format(request.getParameter("username"));
		String phone   			=format(request.getParameter("phone"));
		String issms 		 	=format(request.getParameter("issms"));
		String advicesmstype   	=format(request.getParameter("advicesmstype"));
		String smstemplateid   	=format(request.getParameter("smstemplateid"));		
		String smstempldatemsg  =format(request.getParameter("smstempldatemsg"));
		String avaliabletime   	=format(request.getParameter("avaliabletime"));
		String callbackurl  		=format(request.getParameter("callbackurl"));
		String requesttime   	=format(request.getParameter("requesttime"));
		String authtype   		=format(request.getParameter("authtype"));
		String remark  			=format(request.getParameter("remark"));
		String extinfos   		=format(request.getParameter("extinfos"));
		
		Map<String,String> map	=	new HashMap<String,String>();
	   	map.put("merchantno", merchantno);
	   	map.put("requestno", requestno);
	   	map.put("identityid", identityid);
	   	map.put("identitytype", identitytype);
	   	map.put("cardno", cardno);
	   	map.put("idcardno", idcardno);
	   	map.put("idcardtype", idcardtype);
	   	map.put("username", username);
	   	map.put("phone", phone);
	   	map.put("issms", issms);
	   	map.put("advicesmstype", advicesmstype);
	   	map.put("smstemplateid", smstemplateid);
	   	map.put("smstempldatemsg", smstempldatemsg);
	   	map.put("avaliabletime", avaliabletime);
	   	map.put("callbackurl", callbackurl);
	   	map.put("requesttime", requesttime);
	   	map.put("authtype", authtype);
	   	map.put("remark", remark);
	   	map.put("extinfos", extinfos);

	   	Map<String,String> yopresponsemap	=	YeepayService.yeepayYOP(map,authbindcardreqUri);


	   	request.setAttribute("yopresponsemap",yopresponsemap==null?"系统异常":yopresponsemap);
	   	RequestDispatcher view	= request.getRequestDispatcher("/jsp/11authbindcardreqResponse.jsp");
	   	view.forward(request, response);
		
	}
 	public static String format(String text){
	 return text==null?"":text.trim();
 }

	public static void main(String[] args) throws IOException {
		Config config = Config.getInstance();
		String merchantno		= Config.getInstance().getValue("merchantno");
		String requestno 		= "20190409002";
		String identityid   	= "F1001";
		String identitytype  	= "USER_ID";
		String cardno   		= "6222022314007119475";
		String idcardno  		= "511502199012168436";
		String idcardtype   	= "ID";
		String username  		= "李亮";
		String phone   			= "18728344025";
		String issms 		 	= "true";
		String advicesmstype   	= "MESSAGE";
		//String smstemplateid   	= format(request.getParameter("smstemplateid"));
		//String smstempldatemsg  =format(request.getParameter("smstempldatemsg"));
		String avaliabletime   	= "120";
		//String callbackurl  	= format(request.getParameter("callbackurl"));
		String requesttime   	= DateUtil.getDate();
		String authtype   		= "COMMON_FOUR";
		String remark  			= "TEST";
		//String extinfos   		=format(request.getParameter("extinfos"));

		Map<String,String> map	=	new HashMap<String,String>();
		map.put("appKey",config.getValue("appKey"));
		map.put("ts",String.valueOf(System.currentTimeMillis()));
		map.put("merchantno", merchantno);
		map.put("requestno", requestno);
		map.put("identityid", identityid);
		map.put("identitytype", identitytype);
		map.put("cardno", cardno);
		map.put("idcardno", idcardno);
		map.put("idcardtype", idcardtype);
		map.put("username", username);
		map.put("phone", phone);
		map.put("issms", issms);
		map.put("advicesmstype", advicesmstype);
		//map.put("smstemplateid", smstemplateid);
		//map.put("smstempldatemsg", smstempldatemsg);
		map.put("avaliabletime", avaliabletime);
		//map.put("callbackurl", callbackurl);
		map.put("requesttime", requesttime);
		map.put("authtype", authtype);
		map.put("remark", remark);
		//map.put("extinfos", extinfos);
		String rootUrl = config.getValue("serverRoot");
		String url = config.getValue("authbindcardreqUri");

		Map<String,String> yopresponsemap	=	YeepayService.yeepayYOP(map,url);
	}
}
