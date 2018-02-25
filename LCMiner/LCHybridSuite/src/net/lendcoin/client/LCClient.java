package net.lendcoin.client;


import java.nio.file.Files;
import java.nio.file.Paths;

<<<<<<< HEAD
import net.lendcoin.core.BlockChain;
=======
import com.jfoenix.controls.JFXButton;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
>>>>>>> 0e4e14312a773eba8277acbcacd8732fddb7ac9b
import net.lendcoin.core.Transaction;
import net.lendcoin.miner.LCMiner;

<<<<<<< HEAD
public class LCClient {
	public static Wallet MAIN_WALLET = new Wallet();
=======
public class LCClient extends Application{
	
	public static final Wallet MAIN_WALLET = new Wallet();
>>>>>>> 0e4e14312a773eba8277acbcacd8732fddb7ac9b
	
	public static final void lccMain(final String[] args)throws Exception
	{
		launch(args);
		MAIN_WALLET.PRIV_KEY = Files.readAllBytes(Paths.get("wallet_key.dat"));
		MAIN_WALLET.PUB_KEY = Files.readAllBytes(Paths.get("wallet_address.dat"));
	}
	
<<<<<<< HEAD
	public static void send(String destination, long amount, int delay, int duration, long fees)
=======
	@Override
	public void start(Stage stage) throws Exception {
		GridPane main = new GridPane();
        main.setVgap(20);
        main.setHgap(20);

        Image image = new Image("favicon.png");
        //Image image1 = new Image("bg1.jpg");
        
       // ImageView iv1 = new ImageView();
        ImageView iv2 = new ImageView();
        iv2.setImage(image);
        iv2.setFitWidth(100);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);

      //  main.add(iv1, 0 , 0);
        main.add(iv2, 0 , 0);

        JFXButton button = new JFXButton("New User?");
        button.getStyleClass().add("button-raised");
        main.add(button, 15, 0);
        
        JFXButton button2 = new JFXButton("Returning User?");
        button2.getStyleClass().add("button-raised");
        main.add(button2, 15, 1);
      
        final Scene scene = new Scene(main, 600, 300);
        stage.setTitle("Lend Coin");
        stage.setScene(scene);
        stage.show();
		
	}
	
	public void send(String destination, long amount, int delay, int duration, long fees)
>>>>>>> 0e4e14312a773eba8277acbcacd8732fddb7ac9b
	{
		
	}
	
	public static void receive(Transaction inbound)
	{
		
	}
	
	public static long queryBalance()
	{
<<<<<<< HEAD
		return GlobalTotals.queryBalance(MAIN_WALLET.PUB_KEY);
=======
		return 0;
>>>>>>> 0e4e14312a773eba8277acbcacd8732fddb7ac9b
	}
	
	public static long queryDebt()
	{
<<<<<<< HEAD
		BlockChain bc = BlockChain.MAIN_CHAIN.findLongest();
		return GlobalTotals.queryCredit(MAIN_WALLET.PUB_KEY, bc == null || bc.containedBlock == null ? 0 : bc.containedBlock.blockNumber);
=======
		return 0;
>>>>>>> 0e4e14312a773eba8277acbcacd8732fddb7ac9b
	}
	
	static Thread miner;
	
	public static void startMining()
	{
		LCMiner.mining = true;
		miner = new Thread() {
			public void run()
			{
				try {
					new LCMiner().lcmMain(new String[0]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		miner.start();
	}
	
	public static void stopMining()
	{
		LCMiner.mining = false;
		miner = null;
	}

	
}
