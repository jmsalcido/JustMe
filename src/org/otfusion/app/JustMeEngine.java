package org.otfusion.app;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;

import android.content.Context;

public class JustMeEngine {
	
	public Context mContext;
	public static final HashMap<String, Integer> PORTS = new HashMap<String, Integer>();
	private String address;
	private int port;
	
	public JustMeEngine(Context context) {
		mContext = context;
		this.populateMap();
	}
	
	/*
	 * HashMap for the Address and Port, must add some other protocols.
	 */
	private void populateMap() {
		PORTS.put("http", 80);
		PORTS.put("https", 80);
		PORTS.put("ftp", 21);
		PORTS.put("smtp", 25);
		PORTS.put("otserv", 7171);
	}
	
	/*
	 * isOnline(String address): ugly way to tell you if that Internet Address
	 * is available or not by connecting a socket... I need a remote way to do
	 * this! :$
	 * 
	 * @param address ip address or dns
	 * 
	 * @return true or false
	 * 
	 */
	public boolean isOnline() {
		Socket socket;
		SocketAddress addr = new InetSocketAddress(address, port);
		try {
			socket = new Socket();
			socket.connect(addr, 5000); //TODO check this, must work with threads?
			return true;
		} catch (IOException ioe) {
			return false;
		}
	}
	
	/*
	 * getHost(String address): A very ugly (I dont know, I think this could be
	 * better but I cant imagine it lol) way to get the host in an URL.
	 * 
	 * @param address URL
	 * 
	 * @return dah Host!
	 * 
	 */
	private String getHost(String address) {
		String host = address;
		if (address.contains("://"))
			host = address.substring(address.indexOf("://") + 3);
		host = host.split("/")[0];
		if (host.contains(":"))
			host = host.substring(0, host.indexOf(":"));
		return host;
	}
	
	/*
	 * getPort(String address): Get the fucking port... Why all looks ugly for
	 * me? :/
	 * 
	 * @param address
	 * 
	 * @return port
	 * 
	 * TODO Cambiar a otra clase.
	 */
	private int getPort(String address) {
		// Default port will be 80, the httpd port.
		int port = 80;
		if (address.contains("://")) {
			try {
				String filter[] = address.split("://");
				port = PORTS.get(filter[0]);
				address = filter[1];
			} catch (IndexOutOfBoundsException ioobe) {
				port = 80;
			}
		}
		if (address.contains(":")) {
			address = address + "/";
			port = Integer.parseInt(address.substring(address.indexOf(":") + 1,
					address.indexOf("/")));
		}
		return port;
	}
	
	/*
	 * get the host and set as address.
	 */
	public void setAddress(String address) {
		this.address = getHost(address);
	}
	
	/*
	 * get the port and set as port
	 */
	public void setPort(String address) {
		this.port = getPort(address);
	}

}
