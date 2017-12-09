package server;

import java.io.*;
import java.net.Socket;

public class ClientManagerThread extends Thread
{
	private Socket m_socket;
	private String m_ID;
	
	@Override
	public void run() 
	{
		try 
		{
			BufferedReader in 
				= new BufferedReader(new InputStreamReader(m_socket.getInputStream()));			
			String text;
			
			while(true)
			{
				text = in.readLine();
				
				if(text == null)
				{
					System.out.println(m_ID + "��(��) �������ϴ�.");
					ChatServer.out.write(m_ID + "�� �������ϴ�\n");
					ChatServer.out.flush();
					break;
				}
				
				String[] split = text.split("highkrs12345");
				if(split.length == 2 && split[0].equals("ID"))
				{
					m_ID = split[1];
					System.out.println(m_ID + "��(��) �����Ͽ����ϴ�.");
					ChatServer.out.write(m_ID + "��(��) �����Ͽ����ϴ�.\n");
					ChatServer.out.flush();
					continue;
				}
				
				ChatServer.out.write(m_ID + "> "+ text + "\n");
				ChatServer.out.flush();
			}
			
			m_socket.close();
			
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void setSocket(Socket _socket)
	{
		m_socket = _socket;
	}
}
