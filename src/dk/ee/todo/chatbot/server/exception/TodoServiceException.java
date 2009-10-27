package dk.ee.todo.chatbot.server.exception;

public class TodoServiceException extends Exception {

	private static final long serialVersionUID = 2712376909402534587L;

	public TodoServiceException(String message) {
		super(message);
	}
}
