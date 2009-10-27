package dk.ee.todo.chatbot.server.bean;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

@PersistenceCapable
public class TodoList {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key; 
	
	@Persistent
	private String title;
	
	@Persistent
	private Text description;
	
	@Persistent
	private TodoList parentList;

	@Persistent
	private List<TodoItem> todoItems = new ArrayList<TodoItem>();

	public TodoList() {
	}
	
	public TodoList(String title, Text description, TodoList parentList,
			List<TodoItem> todoItems) {
		this.title = title;
		this.description = description;
		this.parentList = parentList;
		this.todoItems = todoItems;
	}

	public Key getId() {
		return key;
	}

	public void setId(Key id) {
		this.key = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Text getDescription() {
		return description;
	}

	public void setDescription(Text description) {
		this.description = description;
	}

	public TodoList getParentList() {
		return parentList;
	}

	public void setParentList(TodoList parentList) {
		this.parentList = parentList;
	}

	public List<TodoItem> getTodoItems() {
		return todoItems;
	}

	public void setTodoItems(List<TodoItem> todoItems) {
		this.todoItems = todoItems;
	}
}
