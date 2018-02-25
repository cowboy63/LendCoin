package net.lendcoin.core.netcode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

import net.lendcoin.core.LCUtils;

public class PeerConnections {
	public static final List<DataOutputStream> outboundPeers = new ArrayList<>();
	public static final List<DataInputStream> inboundPeers = new ArrayList<>();
	
	public static final void reloadConnections()
	{
		for(DataOutputStream out : outboundPeers)
		{
			try {
				
			} catch (Exception e) {
				LCUtils.logEvent("WARNING - Could not safely disconnect from peer");
			}
		}
	}
}
