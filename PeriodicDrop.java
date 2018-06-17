 public class PeriodicDrop implements Runnable{
     private Thread runner;
     
    PeriodicDrop(){
        if(runner == null){
            runner = new Thread(this);
            runner.start();
        }
    }
        
     public void run(){
         while(true){
             try{
                 while(true){
                     if(ToublakiaGame.EndOfGame == false)
                        if(ToublakiaGame.paused == false){
                            ToublakiaPane.PlayingBrick.Drop();
                            ToublakiaGame.ToublakiaPane.repaint();
                        }
                     Thread.sleep(ToublakiaGame.Speed);
                 }
              }
              catch(InterruptedException ie){}
            }
        }
    }