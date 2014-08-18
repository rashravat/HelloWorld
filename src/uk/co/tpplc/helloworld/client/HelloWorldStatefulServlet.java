package uk.co.tpplc.helloworld.client;

import java.io.IOException;

import javax.ejb.EJBNoSuchObjectException;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.co.tpplc.helloworld.sessionbeans.exception.HelloWorldStatefulBeanNotReadyException;
import uk.co.tpplc.helloworld.sessionbeans.stateful.HelloWorldStatefulService;

public class HelloWorldStatefulServlet extends HttpServlet {
	private static final long serialVersionUID = 2083913666826069113L;

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
String helloText = (String) request.getParameter("helloText");
HelloWorldStatefulService helloWorldService = null;

		try{
			InitialContext ctx = new InitialContext();
			
			helloWorldService = (HelloWorldStatefulService) ctx.lookup("HelloWorld/HelloWorldStatefulBean/local");
			
			helloWorldService.initialise(helloText);
			String helloWorldMessage = helloWorldService.helloWorld();
			
			request.setAttribute("helloWorldStatefulMessage", helloWorldMessage);
		} catch (HelloWorldStatefulBeanNotReadyException e){
			request.setAttribute("systemError", e.getMessage());
		} catch (EJBNoSuchObjectException e){
			request.setAttribute("systemError", e.getMessage());
		} catch (Exception e){
			request.setAttribute("systemError", e.getClass().getSimpleName() +":"+ e.getMessage());
		} finally{ 
			if(helloWorldService != null)
				helloWorldService.finished();
			
			RequestDispatcher rd = request.getRequestDispatcher("/pages/helloWorld.jsp");
			rd.forward(request, response);
		}
		
		
	}
}
