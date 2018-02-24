import java.util.ArrayList;
import java.util.Date;



public class Block 
{
	private String hash;
	private String previosHash;
	private ArrayList<Entry> data;
	private long timeStamp;
	
	public Block(ArrayList<Entry> e, String previousHash)
	{
		this.data = e;
		this.previosHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPreviosHash() {
		return previosHash;
	}

	public void setPreviosHash(String previosHash) {
		this.previosHash = previosHash;
	}

	public ArrayList<Entry> getData() {
		return data;
	}

	public void setData(ArrayList<Entry> data) {
		this.data = data;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}
