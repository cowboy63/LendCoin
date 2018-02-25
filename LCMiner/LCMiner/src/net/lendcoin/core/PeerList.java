package net.lendcoin.core;
import java.io.File;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.util.HashSet;
import java.util.Scanner;

public final class PeerList {
	public static final HashSet<Inet4Address> PEERS = new HashSet<>();
	public static final void loadPeers() throws Exception
	{
		Scanner peerFile = new Scanner(new File("peers.dat"));
		while(peerFile.hasNextLine())
		{
			Inet4Address peerIP = (Inet4Address)Inet4Address.getByName(peerFile.nextLine());
			try {
				if(peerIP.isReachable(1000))
				{
					PEERS.add(peerIP);
				} else {
					LCUtils.logEvent("WARNING - Could not reach peer at: " + peerIP.getHostAddress());
				}
			} catch (Exception e) {
				LCUtils.logEvent("WARNING - Could not attempt connection to peer at: " + peerIP.getHostName());
			}
		}
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
}
