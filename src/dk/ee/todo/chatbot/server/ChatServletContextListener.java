package dk.ee.todo.chatbot.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.wideplay.warp.servlet.Servlets;
import com.wideplay.warp.servlet.WarpServletContextListener;

public class ChatServletContextListener extends WarpServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new TodoModule(), Servlets.configure()
				.filters().servlets().serve("/_ah/xmpp/message/chat/").with(
						TodoChatReceiver.class).buildModule());
	}
}