import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;

class ToublakiaPane extends JPanel{

    public static Brick PlayingBrick;
    public static Brick NextBrick;
    public static Graphics2D comp2D;
    private static Random random;
    private static Stack LinesToClear;
    
    public ToublakiaPane(){
        
        super();
        PlayingBrick = new Brick();
        NextBrick = new Brick();
        random = new Random();
    }
    
    public void paintComponent(Graphics comp) {
        comp2D = (Graphics2D)comp;
        
        //color the background
        comp2D.setColor(Color.black);
        Rectangle2D.Float background = new Rectangle2D.Float(0F, 0F, (float)getSize().width, (float)getSize().height);
        comp2D.fill(background);
       
        // Draw grid
        comp2D.setColor(Color.gray);
        BasicStroke pen2 = new BasicStroke();
        comp2D.setStroke(pen2);
        
        //draw grid lines
        for (int x=0; x<11; x++){
            comp2D.draw(new Line2D.Float(30*x, 0, 30*x, 600));
        }
            
        for (int y=0; y<21; y++){
            comp2D.draw(new Line2D.Float(0, 30*y, 300, 30*y));
        }
        

        Font font = new Font("Verdana", Font.BOLD, 18);
        comp2D.setFont(font);
        
        //display Next brick message
        comp2D.drawString("Επόμενο τούβλο",400,50);
        
        //display score and lines and level
        comp2D.drawString("Σκορ: "+ToublakiaGame.Score,420,300);
        comp2D.drawString("Γραμμές: "+ToublakiaGame.Lines,420,330);
        comp2D.drawString("Επίπεδο: "+ToublakiaGame.Level,420,390);
          
        
        //draw next brick grid lines

        for (int x=0; x<5; x++){
            comp2D.draw(new Line2D.Float(420+30*x, 60, 420+30*x, 180));
        }
            
        for (int y=0; y<5; y++){
            comp2D.draw(new Line2D.Float(420, 60+30*y, 540, 60+30*y));
        }
        
        FillGrid();
        PlayingBrick.DrawBrick();
        NextBrick.DrawBrick();
        
        comp2D.setColor(Color.white);
        font = new Font("Verdana", Font.BOLD, 38);
        FontMetrics fm = getFontMetrics(font);
        comp2D.setFont(font);
        int x = (getSize().width - fm.stringWidth(ToublakiaGame.pausedString))/2;
        int y = getSize().height/2;
        comp2D.drawString(ToublakiaGame.pausedString,x,y);
    }
    
    
    private static void FillGrid(){
        for(int i=0; i<10; i++)
            for(int j=0; j<20; j++){
                if(ToublakiaGame.FilledSquare[i][j] == true){
                    comp2D.setColor(ToublakiaGame.SquareColor[i][j]);
                    comp2D.fill(new Rectangle2D.Float(30*i+2, 30*j+2, 28F, 28F));
                }else{
                    comp2D.setColor(Color.black);
                    comp2D.fill(new Rectangle2D.Float(30*i+2, 30*j+2, 28F, 28F));
                }
                }
    }
    
    public static void PlayerPoints(int lines){
        if(lines >0)
            ToublakiaGame.Score += lines*(ToublakiaGame.Level+1)*100 +100;
        else
            ToublakiaGame.Score += 20;
    }
    
    
    public static void ClearLine(int lineToClear){
        int line, i;

        for(i=0; i<10; i++){
                ToublakiaGame.FilledSquare[i][lineToClear] = false;
                ToublakiaGame.SquareColor[i][lineToClear] = Color.black;
            }
            
        for(line = lineToClear; line > 0; line--){
            for(i=0; i<10; i++){
                ToublakiaGame.FilledSquare[i][line] = ToublakiaGame.FilledSquare[i][line-1];
                ToublakiaGame.SquareColor[i][line] = ToublakiaGame.SquareColor[i][line-1];
            }
        }
    }
    
    public static void FindLines(){
        boolean result=true;
        int linesCount=0;
        
        for(int j=0; j<20; j++){
            
            for(int i=0; i<10; i++)
                result = result && ToublakiaGame.FilledSquare[i][j];

            if(result ==true){
                linesCount++;
                ClearLine(j); 
            }
                
           result = true;
        }
        
        ToublakiaGame.LevelLines += linesCount;
        ToublakiaGame.Lines += linesCount;
        
        PlayerPoints(linesCount);
        
        if((float)(ToublakiaGame.LevelLines)/10F >=1F){
            ToublakiaGame.Level++;
            ToublakiaGame.Speed = (int)(4000/(0.7*ToublakiaGame.Level + 4));
            ToublakiaGame.LevelLines = ToublakiaGame.LevelLines%10;
        }
        
        if(linesCount>0){
            ToublakiaGame.effectPlayer.Run("ClearLine.wav", false);
        }
        
    }
    
    public static void NewBricks(){
        PlayingBrick.NewBrick(NextBrick.type, false);
        NextBrick.NewBrick(random.nextInt(7)+1, true);
    }
    
    
    public static void NewGame(){
        
        for(int i=0; i<10; i++)
            for(int j=0; j<20; j++){
                ToublakiaGame.FilledSquare[i][j]= false;
                ToublakiaGame.SquareColor[i][j] = new Color(240,240,240);
            }
            
        NewBricks();        
        ToublakiaGame.Score=0;
        ToublakiaGame.Lines=0;
        ToublakiaGame.Level=0;
        ToublakiaGame.Speed=1000;
        ToublakiaGame.paused = ToublakiaGame.paused && false;
        ToublakiaGame.EndOfGame = ToublakiaGame.EndOfGame && false;
    }
    
}