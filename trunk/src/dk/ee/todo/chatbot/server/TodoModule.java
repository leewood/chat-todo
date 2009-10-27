package dk.ee.todo.chatbot.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import dk.ee.todo.chatbot.server.db.TodoRepository;
import dk.ee.todo.chatbot.server.db.TodoRepositoryJDO;
import dk.ee.todo.chatbot.server.service.TodoService;
import dk.ee.todo.chatbot.server.service.TodoServiceImpl;

public class TodoModule extends AbstractModule {

	@Override
	protected void configure() {
		// Bind Todo classes and interfaces
		bind(TodoRepository.class).to(TodoRepositoryJDO.class);
		bind(TodoService.class).to(TodoServiceImpl.class).in(Scopes.SINGLETON);
		
		// Bind JDO PersistenceManagerFactory
		PersistenceManagerFactory pmfInstance = JDOHelper
				.getPersistenceManagerFactory("todo-persistence-factory");
		bind(PersistenceManagerFactory.class).toInstance(pmfInstance);
	}
}
