package dk.ee.todo.chatbot.server.db;

import dk.ee.todo.chatbot.server.bean.TodoUser;

/**
 * Abstract Repository
 * @author NEOERE
 *
 */
public interface TodoRepository {
	
	public abstract TodoUser findUserByUserName(String userName);
	
	public abstract void persistUser(TodoUser todoUser);

}
