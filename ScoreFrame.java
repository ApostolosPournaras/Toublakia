import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Arrays;

class ScoreFrame extends JFrame{

    public ScoreFrame(String[] ReadNames, int[] ReadScores, int[] ReadLines){
        
        super("Τα 10 καλύτερα σκόρ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JLabel[] NumberLabels = new JLabel[11];
        JLabel[] NameLabels = new JLabel[11];
        JLabel[] ScoreLabels = new JLabel[11];
        JLabel[] LineLabels = new JLabel[11];
        
        NumberLabels[0] = new JLabel("   Κατάταξη");
        for(int i=1; i<11; i++)
            NumberLabels[i] = new JLabel("         "+i);
            
        NameLabels[0] = new JLabel("Όνομα Παίχτη");
        for(int i=1; i<11; i++)
            NameLabels[i] = new JLabel(ReadNames[i-1]);
            
        ScoreLabels[0] = new JLabel("Σκορ");
        for(int i=1; i<11; i++)
            ScoreLabels[i] = new JLabel(""+ReadScores[i-1]);
            
        LineLabels[0] = new JLabel("Γραμμές");
        for(int i=1; i<11; i++)
            LineLabels[i] = new JLabel(""+ReadLines[i-1]);

        JPanel panel = new JPanel();
        GridLayout grid = new GridLayout(11,4);
        
        panel.setLayout(grid);
        
        for(int i=0; i<11; i++){
            panel.add(NumberLabels[i]);
            panel.add(NameLabels[i]);    
            panel.add(ScoreLabels[i]);
            panel.add(LineLabels[i]);
        }
        
        setContentPane(panel);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2 -150, 100, 400, 600);
        setResizable(false);
        setVisible(true);
    }
}