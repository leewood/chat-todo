package dk.ee.todo.chatbot.server.service;

import dk.ee.todo.chatbot.server.bean.TodoList;
import dk.ee.todo.chatbot.server.bean.TodoUser;
import dk.ee.todo.chatbot.server.exception.TodoServiceException;

public interface TodoService {

	public abstract void createUser(TodoUser todoUser);

	public abstract void addNewTodoListToUser(String userName, TodoList todoList)
			throws TodoServiceException;
}
