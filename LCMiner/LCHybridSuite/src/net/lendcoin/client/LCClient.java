package net.lendcoin.client;


import java.nio.file.Files;
import java.nio.file.Paths;

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
import net.lendcoin.core.Transaction;

public class LCClient extends Application{
	
	public static final Wallet MAIN_WALLET = new Wallet();
	
	public static final void lccMain(final String[] args)throws Exception
	{
		launch(args);
		MAIN_WALLET.PRIV_KEY = Files.readAllBytes(Paths.get("wallet_key.dat"));
		MAIN_WALLET.PUB_KEY = Files.readAllBytes(Paths.get("wallet_address.dat"));
	}
	
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
	{
		
	}
	
	public void receive(Transaction inbound)
	{
		
	}
	
	public long queryBalance()
	{
		return 0;
	}
	
	public long queryDebt()
	{
		return 0;
	}
	
	public void startMining()
	{
		
	}
	
	public void stopMining()
	{
		
	}

	
}
