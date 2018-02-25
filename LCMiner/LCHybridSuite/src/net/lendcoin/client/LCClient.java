package net.lendcoin.client;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.lendcoin.core.BlockChain;
import net.lendcoin.core.LCUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import net.lendcoin.core.Transaction;
import net.lendcoin.miner.LCMiner;

public class LCClient extends Application{
	
	public static final Wallet MAIN_WALLET = new Wallet();
	
	public static final void lccMain(final String[] args)throws Exception
	{
		MAIN_WALLET.PRIV_KEY = Files.readAllBytes(Paths.get("wallet_key.dat"));
		MAIN_WALLET.PUB_KEY = Files.readAllBytes(Paths.get("wallet_address.dat"));
		launch(args);
		
	}
	@Override
	public void start(Stage stage) throws Exception {
		GridPane main = new GridPane();
        main.setVgap(20);
        main.setHgap(20);

        Image image = new Image("favicon.png");
        
       BackgroundImage myBI= new BackgroundImage(new Image("X.jpg",600,300,false,true),
            BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
               BackgroundSize.DEFAULT);
      // then you set to your node
        main.setBackground(new Background(myBI));
        
        ImageView iv2 = new ImageView();
        
     
        iv2.setImage(image);
        iv2.setFitWidth(100);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);

        main.add(iv2, 0 , 0);

        JFXButton button = new JFXButton("Create new Keys");
        button.getStyleClass().add("button-raised");
        main.add(button, 15, 1);
        
        
        
        JFXButton button2 = new JFXButton("Lend Coins");
        button2.getStyleClass().add("button-raised");
        main.add(button2, 15, 2);
        
        JFXButton button3 = new JFXButton("Mine");
        button3.getStyleClass().add("button-raised");
        main.add(button3, 15, 3);
        
        JFXButton button4 = new JFXButton("Stop mining");
        button4.getStyleClass().add("button-raised");
        main.add(button4, 15, 4);
        
        button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					Wallet a = Wallet.generateWallet();
					PrintWriter pw2 = new PrintWriter(new File("wallet_key.dat"));
					pw2.println(LCUtils.bytes2Hex(a.PUB_KEY));
					pw2.println(LCUtils.bytes2Hex(a.PRIV_KEY));
					
					String toastMsg = "New Keys Created";
					int toastMsgTime = 3500; //3.5 seconds
					int fadeInTime = 500; //0.5 seconds
					int fadeOutTime= 500; //0.5 seconds
					Toast.makeText(stage, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
					
					pw2.close();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		});
        
		
        button2.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				MainS(stage);
				return;
			}
		});
        
        button3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LCClient.startMining();
				String toastMsg = "Started Mining";
				int toastMsgTime = 3500; //3.5 seconds
				int fadeInTime = 500; //0.5 seconds
				int fadeOutTime= 500; //0.5 seconds
				Toast.makeText(stage, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
				return;
			}
		});
        
        button4.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LCClient.stopMining();
				String toastMsg = "Stopped Minning";
				int toastMsgTime = 3500; //3.5 seconds
				int fadeInTime = 500; //0.5 seconds
				int fadeOutTime= 500; //0.5 seconds
				Toast.makeText(stage, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
				return;
			}
		});
        
       
        final Scene scene = new Scene(main, 600, 300);
        stage.setTitle("Lend Coin");
        stage.setScene(scene);
        stage.show();
        
        
	}
	
	public void generateKeys()
	{
		
	}
	
	public void MainS(Stage stage)
	{
		GridPane main = new GridPane();
        main.setVgap(10);
        main.setHgap(10);

        
        BackgroundImage myBI= new BackgroundImage(new Image("X.jpg",600,300,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                   BackgroundSize.DEFAULT);
          // then you set to your node
            main.setBackground(new Background(myBI));
            
        JFXTextArea jfxTextArea = new JFXTextArea();
        jfxTextArea.setPromptText("Sender public Key");
        jfxTextArea.setLabelFloat(true);
        jfxTextArea.setMaxSize(100, 10);
        main.add(jfxTextArea, 35, 3);
        
        
        JFXTextArea jfxTextArea2 = new JFXTextArea();
        jfxTextArea2.setPromptText("Amount");
        jfxTextArea2.setLabelFloat(true);
        jfxTextArea2.setMaxSize(100, 10);
        main.add(jfxTextArea2, 35, 7);
        
        
        JFXTextArea jfxTextArea3 = new JFXTextArea();
        jfxTextArea3.setPromptText("Payment Rate");
        jfxTextArea3.setLabelFloat(true);
        jfxTextArea3.setMaxSize(100, 10);
        main.add(jfxTextArea3, 40, 3);
        
        JFXTextArea jfxTextArea4 = new JFXTextArea();
        jfxTextArea4.setPromptText("Payment Delay");
        jfxTextArea4.setLabelFloat(true);
        jfxTextArea4.setMaxSize(100, 10);
        main.add(jfxTextArea4, 40, 7);
        
        JFXTextArea jfxTextArea5 = new JFXTextArea();
        jfxTextArea5.setPromptText("Payment Fee");
        jfxTextArea5.setLabelFloat(true);
        jfxTextArea5.setMaxSize(100, 10);
        main.add(jfxTextArea5, 35, 11);
        
        JFXButton button2 = new JFXButton("Back");
        button2.getStyleClass().add("button-raised");
        main.add(button2, 35, 15);
        
        JFXButton button = new JFXButton("Submit");
        button.getStyleClass().add("button-raised");
        main.add(button, 40, 15);
        
        button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				send(jfxTextArea.getText(), Long.parseLong(jfxTextArea2.getText()), Integer.parseInt(jfxTextArea4.getText()), Integer.parseInt(jfxTextArea3.getText()), Long.parseLong(jfxTextArea5.getText()));
				return;
			}
		});
        
        button2.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					start(stage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		});
        
		final Scene scene = new Scene(main, 600, 300);
		stage.setScene(scene);
	}
	
	public void send(String destination, long amount, int delay, int duration, long fees)
	{
		// no net code
	}
	
	public static void receive(Transaction inbound)
	{
		// no net code
	}
	
	public static long queryBalance()
	{
		return GlobalTotals.queryBalance(MAIN_WALLET.PUB_KEY);
	}
	
	public static long queryDebt()
	{
		BlockChain bc = BlockChain.MAIN_CHAIN.findLongest();
		return GlobalTotals.queryCredit(MAIN_WALLET.PUB_KEY, bc == null || bc.containedBlock == null ? 0 : bc.containedBlock.blockNumber);

	}
	
	static Thread miner;
	
	public static void startMining()
	{
		if(miner != null) return;
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
