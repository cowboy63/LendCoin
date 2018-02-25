package net.lendcoin.core.netcode;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

import net.lendcoin.core.LCUtils;

public final class PeerList {
	public static final HashSet<Inet4Address> PEERS = new HashSet<>();
	protected static final byte[] EXPECTED_RESPONSE = new byte[] {(byte)'L', (byte)'E', (byte)'N', (byte)'D'};
	public static final void loadPeers() throws Exception
	{
		Scanner peerFile = new Scanner(new File("peers.dat"));
		while(peerFile.hasNextLine())
		{
			String name = peerFile.nextLine();
			Inet4Address ad = testPeer(name);
			if(ad != null)
			{
				PEERS.add(ad);
			}
		}
	}
	
	public static final Inet4Address testPeer(String name)
	{
		try {
			Inet4Address peerIP = null;
			if(name.matches("\\d+\\.\\d+\\.\\d+\\.\\d+"))
			{
				int[] ipa = Arrays.stream(name.split("\\.")).mapToInt(x -> (byte)Integer.parseInt(x)).toArray();
				peerIP = (Inet4Address)Inet4Address.getByAddress(new byte[] {(byte)ipa[0], (byte)ipa[1], (byte)ipa[2], (byte)ipa[3]});
			}
			else
				peerIP = (Inet4Address)Inet4Address.getByName(name);
			if(peerIP.isReachable(1000))
			{
				LCUtils.logEvent("INFO - Successfully located peer at: " + name);
				LCUtils.logEvent("INFO - Querying peer for connectivity...");
				try {
					SocketAddress sa = new InetSocketAddress(peerIP, NetConfig.CLIENT_PORT);
					Socket sock = new Socket();
					sock.connect(sa);
					LCUtils.logEvent("INFO - Peer connectivity verified...");
					LCUtils.logEvent("INFO - Testing peer for Lendcoin client status...");
					try {
						DataOutputStream outputStream = new DataOutputStream(sock.getOutputStream());
						DataInputStream inputStream = new DataInputStream(sock.getInputStream());
						outputStream.writeByte(0x43);
						outputStream.writeByte(0x4f);
						outputStream.writeByte(0x49);
						outputStream.writeByte(0x4e);
						outputStream.flush();
						outputStream.close();
						byte[] response = new byte[] {inputStream.readByte(), inputStream.readByte(), inputStream.readByte(), inputStream.readByte()};
						if(Arrays.equals(response, EXPECTED_RESPONSE))
						{
							LCUtils.logEvent("INFO - Detected Lendcoin client on peer...");
							return peerIP;
						}
						else LCUtils.logEvent("WARNING - Did detect functional Lendcoin client on peer at: " + name);
					} catch (Exception e) {
						LCUtils.logEvent("WARNING - Did detect functional Lendcoin client on peer at: " + name);
					}
				} catch (Exception e) {
					LCUtils.logEvent("WARNING - Failed to connect to peer at: " + name);
				}
			} else {
				LCUtils.logEvent("WARNING - Could not reach peer at: " + name);
			}
		} catch (Exception e) {
			LCUtils.logEvent("WARNING - Could not attempt connection to peer at: " + name);
		}
		return null;
	}
	
	public static final boolean addPeer(String name)
	{
		Inet4Address peer = testPeer(name);
		if(peer != null)
		{
			PEERS.add(peer);
			return true;
		} else return false;
	}
	
	public static final void savePeers() throws Exception
	{
		PrintWriter peerFile = new PrintWriter("peers.dat");
		for(Inet4Address peer : PEERS)
		{
			peerFile.println(peer.getHostName());
		}
		peerFile.close();
	}
	
	public static final void queryAdditionalPeers() throws Exception
	{
		for(Inet4Address peer : (HashSet<Inet4Address>)PEERS.clone())
		{
			
		}
	}
}
