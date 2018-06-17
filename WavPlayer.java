import java.net.URL;
import java.applet.*;

public class WavPlayer {

    public AudioClip sound;
    public URL urlForTheSound;
        
   WavPlayer(){}
    
    public void Play(String filename, boolean loop) {
        
         this.urlForTheSound = WavPlayer.class.getResource(filename);
         
         if(this.urlForTheSound == null)
            try{
                this.urlForTheSound = new URL("file:///"+filename);
            }catch(Exception e){}

         this.sound = Applet.newAudioClip(this.urlForTheSound);
         
         if(loop == true)
            this.sound.loop();
            
         else
            this.sound.play();
    }
    public void Stop(){
        this.sound.stop();
    }
}