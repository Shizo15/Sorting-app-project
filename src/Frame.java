import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    Frame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 600);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setTitle("Sorting App");
        //this.setLayout(null);

        ImageIcon logo=new ImageIcon("jerryLogo.png");
        this.setIconImage(logo.getImage());

        // Ustawienie głównego layoutu na BorderLayout
        this.setLayout(new BorderLayout());

        // Panel górny
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.GRAY);
        JLabel napis = new JLabel("Napis góra");
        topPanel.add(napis);

        // Panel dolny
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.RED);
        JLabel napis4 = new JLabel("Napis dół");
        bottomPanel.add(napis4);

        // Panele środkowe: lewy i prawy
        JPanel leftCenterPanel = new JPanel();
        leftCenterPanel.setBackground(Color.BLUE);

        JPanel rightCenterPanel = new JPanel();
        rightCenterPanel.setBackground(Color.GREEN);

        // SplitPane do podziału środkowej części
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftCenterPanel, rightCenterPanel);
        splitPane.setDividerLocation(300); // Początkowy podział (połowa okna)
        splitPane.setResizeWeight(0);    // Równy podział przestrzeni
        splitPane.setDividerSize(0);      // Szerokość separatora

        this.add(topPanel, BorderLayout.NORTH);
        this.add(splitPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

//        JPanel leftCenterPanel=new JPanel();
//        leftCenterPanel.setBackground(Color.GRAY);
//        JLabel napis2=new JLabel("Napis srodek lewo");
//        leftCenterPanel.add(napis2);
//
//        JPanel rightCenterPanel=new JPanel();
//        rightCenterPanel.setBackground(Color.LIGHT_GRAY);
//        JLabel napis3=new JLabel("Napis srodek prawo");
//        rightCenterPanel.add(napis3);
//
//        JSplitPane splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftCenterPanel, rightCenterPanel);
//        splitPane.setDividerLocation(400);
//        splitPane.setResizeWeight(0.5);
//        this.add(splitPane, BorderLayout.CENTER);
    }
}
