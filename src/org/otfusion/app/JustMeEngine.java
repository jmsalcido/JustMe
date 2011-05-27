/*
 * JustMeEngine.java:
 * This class is part of JustMe Android Application.
 * 
 * The class will work with the connection part of the app.
 */

package org.otfusion.app;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;

public class JustMeEngine {
	
	private HashMap<String, Integer> ports = new HashMap<String, Integer>();
	private String address;
	private int port;
	
	public JustMeEngine() {
		this.populateMap();
	}
	
	/**
	 * populateMap() method:
	 * this method will populate herpderp the map herpderp
	 */
	private void populateMap() {
		// TODO add a very good list of ports.
		ports.put("http", 80);
		ports.put("https", 80);
		ports.put("ftp", 21);
		ports.put("smtp", 25);
		ports.put("otserv", 7171);
		//PORTS.put();
	}
	
	/**
	 * isOnline(String address) method:
	 * this method will check if the server is online or offline
	 * 
	 * @param address ip address or dns
	 * @return true or false
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
	
	/**
	 * getHost(String address) method:
	 * this method will get only the host from an address supplied.
	 * 
	 * Example:
	 * http://otfusion.org/jose152 -> otfusion.org
	 * 
	 * @param address URL
	 * @return dah Host!
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
	
	/**
	 * getPort(String address) method:
	 * this method will get the port from an address.
	 * @param address
	 * @return port
	 */
	private int getPort(String address) {
		// Default port will be 80, the httpd port.
		int port = 80;
		if (address.contains("://")) {
			try {
				String filter[] = address.split("://");
				port = ports.get(filter[0]);
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
	
	/**
	 * setAddress(String address) method:
	 * this method will set the address of the object to the address supplied
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = getHost(address);
	}
	
	/**
	 * setPort(String address) method:
	 * this method will set the port of the object to the address port supplied
	 * @param address
	 */
	public void setPort(String address) {
		this.port = getPort(address);
	}

}
