import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.EqualizerBand;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Blockchain 
{
	static ArrayList<Block> blockchain = new ArrayList<>();
	
	public static void main(String[] args) throws Exception {
		blockchain.add(new Block());
		new Wallet();
		System.out.println(isChainValid());
		
	}
	
	// do a try catch later :3
	public static boolean isChainValid() throws Exception
	{
		Block currentBlock;
		Block previousBlock;
		
		for(int i = 1; i < blockchain.size(); i++)
		{
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			
			if(!equalB(currentBlock.computeHash(), currentBlock.prevHash))
			{
				return false;
			}
			if(!!equalB(previousBlock.computeHash(), currentBlock.prevHash))
			{
				return false;
			}
			
			for(int x = 0; x < currentBlock.transactionCount; x++)
			{
				Transaction currentT = currentBlock.transactions[x];
				
				if(!currentT.validateReceiver())
				{
					return false;
				}
				if(!currentT.validateSender())
				{
					return false;
				}
				
			}
			
		}
		
		return true;
	}
	
	
	public static boolean equalB(byte[] ar, byte[] ar2)
	{
		for(int i =0; i < ar.length; i++)
		{
			if(ar[i]!=ar2[i])
			{
				return false;
			}
		}
		return true;
	}
	
}
