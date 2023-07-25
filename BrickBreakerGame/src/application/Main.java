package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("BrickBreaker.fxml"));
			Pane root = loader.load();
			Scene scene = new Scene(root, 680, 680);
			Image icon = new Image("D:/JAVAFX_projects/Brick_Breaker/src/application/brickgameicon.png");
			primaryStage.setTitle("Brick Breaker");
			primaryStage.getIcons().add(icon);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			Controllerforbrickbreakker controller = loader.getController();
			scene.setOnKeyPressed(event -> {

				KeyCode keyPressed = event.getCode();

				if (keyPressed == KeyCode.RIGHT) {
					controller.paddle_right();
				}
				if (keyPressed == KeyCode.LEFT) {
					controller.paddle_left();
				}
				if (keyPressed == KeyCode.SPACE) {
					controller.shoot_ball();
				}
				if (keyPressed == KeyCode.A) {
					controller.Pointer_To_Left();
				}
				if (keyPressed == KeyCode.D) {
					controller.Pointer_To_Right();
				}
				if(keyPressed == KeyCode.SPACE && controller.areBricksRemoved())
				{
					FXMLLoader new_Loader  = new FXMLLoader(getClass().getResource("Level2.fxml"));
					try {
						Parent new_root = new_Loader.load();
						Scene new_Scene = new Scene(new_root);
						primaryStage.setScene(new_Scene);

						LevelController new_controller = new_Loader.getController();
						new_Scene.setOnKeyPressed(new_event -> {

							KeyCode new_keyPressed = new_event.getCode();

							if (new_keyPressed == KeyCode.RIGHT) {
								new_controller.paddle_right();
							}
							if (new_keyPressed == KeyCode.LEFT) {
								new_controller.paddle_left();
							}
							if (new_keyPressed == KeyCode.SPACE) {
								new_controller.shoot_ball();
							}
							if (new_keyPressed == KeyCode.A) {
								new_controller.Pointer_To_Left();
							}
							if (new_keyPressed == KeyCode.D) {
								new_controller.Pointer_To_Right();
							}
						});

					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			});
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
