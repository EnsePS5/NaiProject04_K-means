import javax.swing.*;
import java.awt.*;

public class GUIDrawer extends JPanel {

    private int firstVal,secondVal;
    private Color colorType;

    public GUIDrawer(){
        SwingUtilities.invokeLater(this::createGUI);
    }
    private void createGUI(){

        String mainTitle = "NAIProject01_Knn";

        JFrame frame = new JFrame();
        frame.setTitle(mainTitle);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(this);

        frame.setSize(600,400);

        frame.setVisible(true);

    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

       /* for (int i = 0; i < data.listOfPoints.size(); i++) {

            var tempVal = data.listOfPoints.get(i).getPointValues();*/

            //setVales(tempVal.get(0), tempVal.get(1), data.listOfPoints.get(i).getPointType());

            graphics.setColor(colorType);
            graphics.fillRect(firstVal,secondVal,10,10);
            graphics.setColor(Color.BLACK);
            graphics.drawRect(firstVal,secondVal,10,10);
    }
}
