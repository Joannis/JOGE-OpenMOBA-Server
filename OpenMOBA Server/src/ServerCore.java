import java.awt.Graphics;

import JOGE.core.JOGECore;
import JOGE.core.JOGEGame;
import JOGE.core.JOGEWindow;

public class ServerCore extends JOGEGame
{
	public static ServerCore			me;
	public static JOGECore				core;
	public static JOGEWindow			window;

	public static Server				server;
	
	public ServerCore()
	{
		if(me != null)
			return;
		else
			me = this;
		
		window = new JOGEWindow();
		core = new JOGECore(window);
		
		window.init(this, core, "My JOGE Server!", 800, 600, false, -1, -1);
		JOGEWindow.window.setVisible(false);
		
		core.start();
		
		server = new Server(1234, "ServerThread");
		
		server.setProtocol(new MyServerProtocol(server));
		server.init();
	}
	
	public static void main(String[] args)
	{
		new ServerCore();
	}
	
	public void onTick()
	{
		if(server != null)
			server.onTick();
	}
	
	public void onRender(Graphics g) {
		if(server != null)
			server.onRender(g);
	}
	
}