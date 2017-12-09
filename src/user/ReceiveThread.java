package user;

import java.io.*;
import java.net.Socket;

public class ReceiveThread extends Thread
{
	private Socket m_Socket;
	
	@Override
	public void run() 
	{
		try
		{
			BufferedReader in
				= new BufferedReader(new InputStreamReader(m_Socket.getInputStream()));
			
			String receiveString;
			String[] split;
			
			while(true)
			{
				receiveString = in.readLine();
				
				split = receiveString.split(">");
				if(split.length >= 2 && split[0].equals(ChatUser.UserID))
				{
					continue;
				}
				System.out.println(receiveString);
			}
			
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void setSocket(Socket _socket)
	{
		m_Socket = _socket;
	}

}