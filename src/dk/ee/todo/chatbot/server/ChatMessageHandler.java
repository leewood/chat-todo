package dk.ee.todo.chatbot.server;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.inject.Inject;

import dk.ee.todo.chatbot.server.bean.TodoList;
import dk.ee.todo.chatbot.server.bean.TodoUser;
import dk.ee.todo.chatbot.server.service.TodoService;


public class ChatMessageHandler {
	private static final Logger LOG = Logger.getLogger(ChatMessageHandler.class
			.getName());

	private final TodoService todoService;

	@Inject
	public ChatMessageHandler(TodoService todoService) {
		this.todoService = todoService;
	}
	
	public String handleMessage(String body) {
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
