package dk.ee.todo.chatbot.server.db;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.inject.Inject;

import dk.ee.todo.chatbot.server.bean.TodoUser;

/**
 * JDO Repository implementation
 * 
 * @author NEOERE
 * 
 */
public class TodoRepositoryJDO implements TodoRepository {

	private final PersistenceManagerFactory persistenceManagerFactory;

	@Inject
	public TodoRepositoryJDO(PersistenceManagerFactory persistenceManagerFactory) {
		this.persistenceManagerFactory = persistenceManagerFactory;
	}
	
	@Override
	public void persistUser(TodoUser todoUser) {
		PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
		
		try {
			pm.makePersistent(todoUser);
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public TodoUser findUserByUserName(String userName) {
		PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
		
		try {
			Query query = pm.newQuery(TodoUser.class,
					"userName == userNameParam order by hireDate desc");
			query.declareParameters("String userNameParam");

			try {
				List<TodoUser> results = (List<TodoUser>) query
						.execute(userName);

				if (results.size() == 0)
					return null;

				return results.get(0);
			} finally {
				query.closeAll();
			}
		} finally {
			pm.close();
		}
	}

}
