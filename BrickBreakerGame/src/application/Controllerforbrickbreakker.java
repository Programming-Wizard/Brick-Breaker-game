package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Controllerforbrickbreakker implements Initializable 
{

	@FXML
	private GridPane root;
	@FXML
	private MediaView background;
	@FXML
	private Rectangle paddle;
	@FXML
	private Rectangle ball;
	@FXML
	private Line pointer;
	@FXML
	private Text Levelid;
	@FXML
	private Text user_help;

	private Media media;
	private MediaPlayer mediaplayer;
	private boolean ball_Connection = true;
	private boolean shoot_disabled = false;
	int numRows = 3;
	int numColumns = 8;
	private Brick[][] bricks = new Brick[numRows][numColumns];
	AudioClip LevelShowSound = new AudioClip(getClass().getResource("level_change.mp3").toExternalForm());

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		media = new Media(getClass().getResource("background.mp4").toExternalForm());
		mediaplayer = new MediaPlayer(media);
		background.setMediaPlayer(mediaplayer);
		mediaplayer.setAutoPlay(true);
		mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);
		// populating and rendering bricks
		populate();
		LevelShowSound.play();
	}

	// checking if the bricks have finished
	private void populate() 
	{
		bricks = new Brick[numRows][numColumns];
		for (int row = 0; row < numRows; row++) 
		{
			for (int col = 0; col < numColumns; col++) 
			{
				Brick brick = new Brick(Color.rgb(197, 249, 103), 75, 35);
				bricks[row][col] = brick;

				Rectangle brickShape = brick.getShape();

				// Adding the brick shape to the bricks container (GridPane)
				root.add(brickShape, col, row);
			}
		}
	}
	public boolean areBricksRemoved() 
	{
		return GameUtils.Check_If_Bricksareover(numRows, numColumns, bricks);
	}

	public void paddle_right() 
	{
		// Setting Boundary for paddle
		double x = paddle.getTranslateX();
		if (x >= 245) 
		{
			return;
		}
		GameUtils.paddle_right(ball_Connection, paddle, ball, pointer);
	}

	public void paddle_left() 
	{
		// Setting boundary for paddle
		double x = paddle.getTranslateX();
		if (x <= -245) 
		{
			return;
		}
		GameUtils.paddle_left(ball_Connection, paddle, ball, pointer);
	}

	private void Spin_the_ball() 
	{
		GameUtils.spin(ball);
	}

	public void Pointer_To_Right() 
	{
		GameUtils.Move_Pointer_right(pointer);
	}

	public void Pointer_To_Left() 
	{
		GameUtils.Move_Pointer_Left(pointer);
	}

	public void shoot_ball() 
	{
		if (!shoot_disabled) 
		{
			GameUtils.Shoot_Mechanism(shoot_disabled, ball, pointer, paddle, numRows, numColumns, bricks, root,
					user_help, ball_Connection);
		}
		ball_Connection = false;
		shoot_disabled = true;
		return;
	}

}