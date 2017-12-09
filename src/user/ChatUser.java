package user;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import panel.PanelManager;

public class ChatUser extends JPanel
{
	public static String UserID;
	
	private JTextField receiveMessage;
	private JTextField sendMessage;
	private JScrollPane scrollPane;
	private PanelManager panelManager;
	
	public ChatUser(PanelManager panelManager)
	{
		this.panelManager = panelManager;
		
		setSize(1100, 800);
		setLayout(new BorderLayout());
		
		receiveMessage = new JTextField();
		sendMessage = new JTextField();
		
		scrollPane = new JScrollPane(receiveMessage);		
		add(scrollPane, BorderLayout.CENTER);
		add(sendMessage, BorderLayout.SOUTH);
		
		try
		{
			Socket c_socket = new Socket("172.30.1.34", 8888);
			
			ReceiveThread rec_thread = new ReceiveThread();
			rec_thread.setSocket(c_socket);
			
			SendThread send_thread = new SendThread();
			send_thread.setSocket(c_socket);
			
			send_thread.start();
			rec_thread.start();
			
		} catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}