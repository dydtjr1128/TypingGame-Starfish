package user;

import java.io.*;
import java.net.Socket;

public class SendThread extends Thread
{
	private Socket m_Socket;
	
	@Override
	public void run()
	{
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			BufferedWriter out
				= new BufferedWriter(new OutputStreamWriter(m_Socket.getOutputStream()));
			
			String sendString;
			
			System.out.println("사용할 ID를 입력해주십시오 : ");
			ChatUser.UserID = in.readLine();
			
			out.write("IDhighkrs12345" + ChatUser.UserID + "\n");
			out.flush();
			
			while(true)
			{
				sendString = in.readLine();

				if(sendString.equals("exit"))
				{
					break;
				}
				
				out.write(sendString+"\n");
				out.flush();
			}
			
			out.close();
			in.close();
			m_Socket.close();
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
