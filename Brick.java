import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.*;

class Brick{

    //x and y values of the 4 blocks of each brick
    float[][] BrickXY = new float[4][2];
    float[] center = new float[2];
    float leftmost=-1, rightmost=-1, upmost=-1, bottomost=-1;
    int type;
    Color color;
    
    Brick(){
        for(int i=0; i<4; i++)
            for (int j=0; j<2; j++)
                BrickXY[i][j]=-1;
       
        for (int j=0; j<2; j++)
            center[j]=-1;
            
            Random random = new Random();
            type=random.nextInt(7)+1;
    }

    
    void NewBrick(int type, boolean next){
        this.type=type;
        
        switch (type){
            
            case 1:
                this.color = new Color(255,100,0); //square, orange
                this.BrickXY[0][0] = 4;
                this.BrickXY[0][1] = 0;
                this.BrickXY[1][0] = 5;
                this.BrickXY[1][1] = 0;
                this.BrickXY[2][0] = 4;
                this.BrickXY[2][1] = 1;
                this.BrickXY[3][0] = 5;
                this.BrickXY[3][1] = 1;
                
                this.center[0]=4;
                this.center[1]=1;
                
                leftmost = 4;
                rightmost = 5;
                upmost = 0;
                bottomost = 1;
                break;
            
            case 2:
                this.color = new Color(255,6,6); //line, red
                this.BrickXY[0][0] = 4;
                this.BrickXY[0][1] = 0;
                this.BrickXY[1][0] = 4;
                this.BrickXY[1][1] = 1;
                this.BrickXY[2][0] = 4;
                this.BrickXY[2][1] = 2;
                this.BrickXY[3][0] = 4;
                this.BrickXY[3][1] = 3;
                
                this.center[0]=4;
                this.center[1]=1;
                
                leftmost = 4;
                rightmost = 4;
                upmost = 0;
                bottomost = 3;
                break;
            
            case 3:
                this.color = new Color(120,255,0); //sigma, lime
                this.BrickXY[0][0] = 4;
                this.BrickXY[0][1] = 0;
                this.BrickXY[1][0] = 5;
                this.BrickXY[1][1] = 0;
                this.BrickXY[2][0] = 3;
                this.BrickXY[2][1] = 1;
                this.BrickXY[3][0] = 4;
                this.BrickXY[3][1] = 1;
                
                this.center[0]=4;
                this.center[1]=0;
                
                leftmost = 3;
                rightmost = 5;
                upmost = 0;
                bottomost = 1;
                break;
            
            case 4:
                this.color = new Color(16,255,255); //zeta, cyan
                this.BrickXY[0][0] = 3;
                this.BrickXY[0][1] = 0;
                this.BrickXY[1][0] = 4;
                this.BrickXY[1][1] = 0;
                this.BrickXY[2][0] = 4;
                this.BrickXY[2][1] = 1;
                this.BrickXY[3][0] = 5;
                this.BrickXY[3][1] = 1;
                
                this.center[0]=4;
                this.center[1]=0;
                
                leftmost = 3;
                rightmost = 5;
                upmost = 0;
                bottomost = 1;
                break;
            
            case 5:
                this.color = new Color(255,252,0); //taf, yellow
                this.BrickXY[0][0] = 4;
                this.BrickXY[0][1] = 0;
                this.BrickXY[1][0] = 3;
                this.BrickXY[1][1] = 1;
                this.BrickXY[2][0] = 4;
                this.BrickXY[2][1] = 1;
                this.BrickXY[3][0] = 5;
                this.BrickXY[3][1] = 1;
                
                this.center[0]=4;
                this.center[1]=1;
                
                leftmost = 3;
                rightmost = 5;
                upmost = 0;
                bottomost = 1;
                break;
            
            case 6:
                this.color = new Color(255,0,172); //el, pink
                this.BrickXY[0][0] = 4;
                this.BrickXY[0][1] = 0;
                this.BrickXY[1][0] = 4;
                this.BrickXY[1][1] = 1;
                this.BrickXY[2][0] = 4;
                this.BrickXY[2][1] = 2;
                this.BrickXY[3][0] = 5;
                this.BrickXY[3][1] = 2;
                
                this.center[0]=4;
                this.center[1]=1;
                
                leftmost = 4;
                rightmost = 5;
                upmost = 0;
                bottomost = 2;
                break;
            
            case 7:
                this.color = new Color(30,36,210); //jay, blue
                this.BrickXY[0][0] = 5;
                this.BrickXY[0][1] = 0;
                this.BrickXY[1][0] = 5;
                this.BrickXY[1][1] = 1;
                this.BrickXY[2][0] = 4;
                this.BrickXY[2][1] = 2;
                this.BrickXY[3][0] = 5;
                this.BrickXY[3][1] = 2;
                
                this.center[0]=5;
                this.center[1]=1;
                
                leftmost = 4;
                rightmost = 5;
                upmost = 0;
                bottomost = 2;
                break;
            
            default:
                this.type=1;
                this.color = new Color(255,100,0); //square, orange
                this.BrickXY[0][0] = 4;
                this.BrickXY[0][1] = 0;
                this.BrickXY[1][0] = 5;
                this.BrickXY[1][1] = 0;
                this.BrickXY[2][0] = 4;
                this.BrickXY[2][1] = 1;
                this.BrickXY[3][0] = 5;
                this.BrickXY[3][1] = 1;
                
                this.center[0]=4;
                this.center[1]=1;
                
                leftmost = 4;
                rightmost = 5;
                upmost = 0;
                bottomost = 1;
                break;
        }
        
        if(next==true){
            for(int i=0; i<4; i++){
                this.BrickXY[i][0] += 11;
                this.BrickXY[i][1] += 2;
            }
        }
    }
    
    
    void DrawSquare(float gridX, float gridY, Graphics2D comp2D, Color color){
        comp2D.setColor(color);
        comp2D.fill(new Rectangle2D.Float(30*gridX+2, 30*gridY+2, 28F, 28F));
        
    }
    
    
    void DrawBrick(){
        for(int i=0; i<4; i++)
            DrawSquare(this.BrickXY[i][0], this.BrickXY[i][1], ToublakiaPane.comp2D, this.color);
    }
    
    
    boolean Exceed(){
        boolean exceeded=false;
        
        for(int i=0; i<4; i++){
                if(this.BrickXY[i][0]<0 || this.BrickXY[i][0]>9 || this.BrickXY[i][1]<0 || this.BrickXY[i][1]>19)
                    exceeded = exceeded || true;
                else
                    exceeded = exceeded || false;
        }
        return exceeded;
    }
    
    
    boolean Filled(int choise){
        int x,y;
        boolean upFilled = false;
        boolean downFilled = false;
        boolean leftFilled = false;
        boolean rightFilled = false;
        boolean returnVal = false;
        
        
        //find if there is a filled square around of the brick
            for(int i=0; i<4; i++){
                x = (int)this.BrickXY[i][0];
                y = (int)this.BrickXY[i][1];
            
                if(x-1>=0 && x+1<=9){
                    leftFilled = leftFilled || ToublakiaGame.FilledSquare[x-1][y];
                    rightFilled = rightFilled || ToublakiaGame.FilledSquare[x+1][y];
                }
                
                if(y-1>=0 && y+1<=19){
                    upFilled = upFilled || ToublakiaGame.FilledSquare[x][y-1];
                    downFilled = downFilled || ToublakiaGame.FilledSquare[x][y+1];
                }
            }
            
            switch (choise){
                
                case 1:
                    returnVal = upFilled;
                    break;
                    
                case 2:
                    returnVal = downFilled;
                    break;
                
                case 3:
                    returnVal = leftFilled;
                    break;
                
                case 4:
                    returnVal = rightFilled;
                    break;
                
                case 5:
                    returnVal = (upFilled || downFilled || leftFilled || rightFilled);
                    break;
            }
            return returnVal;
    }
    
    
    void Rotate(){
            if(this.type != 1){
            float temp, tempUp, tempBottom;
            Brick tempBrick = new Brick();
            
            //copy cordinates to a temp brick
            for(int i=0; i<4; i++){
                tempBrick.BrickXY[i][0]=this.BrickXY[i][0];
                tempBrick.BrickXY[i][1]=this.BrickXY[i][1];
            }

                
            //rotate the temp brick
            for(int i=0; i<4; i++){
                tempBrick.BrickXY[i][0] -= this.center[0];
                tempBrick.BrickXY[i][1] -= this.center[1];
                temp = tempBrick.BrickXY[i][0];
                tempBrick.BrickXY[i][0] = tempBrick.BrickXY[i][1];
                tempBrick.BrickXY[i][1] = -temp;
                tempBrick.BrickXY[i][0] += this.center[0];
                tempBrick.BrickXY[i][1] += this.center[1];
                
                tempUp = this.upmost;
                tempBottom = this.bottomost;
                
                tempBrick.upmost = -(this.rightmost - this.center[0]) + this.center[1];
                tempBrick.bottomost = -(this.leftmost - this.center[0]) + this.center[1];
                tempBrick.leftmost = (tempUp - this.center[1]) + this.center[0];
                tempBrick.rightmost = (tempBottom - this.center[1]) + this.center[0];
            }

            //if ok, rotate the original brick
            if(tempBrick.Exceed()==false)
                if(tempBrick.Filled(5)==false)
                    for(int i=0; i<4; i++){
                        this.BrickXY[i][0] = tempBrick.BrickXY[i][0];
                        this.BrickXY[i][1] = tempBrick.BrickXY[i][1];
                        this.upmost = tempBrick.upmost;
                        this.bottomost = tempBrick.bottomost;
                        this.leftmost = tempBrick.leftmost;
                        this.rightmost = tempBrick.rightmost;
                    }
        }
        ToublakiaGame.effectPlayer.Run("Rotate.wav", false);
    }
    
    void Left(){
        if(this.leftmost > 0 && this.Filled(3)==false){
            for(int i=0; i<4; i++)
            this.BrickXY[i][0] -= 1;
                
            this.center[0] -= 1;
            this.leftmost -= 1;
            this.rightmost -= 1;
        }
    }
    
    void Right(){
        if(this.rightmost < 9 && this.Filled(4)==false){
            for(int i=0; i<4; i++)
                this.BrickXY[i][0] += 1;
                
            this.center[0] += 1;
            this.leftmost += 1;
            this.rightmost += 1;
        }
    }
    
    
    void Drop(){
        int x,y;
        
        //settle the brick
        if(this.bottomost == 19 || this.Filled(2) == true){
            for(int i=0; i<4; i++){
                x = (int)this.BrickXY[i][0];
                y = (int)this.BrickXY[i][1];
                ToublakiaGame.FilledSquare[x][y]=true;
                ToublakiaGame.SquareColor[x][y]=this.color;
            }
            
            if(ToublakiaGame.EndOfGame() == false){
                ToublakiaPane.FindLines();
                ToublakiaPane.NewBricks();
                ToublakiaGame.effectPlayer.Run("Drop.wav", false);
                return;
            }
        }
        
        //if obligations are satisfied, drop brick
        if(this.bottomost < 19)
            if(this.Filled(2) == false){
                for(int i=0; i<4; i++)
                    this.BrickXY[i][1] += 1;
                    
                this.center[1] += 1;
                this.bottomost += 1;
                this.upmost += 1;
            }
    }
    
}
        