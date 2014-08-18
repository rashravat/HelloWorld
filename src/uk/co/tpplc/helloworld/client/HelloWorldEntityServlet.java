package uk.co.tpplc.helloworld.client;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.tpplc.helloworld.sessionbeans.stateless.HelloWorldEntityService;

public class HelloWorldEntityServlet extends HttpServlet {
	private static final long serialVersionUID = 8529851488388832273L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String helloText = (String) request.getParameter("helloText");
		
		try {
			InitialContext ctx = new InitialContext();
			HelloWorldEntityService helloWorldService = 
				(HelloWorldEntityService) ctx.lookup("HelloWorld/HelloWorldEntityClientBean/local");
			
			Integer messageId = helloWorldService.createMessage(helloText);			
			String helloWorld = helloWorldService.helloWorld(messageId);
			
			request.setAttribute("helloWorldEntityMessage", helloWorld);
			
		} catch (Exception e) {
			
			request.setAttribute("systemError", e.getClass().getSimpleName() + ":" + e.getMessage());
			
		} finally {
			
			RequestDispatcher rd = request.getRequestDispatcher("/pages/helloWorld.jsp");
			rd.forward(request, response);
			
		}
	}
}
