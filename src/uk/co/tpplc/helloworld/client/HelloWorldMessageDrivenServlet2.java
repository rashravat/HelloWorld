package uk.co.tpplc.helloworld.client;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.tpplc.helloworld.sessionbeans.stateless.HelloWorldStatelessMessageService;

public class HelloWorldMessageDrivenServlet2 extends HttpServlet {

	private static final long serialVersionUID = -8448984768361576582L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		
		InitialContext ctx = new InitialContext();
		
		HelloWorldStatelessMessageService service = (HelloWorldStatelessMessageService)
			ctx.lookup("HelloWorld/HelloWorldStatelessMessageBean/local");
		
		service.helloWorldMessage(request.getParameter("helloText"));
		
		request.setAttribute("helloWorldMessageDriven2", "Your message has been added to a queue and will be processed shortly");
		
		} catch (NamingException e) {
			request.setAttribute("systemError", e.getMessage());
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/pages/helloWorld.jsp");
		rd.forward(request, response);
		
	}

}
