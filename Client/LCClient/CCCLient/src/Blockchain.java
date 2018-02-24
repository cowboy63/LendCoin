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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.*;

public class Blockchain extends Application
{
	static ArrayList<Block> blockchain = new ArrayList<>();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static boolean isChainValid()
	{
		Block currentBlock;
		Block previousBlock;
		
		for(int i = 1; i < blockchain.size(); i++)
		{
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			
			if(!currentBlock.getHash().equals(currentBlock.getPreviosHash()))
			{
				return false;
			}
			if(!previousBlock.getHash().equals(currentBlock.getPreviosHash()))
			{
				return false;
			}
		}
		
		return true;
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setTitle("Lend Coin");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        
	}
	
	
	
}
