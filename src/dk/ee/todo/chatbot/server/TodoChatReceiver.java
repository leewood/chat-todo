package dk.ee.todo.chatbot.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;
import com.google.inject.Inject;

import dk.ee.todo.chatbot.server.bean.TodoList;
import dk.ee.todo.chatbot.server.bean.TodoUser;
import dk.ee.todo.chatbot.server.service.TodoService;


public class TodoChatReceiver extends HttpServlet {
	private static final long serialVersionUID = 1756587344058920800L;
	private static final Logger LOG = Logger.getLogger(TodoChatReceiver.class
			.getName());
	
	private final TodoService todoService;
	
	@Inject
	public TodoChatReceiver(TodoService todoService) {
		this.todoService = todoService;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			XMPPService xmpp = XMPPServiceFactory.getXMPPService();

			// Parse message 
			Message message = xmpp.parseMessage(req);
			JID fromJid = message.getFromJid();
			String body = message.getBody();
			LOG.finest("Received a message from " + fromJid + " and body = "
					+ body);

			String replyBody = handleMessage(body);
			
			// Send reply
			Message replyMessage = new MessageBuilder().withRecipientJids(
					fromJid).withBody(replyBody).build();
			
			SendResponse status = xmpp.sendMessage(replyMessage);
			if (status.getStatusMap().get(fromJid) == SendResponse.Status.SUCCESS) {
				LOG.finest("Message has been sent successfully");
			} else {
				LOG.finest("Message could not be sent");
			}
		} catch (Exception e) {
			LOG.log(Level.SEVERE, e.getMessage());
		}
	}
	
	private String handleMessage(String body) {
		if (body.startsWith("/help"))
			return handleHelpCommand(body);
		else if (body.startsWith("/createuser"))
			return handleCreateUserCommand(body);
		return "Unknown command";
	}

	private String handleCreateUserCommand(String body) {
		String[] parts = body.split("\\s+");
		if (parts.length != 3)
			return "Invalid command";

		// Parse body
		String userName = parts[1];
		String password = parts[2];

		LOG.finest("Creating new user " + userName + "/" + password);

		// Create and store new user
		TodoUser newUser = new TodoUser(userName, password,
				new ArrayList<TodoList>());

		todoService.createUser(newUser);

		return "User " + userName + " created!";
	}

	private static String handleHelpCommand(String body) {
		LOG.finest("Help command invoked: " + body);
		return new StringBuilder().append("Supported commands:\n").append(
				"  /createuser <username> <password>").toString();
	}
}
