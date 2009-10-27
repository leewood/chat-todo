package dk.ee.todo.chatbot.server.service;

import com.google.inject.Inject;

import dk.ee.todo.chatbot.server.bean.TodoList;
import dk.ee.todo.chatbot.server.bean.TodoUser;
import dk.ee.todo.chatbot.server.db.TodoRepository;
import dk.ee.todo.chatbot.server.exception.TodoServiceException;

public class TodoServiceImpl implements TodoService {

	private final TodoRepository todoRepository;

	@Inject
	public TodoServiceImpl(TodoRepository todoRepository){
		this.todoRepository = todoRepository;
	}

	@Override
	public void addNewTodoListToUser(String userName, TodoList todoList)
			throws TodoServiceException {
		TodoUser todoUser = todoRepository.findUserByUserName(userName);
		if(userName == null)
			throw new TodoServiceException(String.format("User %s does not exists", userName));

		todoUser.getTodoLists().add(todoList);
	}

	@Override
	public void createUser(TodoUser todoUser) {
		todoRepository.persistUser(todoUser);
	}

}
