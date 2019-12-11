package main.scene;


import java.util.Date;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.graph.TweetGraph;
import main.tweet.Tweet;
import main.tweet.TweetBase;

public class Interface {

	private Interface intrface;
	private static Scene scene;
	
	public Interface getInterface()
	{
		return intrface;
	}
	
	private Interface()
	{
		intrface = new Interface();
	}
	
	public static void launchInterface(Stage stage)
	{
		stage.setTitle("MesNews");
		scene = buildScene();
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}
	
	@SuppressWarnings("unchecked")
	public static Scene buildScene()
	{
		GridPane grid = new GridPane();

		MenuBar menuBar = new MenuBar();
		Menu actions = new Menu("Actions");
		menuBar.getMenus().addAll(actions);
		MenuItem itemQuitter = new MenuItem("Quitter");
		actions.getItems().addAll(itemQuitter);

		itemQuitter.setText("Quitter");
		itemQuitter.setOnAction((ActionEvent t) ->
		{
			Platform.exit();
		});
	    
		Separator separator = new Separator();

		grid.add(menuBar,0,0);
		grid.add(separator,0,2);
		
		TableView<Tweet> tableTweet = new TableView<Tweet>();
		TableColumn<Tweet,String> cId = new TableColumn<Tweet,String>("Id");
		TableColumn<Tweet,String> cUser = new TableColumn<Tweet,String>("User");
		TableColumn<Tweet,Date> cDate = new TableColumn<Tweet,Date>("Date");
		TableColumn<Tweet,String> cContent = new TableColumn<Tweet,String>("Content");
		TableColumn<Tweet,String> cRetweet = new TableColumn<Tweet,String>("Retweet");
		
		cId.setCellValueFactory(new PropertyValueFactory<>("id"));
		cUser.setCellValueFactory(new PropertyValueFactory<>("user"));
		cDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		cContent.setCellValueFactory(new PropertyValueFactory<>("content"));
		cRetweet.setCellValueFactory(new PropertyValueFactory<>("retweet"));
		
		tableTweet.getColumns().addAll(cId,cUser,cDate,cContent,cRetweet);
		tableTweet.setItems(getTweets());
				
		BorderPane root = new BorderPane();
		
		Button graph = new Button("DisplayGraph");
		graph.setText("Display graph");
		graph.setOnAction((ActionEvent e) ->
		{
			TweetGraph tweetGraph = new TweetGraph("climat");
			
			tweetGraph.displayGraph();
		});
		root.setTop(grid);
		root.setCenter(tableTweet);
		root.setBottom(graph);
		Scene scene = new Scene(root,1000,700);
		return scene;
	}
	
	private static ObservableList<Tweet> getTweets()
	{
		ObservableList<Tweet> list = FXCollections.observableArrayList(TweetBase.getInstance().getTweets());
		return list;
	}
}
