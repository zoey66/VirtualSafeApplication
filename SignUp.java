package application;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 * @author zhangxinyu
 * Written 03/14/2019
 * Last updated  03/16/2019
 * 
 * This class is creating the UI for the sign up and send email to others
 * 
 */
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class signup extends Application {
	String url = "jdbc:sqlserver://umd-fmis.d.umn.edu;databaseName=vwpteam59";
	String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	String uid = "vwp";
	String pwd = "vwpumd";
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();

	// create button, label, textfield, and stirng

	final Button button = new Button("Sign Up");
	final Button button1 = new Button("Sign in");
	Button button2 = new Button("Add note");
	Button button3 = new Button("upload images");
	Button button4 = new Button("delete note");
	Button button5 = new Button("Delete your note");
	Button button6 = new Button("go back");
	Button button7 = new Button("create notes");
	String time = dtf.format(now).toString();
	TextField tim = new TextField();
	TextField noteid = new TextField();

	final Label notification = new Label();
	Label signin = new Label();
	TextField checkpass = new TextField();
	TextField photoname = new TextField();
	Desktop desktop = Desktop.getDesktop();

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Sign Up Form");
		Scene scene = new Scene(new Group(), 1000, 400);

		// put prompt text for text field

		final TextField fname = new TextField();
		final TextField lname = new TextField();
		fname.setPromptText("First name");
		lname.setPromptText("Last Name");
		final TextField Username = new TextField();
		Username.setPromptText("Username");
		// final TextField eaddress = new TextField();
		// eaddress.setPromptText("Userid");
		final PasswordField password = new PasswordField();
		password.setPromptText("Password");
		final PasswordField repassword = new PasswordField();
		repassword.setPromptText("Retype password");

		// set on action for button sign up
		button.setStyle("-fx-background-color:gray;" + "-fx-text-fill:white");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (fname.getText().isEmpty()) {
					notification.setText("\n" + "\n" + "You are missing your firstname");
				} else if (lname.getText().isEmpty()) {
					notification.setText("\n" + "\n" + "You are missing your lastname");
				} else if (Username.getText().isEmpty()) {
					notification.setText("\n" + "\n" + "Your are missing your username");
				} else if (password.getText().isEmpty()) {
					notification.setText("\n" + "\n" + "You are missing your password");
				} else if (repassword.getText().isEmpty()) {
					notification.setText("\n" + "\n" + "You are missing your confirmed password");
				} else if (!password.getText().equals(repassword.getText())) {
					notification.setText("\n" + "\n" + "Your confirmed password is wrong");
				} else {
					// int userid = Integer.parseInt(eaddress.getText());

					signup(Username.getText(), fname.getText(), lname.getText(), password.getText());
					notification.setText("\n" + "\n" + "signed up");
				}
			}
		});

		PasswordField password1 = new PasswordField();
		TextField username1 = new TextField();
		Image image = new Image("logo.png");
		ImageView imageview = new ImageView();
		imageview.setImage(image);

		try {
			// Image files are in the raw folder
			image = new Image(new FileInputStream("raw/logo.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		imageview.setX(50);
		imageview.setY(25);
		// setting the fit height and width of the image view
		imageview.setFitHeight(455);
		imageview.setFitWidth(500);
		// Setting the preserve ratio of the image view
		imageview.setPreserveRatio(true);

		// add notes
		final TextArea msg = new TextArea("");

		button2.setOnAction(e -> {

			VBox vbox = new VBox(20);
			HBox hbox1 = new HBox(20);
			noteid.setText("enter your note name");
			tim.setText(time);
			hbox1.getChildren().addAll(button7, button6);
			vbox.getChildren().addAll(noteid, msg, hbox1, tim);
			Scene scene2 = new Scene(new Group(), 700, 400);
			Group root = (Group) scene2.getRoot();
			root.getChildren().addAll(vbox);
			primaryStage.setTitle("Your Note");
			primaryStage.setScene(scene2);
			primaryStage.show();
		});

		button7.setOnAction(e -> {
			notes(username1.getText(), msg.getText());

		});

		TextField username4 = new TextField();
		username4.setPromptText("username:");
		PasswordField password4 = new PasswordField();
		password4.setPromptText("password:");
		// delete
		button4.setOnAction(e -> {

			GridPane grid1 = new GridPane();
			grid1.setVgap(10);
			grid1.setHgap(20);
			grid1.setPadding(new Insets(5, 5, 5, 5));
			grid1.add(username4, 1, 3);
			grid1.add(password4, 1, 4);
			grid1.add(button5, 1, 5);
			grid1.setStyle("-fx-background-color: #008051");
			HBox hbox = new HBox();
			hbox.getChildren().addAll(grid1, imageview);
			Scene scene4 = new Scene(new Group(), 700, 400);
			Group root = (Group) scene4.getRoot();
			root.getChildren().addAll(hbox);
			primaryStage.setScene(scene4);
			primaryStage.show();

		});
		// delete after checking
		button5.setOnAction(e -> {
			delete(username4.getText(), password4.getText());
		});

		Hyperlink link = new Hyperlink();
		link.setText("Sign in instead");

		Hyperlink link2 = new Hyperlink();
		link2.setText("Sign up instead");

		HBox last = new HBox(200);
		last.getChildren().addAll(link, button);
		HBox last1 = new HBox(200);
		last1.getChildren().addAll(link2, button1);

		// create gridpane
		GridPane grid1 = new GridPane();
		grid1.setVgap(10);
		grid1.setHgap(20);
		grid1.setPadding(new Insets(5, 5, 5, 5));
		Label signup = new Label("Come Join Us");
		signup.setStyle("-fx-font-size:24px;" + "-fx-text-fill:black;" + "-fx-font-weight:bold");
		Label text = new Label("Safe, Secure and Free");
		text.setStyle("-fx-font-size:18px;" + "-fx-text-fill:black;");
		grid1.add(signup, 0, 3);
		grid1.add(text, 0, 5);
		grid1.add(fname, 0, 6);
		grid1.add(lname, 1, 6);
		grid1.add(Username, 0, 7);
		// grid1.add(eaddress, 0, 8, 2, 1);
		grid1.add(password, 0, 9, 2, 1);
		grid1.add(repassword, 0, 10, 2, 1);
		grid1.add(last, 0, 13);
		grid1.add(notification, 0, 14);

		HBox pane = new HBox();
		pane.getChildren().addAll(grid1, imageview);
		pane.setStyle("-fx-background-color: #008051");

		GridPane grid2 = new GridPane();
		grid2.setVgap(10);
		grid2.setHgap(20);
		grid2.setPadding(new Insets(5, 5, 5, 5));
		Label login = new Label("Virtual Safe");
		login.setStyle("-fx-font-size:24px;" + "-fx-text-fill:black;" + "-fx-font-weight:bold");
		grid2.add(login, 0, 3);
		grid2.add(username1, 0, 4);
		grid2.add(password1, 0, 5);
		grid2.add(last1, 0, 7);
		grid2.add(signin, 0, 9);

		link.setOnAction(evt -> {
			HBox hbox = new HBox();
			hbox.getChildren().addAll(grid2, imageview);
			hbox.setStyle("-fx-background-color: #008051");

			Scene scene1 = new Scene(new Group(), 1000, 400);
			Group root = (Group) scene1.getRoot();
			root.getChildren().addAll(hbox);

			primaryStage.setScene(scene1);
			primaryStage.show();

		});
		// sign in

		button1.setStyle("-fx-background-color:gray;" + "-fx-text-fill:white");

		// go back
		button6.setOnAction(e -> {
			GridPane hboxx = new GridPane();
			hboxx.setVgap(10);
			hboxx.setHgap(20);
			hboxx.setPadding(new Insets(5, 5, 5, 5));
			// HBox hboxx = new HBox();
			// hboxx.getChildren().addAll(button2, button3, button4);
			hboxx.add(button2, 1, 8);
			hboxx.add(button3, 3, 8);
			hboxx.add(button4, 5, 8);
			hboxx.setStyle("-fx-background-color: #008051");
			VBox vboxx = new VBox();
			vboxx.getChildren().addAll(hboxx, imageview);
			Scene scenex = new Scene(new Group(), 450, 650);
			Group root = (Group) scenex.getRoot();
			root.getChildren().addAll(vboxx);
			primaryStage.setTitle("Your file list");
			primaryStage.setScene(scenex);
			primaryStage.show();
		});

		link2.setOnAction(evt -> {
			HBox hbox = new HBox();
			hbox.getChildren().addAll(grid1, imageview);
			hbox.setStyle("-fx-background-color: #008051");

			Scene scene1 = new Scene(new Group(), 900, 400);
			Group root = (Group) scene1.getRoot();
			root.getChildren().addAll(hbox);

			primaryStage.setScene(scene1);
			primaryStage.show();

		});
		// images
		button3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				// final FileChooser fileChooser = new FileChooser();
				// File file = fileChooser.showOpenDialog(primaryStage);
				// if (file != null) {
				// openFile(file);
				// }
				FileChooser fc = new FileChooser();
				FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
				FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
				fc.getExtensionFilters().addAll(ext1, ext2);
				File file = fc.showOpenDialog(primaryStage);
				VBox vbox = new VBox();
				primaryStage.setScene(scene);
				primaryStage.setTitle("Insert Image Into Database");
				primaryStage.show();
			}

		});
		button1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				check(username1.getText(), password1.getText());
				if (!checkpass.getText().equals(password1.getText())) {
					signin.setText("Wrong password or username");
					HBox hbox = new HBox();
					hbox.getChildren().addAll(grid2, imageview);
					hbox.setStyle("-fx-background-color: #008051");

					Scene scene1 = new Scene(new Group(), 1000, 400);
					Group root = (Group) scene1.getRoot();
					root.getChildren().addAll(hbox);

					primaryStage.setScene(scene1);
					primaryStage.show();

				} else {
					GridPane hboxx = new GridPane();
					hboxx.setVgap(10);
					hboxx.setHgap(20);
					hboxx.setPadding(new Insets(5, 5, 5, 5));
					// HBox hboxx = new HBox();
					// hboxx.getChildren().addAll(button2, button3, button4);
					hboxx.add(button2, 1, 8);
					hboxx.add(button3, 3, 8);
					hboxx.add(button4, 5, 8);
					hboxx.setStyle("-fx-background-color: #008051");
					VBox vboxx = new VBox();
					vboxx.getChildren().addAll(hboxx, imageview);
					Scene scenex = new Scene(new Group(), 450, 650);
					Group root = (Group) scenex.getRoot();
					root.getChildren().addAll(vboxx);
					primaryStage.setTitle("Your file list");
					primaryStage.setScene(scenex);
					primaryStage.show();
				}

			}

		});
		Group root = (Group) scene.getRoot();
		root.getChildren().addAll(pane);

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	};

	public void file(File file, String username) {
		String photoname;
		photoname = file.getName();

		BufferedImage bf;
		try {
			bf = ImageIO.read(file);
			Image image = SwingFXUtils.toFXImage(bf, null);
			ImageView imgView = new ImageView();
			imgView.setImage(image);
			FileInputStream fin = new FileInputStream(file);
			int len = (int) file.length();
			Connection conn;
			ResultSet rs;
			Class.forName(driver);
			conn = DriverManager.getConnection(url, uid, pwd);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO PhotoInfo(PhotoImage, UserName, PhotoName)"
					+ " VALUES('?'" + ",'" + username + "','" + photoname + "')");
			ps.setBinaryStream(1, fin, len);
			int status = ps.executeUpdate();
			if (status > 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("Information dialog");
				alert.setContentText("Photo saved successfully");
				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog.");
				alert.setHeaderText("Error Information");
				alert.showAndWait();
			}
			conn.close();
		} catch (IOException ex) {
			Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	// public void openFile(File file) {
	// try {
	// desktop.open(file);
	// } catch (IOException ex) {
	// Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
	// }
	// }

	public void signup(String username, String firstName, String lastName, String password) {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, uid, pwd);
			statement = connection.createStatement();

			// // insert
			String sql = "Insert Into UserInfo ( UserName, FirstName,LastName ,password)" + " VALUES('" + username
					+ "','" + firstName + "','" + lastName + "','" + password + "')";

			statement.executeUpdate(sql);

			connection.close();

		} catch (

		Exception ex) {
			ex.printStackTrace();
		}
	}

	public void check(String UserID, String textfield) {

		String password = null;
		Connection connection = null;
		Statement statement = null;
		Statement statement1 = null;
		ResultSet resultSet = null;

		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, uid, pwd);
			statement = connection.createStatement();
			statement1 = connection.createStatement();

			resultSet = statement.executeQuery("select * from UserInfo where Username='" + UserID + "' ");

			while (resultSet.next()) {

				password = resultSet.getString("password");

				if (!password.toString().equals(textfield.toString())) {
					System.out.println("wrong!");

					checkpass.setText(password);

				} else {

					System.out.println("correct");
					checkpass.setText(password);

				}
			}
			connection.close();
			resultSet.close();

		} catch (

		Exception ex) {
			ex.printStackTrace();
		}
	}

	public void delete(String UserID, String text) {

		Connection connection = null;
		Statement statement = null;
		Statement statement1 = null;
		ResultSet resultSet = null;
		String password = null;

		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, uid, pwd);
			statement = connection.createStatement();
			statement1 = connection.createStatement();

			resultSet = statement.executeQuery("select * from UserInfo where Username='" + UserID + "' ");

			while (resultSet.next()) {

				password = resultSet.getString("password");

				if (!text.toString().equals(password.toString())) {
					System.out.println("you have wrong password");

				} else {

					String sql = "Delete From UserInfo where username ='" + UserID + "' ";
					statement1.executeUpdate(sql);
					System.out.println("you have deleted your account");
				}
			}
			resultSet.close();
			connection.close();

		} catch (

		Exception ex) {
			ex.printStackTrace();

		}
	}

	public void notes(String username, String notes) {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		Random random = new Random();
		int noteid = random.nextInt(1000);

		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, uid, pwd);
			statement = connection.createStatement();

			Calendar calendar = Calendar.getInstance();
			java.sql.Date ourJavaDateObject = new java.sql.Date(calendar.getTime().getTime());

			// insert
			String sql = "Insert Into NoteInfo (NoteID, username,Details,timestamp)" + " VALUES('" + noteid + "','"
					+ username + "','" + notes + "','" + ourJavaDateObject + "')";

			statement.executeUpdate(sql);

			connection.close();

		} catch (

		Exception ex) {
			ex.printStackTrace();
		}
	}

}
