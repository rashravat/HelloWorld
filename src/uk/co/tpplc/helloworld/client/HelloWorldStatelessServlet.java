package uk.co.tpplc.helloworld.client;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.tpplc.helloworld.sessionbeans.stateless.HelloWorldStatelessService;

public class HelloWorldStatelessServlet extends HttpServlet {
	private static final long serialVersionUID = 3914555320493920456L;
	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	   try{
		   InitialContext ctx = new InitialContext();
		   
		   HelloWorldStatelessService helloWorldService = 
			   (HelloWorldStatelessService) ctx.lookup("HelloWorld/HelloWorldStatelessBean/local");
		   
		   String helloWorld = helloWorldService.helloWorld();
		   
		   request.setAttribute("helloWorldStatelessMessage", helloWorld);
		   
		} catch (NamingException e){
			request.setAttribute("systemError", e.getMessage());
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/pages/helloWorld.jsp");
		rd.forward(request, response);

	}

}
