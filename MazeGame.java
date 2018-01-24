
import java.awt.*;
import javax.swing.*;

public class MazeGame extends JFrame {

  public MazeGame(String title) {
    super(title);

    MazePanel mPanel = new MazePanel(this);

    this.add(mPanel, BorderLayout.CENTER);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.pack();
    this.setVisible(true);

  }

  public static void main(String[] args) {
    new MazeGame("Maze Game");
  }
}
