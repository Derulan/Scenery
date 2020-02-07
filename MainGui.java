import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainGui {

   private static final int TIMER_DELAY = 100;

   private static void createAndShowGui() {
      // create our JPanel
      MainGuiPanel mainPanel = new MainGuiPanel();
      // and create our ActionListener for the Swing Timer
      MyTimerListener myTimerListener = new MyTimerListener(mainPanel);

      // create a JFrame
      JFrame frame = new JFrame("Main Gui");
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

      // add our JPanel to it
      frame.getContentPane().add(mainPanel);
      frame.pack();
      frame.setLocationByPlatform(true);
      frame.setVisible(true);  // display the GUI

      // create and start the Swing Timer
      new Timer(TIMER_DELAY, myTimerListener).start();
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGui();
         }
      });
   }
}

@SuppressWarnings("serial")
class MainGuiPanel extends JPanel {
   private static final int PREF_W = 700;
   private static final int PREF_H = 500;
   private int textX = 0;
   private int textY = 10;

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(Color.blue);
      g.drawString("Test", textX, textY);
      System.out.println("Goodbye");
   }

   @Override
   // make our GUI larger
   public Dimension getPreferredSize() {
      Dimension superSz = super.getPreferredSize();
      if (isPreferredSizeSet()) {
         return superSz;
      }
      int prefW = Math.max(superSz.width, PREF_W);
      int prefH = Math.max(superSz.height, PREF_H);
      return new Dimension(prefW, prefH);
   }

   public void moveText() {
      textX++;
      textY++;
      repaint();
   }
}

// ActionListener for Swing Timer that drives the game loop
class MyTimerListener implements ActionListener {
   private MainGuiPanel mainGuiPanel;

   public MyTimerListener(MainGuiPanel mainGuiPanel) {
      this.mainGuiPanel = mainGuiPanel;
   }

   @Override
   // this method gets called each time the timer "ticks"
   public void actionPerformed(ActionEvent e) {
      // make sure our GUI is not null and is displayed
      if (mainGuiPanel != null && mainGuiPanel.isDisplayable()) {
         // call method to animate.
         mainGuiPanel.moveText(); // this method calls repaint
      } else {
         ((Timer) e.getSource()).stop();
      }
   }
}
