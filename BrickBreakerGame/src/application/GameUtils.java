package application;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class GameUtils {
	//	static AudioClip BumpSound = new AudioClip(getClass().getResource("/bump.mp3").toExternalForm());
	//	static AudioClip PaddleHitSound = new AudioClip(getClass().getResource("/paddle_hit.mp3").toExternalForm());
	//	static AudioClip BrickHitSound = new AudioClip(getClass().getResource("/brick_hit.mp3").toExternalForm());
	private static void playSound(String soundFile) {
		AudioClip sound = new AudioClip(GameUtils.class.getResource(soundFile).toExternalForm());
		sound.play();
	}
	//	Method for moving paddle right
	public static void paddle_right(boolean ball_Connection,Rectangle paddle, Rectangle ball, Line pointer) {
		if (ball_Connection)
		{

			// Moving paddle to right
			double X_of_Paddle = paddle.getTranslateX() + 60;

			KeyValue keyvalue_Paddle = new KeyValue(paddle.translateXProperty(), X_of_Paddle);
			KeyFrame keyframe_Paddle = new KeyFrame(Duration.millis(50), keyvalue_Paddle);

			// Synchronizing ball with paddle
			KeyValue keyvalue_Ball = new KeyValue(ball.translateXProperty(), X_of_Paddle);
			KeyFrame keyframe_Ball = new KeyFrame(Duration.millis(175), keyvalue_Ball);

			// synchronizing pointer with paddle
			KeyValue keyvalue_Pointer = new KeyValue(pointer.translateXProperty(), X_of_Paddle);
			KeyFrame keyframe_Pointer = new KeyFrame(Duration.millis(175), keyvalue_Pointer);

			Timeline Paddle_Ball_Pointer_timeline = new Timeline(keyframe_Paddle, keyframe_Ball, keyframe_Pointer);
			Paddle_Ball_Pointer_timeline.stop();
			Paddle_Ball_Pointer_timeline.play();

		}
		if (!ball_Connection) 
		{
			// Moving paddle to right
			double X_of_Paddle = paddle.getTranslateX() + 60;

			KeyValue keyvalue_Paddle = new KeyValue(paddle.translateXProperty(), X_of_Paddle);
			KeyFrame keyframe_Paddle = new KeyFrame(Duration.millis(50), keyvalue_Paddle);

			Timeline Paddle_Timeline = new Timeline(keyframe_Paddle);
			Paddle_Timeline.stop();
			Paddle_Timeline.play();
		}
	}

	//	Method for moving paddle left
	public static void paddle_left(boolean ball_Connection,Rectangle paddle, Rectangle ball, Line pointer) {
		if (ball_Connection) 
		{
			// Moving paddle to left
			double X_of_Paddle = paddle.getTranslateX() - 60;

			KeyValue keyvalue_Paddle = new KeyValue(paddle.translateXProperty(), X_of_Paddle);
			KeyFrame keyframe_Paddle = new KeyFrame(Duration.millis(50), keyvalue_Paddle);

			// Synchronizing ball with paddle
			KeyValue keyvalue_Ball = new KeyValue(ball.translateXProperty(), X_of_Paddle);
			KeyFrame keyframe_Ball = new KeyFrame(Duration.millis(175), keyvalue_Ball);

			// synchronizing pointer with paddle
			KeyValue keyvalue_Pointer = new KeyValue(pointer.translateXProperty(), X_of_Paddle);
			KeyFrame keyframe_Pointer = new KeyFrame(Duration.millis(175), keyvalue_Pointer);

			Timeline Paddle_Ball_Pointer_timeline = new Timeline(keyframe_Paddle, keyframe_Ball, keyframe_Pointer);
			Paddle_Ball_Pointer_timeline.stop();
			Paddle_Ball_Pointer_timeline.play();
		}
		if (!ball_Connection) 
		{
			// Moving paddle to left
			double X_of_Paddle = paddle.getTranslateX() - 60;

			KeyValue keyvalue_Paddle = new KeyValue(paddle.translateXProperty(), X_of_Paddle);
			KeyFrame keyframe_Paddle = new KeyFrame(Duration.millis(50), keyvalue_Paddle);

			Timeline Paddle_Timeline = new Timeline(keyframe_Paddle);
			Paddle_Timeline.stop();
			Paddle_Timeline.play();
		}
	}

	//	Method for start spinning the ball
	public static void spin(Rectangle ball)
	{
		RotateTransition rotate = new RotateTransition(Duration.millis(5),ball);
		rotate.setAxis(Rotate.Z_AXIS);
		rotate.setByAngle(360);
		rotate.setCycleCount(RotateTransition.INDEFINITE);
		rotate.play();
	}

	//	Method to point the pointer
	public static void Move_Pointer_right(Line pointer)
	{
		pointer.setStartX(pointer.getStartX() + 10);
	}

	//	Method to point the pointer
	public static void Move_Pointer_Left(Line pointer)
	{
		pointer.setStartX(pointer.getStartX() - 10);
	}

	//	method to check whether the level is over 
	public static boolean Check_If_Bricksareover(int numRows,int numColumns, Brick[][] bricks)
	{
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				if (bricks[row][col] != null) {
					return false;
				}
			}
		}
		return true;

	}

	//	bricks generating
	public static void Populating_Bricks(Brick[][] bricks, int numRows, int numColumns, GridPane root)
	{
		bricks = new Brick[numRows][numColumns];
		for (int row = 0; row < numRows; row++) 
		{
			for (int col = 0; col < numColumns; col++) 
			{
				Brick brick = new Brick(Color.BURLYWOOD, 75, 35);
				bricks[row][col] = brick;

				Rectangle brickShape = brick.getShape();

				// Adding the brick shape to the bricks container (GridPane)
				root.add(brickShape, col, row);
			}
		}
	}
	//	handling the shooting and collisions here
	public static void Shoot_Mechanism(boolean shoot_disabled, 
			Rectangle ball,
			Line pointer,
			Rectangle paddle,
			int numRows,
			int numColumns,
			Brick[][] bricks,
			GridPane root,
			Text user_help,
			boolean ball_Connection)
	{
		spin(ball);
		// ball shooting variables
		final double distance_Per_Iteration = 10.0;
		final double[] shootingDistance = { ball.getTranslateY(), ball.getTranslateX() };

		// paddle variables
		double paddleWidth = 188;
		double paddleHeight = 22;

		// paddle coordinates

		double pointer_X = pointer.getStartX();
		double pointer_Y = pointer.getStartY();

		double angle = Math.atan2(pointer_Y - ball.getTranslateY(), pointer_X - ball.getTranslateX());
		final double[] velocity_X = { Math.cos(angle) * distance_Per_Iteration };
		final double[] velocity_Y = { Math.sin(angle) * distance_Per_Iteration };

		// ball settings
		KeyFrame keyframeBall = new KeyFrame(Duration.millis(16), event -> {
			shootingDistance[0] -= velocity_Y[0];
			shootingDistance[1] += velocity_X[0];

			double ballX = shootingDistance[1];
			double ballY = shootingDistance[0];

			if (ballX < -340) 
			{
				ballX = -340;
				playSound("bump.mp3");
				velocity_X[0] = -velocity_X[0];
			} 
			else if (ballX > 340) 
			{
				ballX = 340;
				playSound("bump.mp3");
				velocity_X[0] = -velocity_X[0];
			}

			if (ballY < -600) 
			{
				ballY = -600;
				playSound("bump.mp3");
				velocity_Y[0] = -velocity_Y[0];
			} 
			else if (ballY > 100) 
			{
				ballY = 100;
				playSound("bump.mp3");
				velocity_Y[0] = -velocity_Y[0];
			}

			ball.setTranslateY(ballY);
			ball.setTranslateX(ballX);
		});

		Timeline ball_Timeline = new Timeline(keyframeBall);
		ball_Timeline.setCycleCount(Timeline.INDEFINITE);
		ball_Timeline.play();

		// new timeline for paddle collision detection
		Timeline collision_Timeline = new Timeline(new KeyFrame(Duration.millis(16), event -> {

			double paddleLeft = (paddle.getTranslateX() - paddleWidth / 2) - 2;
			double paddleRight = (paddle.getTranslateX() + paddleWidth / 2) + 2;
			double paddleTop = (paddle.getTranslateY() - paddleHeight / 2) + 5;
			double paddleBottom = (paddle.getTranslateY() + paddleHeight / 2) ;

			double ballX = ball.getTranslateX();
			double ballY = ball.getTranslateY();

			if (ballY >= paddleTop && ballY <= paddleBottom && ballX >= paddleLeft && ballX <= paddleRight) 
			{
				playSound("paddle_hit.mp3");
				velocity_Y[0] = -velocity_Y[0];
			}
		}));

		collision_Timeline.setCycleCount(Timeline.INDEFINITE);
		collision_Timeline.play();

		// new timeline for bricks and ball collision
		final Timeline collisionDetectionTimeline = new Timeline();;
		KeyFrame collisionDetectionKeyframe = new KeyFrame(Duration.millis(16), event -> {

			for (int row = 0; row < numRows; row++) {
				for (int col = 0; col < numColumns; col++) {
					Brick brick = bricks[row][col];
					// Handling the collision between ball and brick
					if (brick != null
							&& ball.getBoundsInParent().intersects(brick.getShape().getBoundsInParent())) 
					{
						root.getChildren().remove(brick.getShape());
						bricks[row][col] = null;
						playSound("brick_hit.mp3");

						//						showing the level up text
						if(Check_If_Bricksareover(numRows, numColumns, bricks))
						{
							ball_Timeline.stop();
							collision_Timeline.stop();
							user_help.setVisible(true);
						}
						velocity_Y[0] = -velocity_Y[0];
					}
				}
			}
		});

		collisionDetectionTimeline.getKeyFrames().add(collisionDetectionKeyframe);
		collisionDetectionTimeline.setCycleCount(Timeline.INDEFINITE);
		collisionDetectionTimeline.play();

		pointer.setVisible(false);


	}
}
