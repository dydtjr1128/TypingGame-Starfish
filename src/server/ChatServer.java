package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer 
{
	public static BufferedWriter out = null;	
	private ServerSocket s_socket = null;
	private Socket c_socket = null;
	private ClientManagerThread c_thread;
	
	public void connectServer()
	{
		try
		{
			s_socket = new ServerSocket(8888);

			while(true)
			{
				c_socket = s_socket.accept();
				c_thread = new ClientManagerThread();
				c_thread.setSocket(c_socket);
				
				out = new BufferedWriter(new OutputStreamWriter(c_socket.getOutputStream()));
				c_thread.start();
			}
		
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}