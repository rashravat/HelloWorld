package uk.co.tpplc.helloworld.client;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class HelloWorldMessageDrivenServlet extends HttpServlet {
	private static final long serialVersionUID = 2931173564147161924L;
	private static Logger logger = Logger.getLogger(HelloWorldMessageDrivenServlet.class);
	
	// Local connection factory
	private ConnectionFactory factory;

	// The local queue that we will send our requests to
	private Queue queue;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String helloText = (String) request.getParameter("helloText");
		Connection connection = null;
		try{

			getFactory();
			getQueue();

			connection = factory.createConnection();
			Session session = connection.createSession(true, 0);

			MessageProducer producer = session.createProducer(queue);

			TextMessage message = session.createTextMessage();
			message.setText(helloText);

			producer.send(message);
			logger.info("sent hello world message " + helloText + " to queue");
			request.setAttribute("helloWorldMessageDriven", "Your message ("+ helloText 
					+") has been sent to a queue and will be processed shortly");
		}catch (NamingException e){
			request.setAttribute("systemError", "report: failed to send report message: "+
					                            e.getMessage());
		}catch (JMSException e){
			request.setAttribute("systemError", "report: failed to send report message: "+
                    e.getMessage());
		}finally{
			if(connection != null){
				try{
					connection.close();
				} catch (JMSException e){
					e.printStackTrace();
				}
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/pages/helloWorld.jsp");
		rd.forward(request, response);
		
	}
	
	@SuppressWarnings("unused")
	private void getFactory() throws NamingException {
		if (factory==null) {
			logger.info("factory null, doing lookup");
			InitialContext ctx;
			try {
				ctx = new InitialContext();
				factory = (ConnectionFactory) ctx.lookup("java:comp/env/jms/JmsXA"); 
			} catch (NamingException e) {
				throw e;
			}
		}
		
	}
	@SuppressWarnings("unused")
	private void getQueue() throws NamingException {
		
		if (queue==null) {
			logger.info("queue null, doing lookup");
			InitialContext ctx;
			try {
				ctx = new InitialContext();
				queue = (Queue) ctx.lookup("java:comp/env/jms/helloWorldMessage"); 
			} catch (NamingException e) {
				throw e;
			}
		}
	}

}
