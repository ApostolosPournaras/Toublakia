 public class EffectsPlayer implements Runnable{
     
    private Thread runner;
    public String WavFile;
    public boolean loop;
    public WavPlayer myPlayer = new WavPlayer();
    
    EffectsPlayer(){}
    
    public void Run(String WavFile, boolean loop){
        if(runner == null){
            runner = new Thread(this);
            this.WavFile = WavFile;
            this.loop = loop;
            runner.start();
        }
    }
        
    public void run(){
        this.myPlayer.Play(this.WavFile, loop);
        runner = null;
    }
    
    public void Stop(){
         this.myPlayer.sound.stop();
    }
 }