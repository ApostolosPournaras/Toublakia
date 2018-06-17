import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Arrays;



public class ToublakiaGame extends JFrame implements KeyListener, ActionListener{

    public static int Score, Speed, Level, LevelLines, Lines;
    public static boolean[][] FilledSquare;
    public static boolean paused, noMusic, EndOfGame;
    public static String pausedString = "";
    public static String MusicTheme;
    public static String folderPath = "Music";
    public static String[] MusicThemes;
    public static Color[][] SquareColor;
    public static ToublakiaPane ToublakiaPane;
    public static JButton PlayGame, PlayMusic;
    public static JMenuItem StartNewGame, ShowStats, ComputerPlay, aboutGame, aboutEffects, aboutMusic, aboutKeys;
    public static JComboBox ThemeSelect;
    public static EffectsPlayer effectPlayer = new EffectsPlayer();
    public static EffectsPlayer themePlayer = new EffectsPlayer();
    
    public ToublakiaGame(){
        
        super("Τουβλάκια 1.1 - Ένα παιχνίδι πεπτόμενων μικρών τούβλων");
        Score = 0;
        Level = 0;
        Speed = 1000;
        LevelLines = 0;
        Lines = 0;
        FilledSquare = new boolean[10][20];
        SquareColor = new Color[10][20];
        
        for(int i=0; i<10; i++)
            for(int j=0; j<20; j++)
                SquareColor[i][j] = Color.black;
        
        Container contentPane = this.getContentPane();
        
        ToublakiaPane = new ToublakiaPane();
        contentPane.add(ToublakiaPane, BorderLayout.CENTER); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
        // create buttons and add them to the frame
        PlayGame = new JButton("Περιμενε ρε!");
        PlayGame.addActionListener(this);
        PlayGame.setFocusable(false);
        
        JPanel north = new JPanel();
        north.add(PlayGame);
        contentPane.add(north, BorderLayout.NORTH);
        
        
        //create a menu bar and attach it to the frame  
        JMenuBar menuBar = new JMenuBar();  
        this.setJMenuBar(menuBar);  
  
        
        //create menus and add them to the menu bar  
        JMenu OptionsMenu = new JMenu("Επιλογές"); 
        //JMenu AIMenu = new JMenu("Σατανική δύναμη");
        JMenu AboutMenu = new JMenu("Βοήθεια");
        menuBar.add(OptionsMenu);  
        //menuBar.add(AIMenu); 
        menuBar.add(AboutMenu);


        //add menu items to the menus
        StartNewGame = new JMenuItem("Ξεκίνα νέο παιχνίδι");
        StartNewGame.addActionListener(this);
        ShowStats = new JMenuItem("Τα σκορ");
        ShowStats.addActionListener(this);
        //ComputerPlay = new JMenuItem("Μαμά ο υπολογιστής παίζει μόνος του!");
        //ComputerPlay.addActionListener(this);
        aboutGame = new JMenuItem("Σχετικά με το παιχνίδι");
        aboutEffects = new JMenuItem("Περί ηχητικών εφέ");
        aboutMusic = new JMenuItem("Πως να προσθέσουμε μουσική στο παρασκήνιο");
        aboutKeys = new JMenuItem("Τα πλήκτρα του παιχνιδιού");
        aboutGame.addActionListener(this);
        aboutEffects.addActionListener(this);
        aboutMusic.addActionListener(this);
        aboutKeys.addActionListener(this);
        OptionsMenu.add(StartNewGame);  
        OptionsMenu.add(ShowStats);  
        //AIMenu.add(ComputerPlay);
        AboutMenu.add(aboutGame);
        AboutMenu.add(aboutEffects);
        AboutMenu.add(aboutMusic);
        AboutMenu.add(aboutKeys);
        
        
        //create the combo box and play music
        if((new File(folderPath)).listFiles() != null){
            MusicThemes = ListMusic(new File(folderPath));
            if(MusicThemes!=null){
                PlayMusic = new JButton("Όχι μουσικούλα");
                PlayMusic.addActionListener(this);
                PlayMusic.setFocusable(false);
                north.add(PlayMusic);
                MusicTheme = MusicThemes[0];

                ThemeSelect = new JComboBox();
                ThemeSelect.setFocusable(false);
                for(int i=0; i<MusicThemes.length;i++)
                    ThemeSelect.addItem(MusicThemes[i]);
                
                    ThemeSelect.addActionListener(this);
                    north.add(ThemeSelect);
                }
        }
        
        ToublakiaPane.NewGame();
        
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch(Exception e){}
              
        //set the window size and make it unresisable
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2 -300, 100, 600, 690);
        setResizable(false);
        
        //set the window icon
        Image img = Toolkit.getDefaultToolkit().getImage(ToublakiaGame.class.getResource("Toublakia.png"));
        this.setIconImage(img);
        
        this.setFocusable(true);
        this.addKeyListener(this);
        setVisible(true);
        
        //play music
        if(MusicThemes!=null)
            ToublakiaGame.themePlayer.Run(System.getProperty("user.dir")+"/"+folderPath+"/"+MusicTheme ,true);

    }
     
    public static boolean EndOfGame(){
       boolean end=false;
       
       for(int i=0; i<10; i++)
            end = end || FilledSquare[i][0];
       
       if(end==true){
           
           ToublakiaGame.paused = ToublakiaGame.paused || true;
           ToublakiaGame.EndOfGame = ToublakiaGame.EndOfGame || true;
           
           int place = CheckScore();
           if(place>10){
                if(JOptionPane.showConfirmDialog(null,"Πο! γκαντεμιά... Θες να ξεκινήσεις νέο παιχνίδι;", "Νέο παιχνίδι", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
                    ToublakiaPane.NewGame();
           }else{
               UpdateScores(place);
            }
                
       }

       return end;
    }
    
    public void actionPerformed(ActionEvent evt){
        
        String text = (String)evt.getActionCommand();

        if (text.equals("Παίξε να γουστάρεις")){
                PlayGame.setText("Περιμενε ρε!");
                ToublakiaGame.paused = ToublakiaGame.paused && false;
                ToublakiaGame.pausedString = "";
                if(MusicThemes!=null)
                    if(noMusic == false){
                        ToublakiaGame.themePlayer.Run(System.getProperty("user.dir")+"/"+folderPath+"/"+MusicTheme ,true);
                        PlayMusic.setText("Όχι μουσικούλα");
                    }
                repaint();
        }

        if (text.equals("Περιμενε ρε!")){
                PlayGame.setText("Παίξε να γουστάρεις");
                ToublakiaGame.paused = ToublakiaGame.paused || true;
                ToublakiaGame.pausedString = "Κάνε δουλειά σου.....";
                if(MusicThemes!=null){
                    PlayMusic.setText("Παίξε μουσικούλα");
                    ToublakiaGame.themePlayer.Stop();
                }
                repaint();
        }
        
        if (text.equals("Όχι μουσικούλα")){
                PlayMusic.setText("Παίξε μουσικούλα");
                noMusic = noMusic || true;
                if(MusicThemes!=null)
                    ToublakiaGame.themePlayer.Stop();
        }

        if (text.equals("Παίξε μουσικούλα")){
                PlayMusic.setText("Όχι μουσικούλα");
                noMusic = noMusic && false;
                ToublakiaGame.themePlayer.Run(System.getProperty("user.dir")+"/"+folderPath+"/"+MusicTheme ,true);
        }
                        
        if(evt.getActionCommand() == "Ξεκίνα νέο παιχνίδι"){
            if(JOptionPane.showConfirmDialog(null,"Είσαι σίγουρος πως θες να ξεκινήσεις νέο παιχνίδι;", "Νέο παιχνίδι", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
                ToublakiaPane.NewGame();
                repaint();
            }
        }
        
                        
        if(evt.getActionCommand() == "Τα σκορ"){
            ShowScores();
        }
                
        if(evt.getActionCommand() == "Μαμά ο υπολογιστής παίζει μόνος του!"){
            JOptionPane.showMessageDialog(null, "Το συστημα τεχνητής νοημοσύνης δεν έχει δημιουργηθεί ακόμα.");
        }
        
        
        if(evt.getActionCommand() == "Σχετικά με το παιχνίδι"){
            JOptionPane.showMessageDialog(null, "Το παιχνίδι αυτό δημιουργήθηκε απο τον Απόστολο Πουρνάρα,\n Μηχανικό Η/Υ και Πληροφορικής.");
        }
        
        if(evt.getActionCommand() == "Περί ηχητικών εφέ"){
            JOptionPane.showMessageDialog(null, "Όλα τα ηχητικά εφέ που χρησιμοποιούνται στο παιχνίδι, βρέθηκαν στη σελίδα www.freesound.org και έχουν δικαιώματα Creative Commons.\n\n Πιο συγκεκριμένα,\nΟ ήχος περιστροφής βρέθηκε εδω: http://www.freesound.org/people/HardPCM/sounds/32944/\n Ο ήχος εναπόθεσης τούβλου βρέθηκε εδώ: http://www.freesound.org/people/HerbertBoland/sounds/33369/\n Ενώ ο ήχος εκκαθάρισης γραμμής(ών) βρέθηκε εδώ: http://www.freesound.org/people/mattwasser/sounds/58919/\n\nΣτις σελίδες αυτές βρίσκονται τα ονόματα των δημιουργών και τα ακριβή δικαιώματα χρήσης.");
        }
        
        if(evt.getActionCommand() == "Πως να προσθέσουμε μουσική στο παρασκήνιο"){
            JOptionPane.showMessageDialog(null, "Δημιουργήστε έναν φάκελο με το όνομα 'Music' -χωρίς τα εισαγωγικα-\nστην ίδια τοποθεσία που βρήσκεται και το αρχείο Toublakia.jar που εκκινήσατε για να παίξετε το παιχνίδι.\nΣε αυτόν τον φάκελο λοιπόν, απλά προσθέστε όποια αρχεία ήχου μορφής .wav θέλετε.\nΤην επόμενη φορά που θα εκκινήσετε το παιχνίδι τα τραγούδια θα είναι διαθέσημα για αναπαραγωγή!");
        }
        
        if(evt.getActionCommand() == "Τα πλήκτρα του παιχνιδιού"){
            JOptionPane.showMessageDialog(null, "Πάνω βέλος:         Περιστροφή τούβλου\nΑριστερό βέλος:   Μετακίνηση τούβλου αριστερά\nΔεξί βέλος:             Μετακίνησητούβλου δεξιά\nΚάτω βέλος:          Πτώση τούβλου κατά μία σειρά\nΠλήκτρο P/Π:        Παύση παιχνιδιού\nΠλήκτρο Pause:    Παύση παιχνιδιού");
        }
        
        if(evt.getSource()==ThemeSelect){
            
            for(int i=0; i<MusicThemes.length; i++){
                if(((String)((JComboBox)(evt.getSource())).getSelectedItem()) == MusicThemes[i]){
                    ToublakiaGame.themePlayer.Stop();
                    ToublakiaGame.MusicTheme = ToublakiaGame.MusicThemes[i];
                    
                    if(noMusic == false){
                        ToublakiaGame.themePlayer.Run(System.getProperty("user.dir")+"/"+folderPath+"/"+MusicTheme ,true);
                    }
            }
        }
        }

    }
    
      
    public void keyPressed(KeyEvent evt){
        
        int code = evt.getKeyCode();
                    
        if(ToublakiaGame.paused == false){
            
            if(code == 37){
                ToublakiaPane.PlayingBrick.Left();
                repaint();
            }

            if(code == 38){
                ToublakiaPane.PlayingBrick.Rotate();
                repaint();
            }
            
            if(code == 39){
                ToublakiaPane.PlayingBrick.Right();
                repaint();
            }
            
            if(code == 40){
                ToublakiaPane.PlayingBrick.Drop();
                repaint();
            }
        }
            
        if(code == 80 || code == 19){
  
            ToublakiaGame.paused ^= true;

            if(ToublakiaGame.paused == true){
                PlayGame.setText("Παίξε να γουστάρεις");
                ToublakiaGame.pausedString = "Κάνε δουλειά σου.....";
                
                if(MusicThemes!=null){
                    PlayMusic.setText("Παίξε μουσικούλα");
                    ToublakiaGame.themePlayer.Stop();
                }
            }
                
            if(ToublakiaGame.paused == false){
                PlayGame.setText("Περιμενε ρε!");
                ToublakiaGame.pausedString = "";
                    
                if(MusicThemes!=null)
                    if(noMusic == false){
                        ToublakiaGame.themePlayer.Run(System.getProperty("user.dir")+"/"+folderPath+"/"+MusicTheme ,true);
                        PlayMusic.setText("Όχι μουσικούλα");
                    }
            }
            repaint();
        }
    }
            
    public void keyReleased(KeyEvent evt){}
                
    public void keyTyped(KeyEvent evt){}
    
    public  static void ShowScores(){
        
        String[] ReadNames = new String[10];
        Arrays.fill(ReadNames, "");
        
        int[] ReadScores = new int[10];
        Arrays.fill(ReadScores, 0);
        
        int[] ReadLines = new int[10];
        Arrays.fill(ReadLines, 0);
               
        ReadScore(ReadNames, ReadScores, ReadLines);
        
        ScoreFrame score = new ScoreFrame(ReadNames, ReadScores, ReadLines);

    }
    
    public static int CheckScore(){
        
        int ReturnVal=11;
        
        String[] ReadNames = new String[10];
        Arrays.fill(ReadNames, "");
        
        int[] ReadScores = new int[10];
        Arrays.fill(ReadScores, 0);
        
        int[] ReadLines = new int[10];
        Arrays.fill(ReadLines, 0);
               
        ReadScore(ReadNames, ReadScores, ReadLines);
        
        for(int i=9; i>=0; i--){
            if(ToublakiaGame.Score == ReadScores[i])
                if(ToublakiaGame.Lines > ReadLines[i])
                    ReturnVal = i;
                
            if(ToublakiaGame.Score > ReadScores[i])
                ReturnVal = i;
            }
                
        return ReturnVal;
    }
    
    public static void UpdateScores(int place){
        
        String PlayerName = "Νέα Παιχτούρα";
               
        String[] ReadNames = new String[10];
        Arrays.fill(ReadNames, "");
        
        int[] ReadScores = new int[10];
        Arrays.fill(ReadScores, 0);
        
        int[] ReadLines = new int[10];
        Arrays.fill(ReadLines, 0);
               
        ReadScore(ReadNames, ReadScores, ReadLines);
               
        PlayerName = JOptionPane.showInputDialog(null, "Συγχαριτήρια!!!\nΕίσαι στους 10 καλύτερους παίχτες :)\nΔώσε το όνομά σου:", "Νέα Παιχτούρα");
            if(PlayerName == null)
                PlayerName = "Νέα Παιχτούρα";

        if(place<9)
            for(int i=8; i>=place; i--){
                ReadNames[i+1] = ReadNames[i];
                ReadScores[i+1] = ReadScores[i];
                ReadLines[i+1] = ReadLines[i];
            }
        
        ReadNames[place] = PlayerName;
        ReadScores[place] = ToublakiaGame.Score;
        ReadLines[place] = ToublakiaGame.Lines;
        
        for(int i=0; i<10; i++)
            WriteScore(ReadNames[i], ReadScores[i], ReadLines[i], (i!=0));
            
        ShowScores();
        
    }
    
    public static void ReadScore(String[] ReadStr, int[] ReadScore, int[] ReadLines){

        try {
            FileInputStream file = new FileInputStream("TopScores.dat");
            BufferedInputStream buff = new BufferedInputStream(file);
            DataInputStream data = new DataInputStream(buff);
                
            int in, i=0;
                while(true){
                    try {
                        while((in = data.readInt())!=(int)'\t'){
                            ReadStr[i]+=String.valueOf((char)in);
                        }
                    }catch (EOFException eof) {
                        buff.close();
                        return;
                    }
            
                    try {
                        while((in = data.readInt())!=(int)'\t'){
                            ReadScore[i] = in;
                        }
                    }catch (EOFException eof) {
                        buff.close();
                        return;
                    }
                    
                    try {
                        while((in = data.readInt())!=(int)'\t'){
                            ReadLines[i] = in;
                        }
                    }catch (EOFException eof) {
                        buff.close();
                        return;
                    }
                    i++;
                }
            }catch(FileNotFoundException fnfe){
                
                for(int i=0; i<10; i++)
                    WriteScore("Παιχτούρα", 0, 0, (i!=0));

                    ReadScore(ReadStr, ReadScore, ReadLines);
            }catch (IOException ioe) {
                System.out.println("Error -- " + ioe.toString());
                return;
            }
    }
    
    
    public static void WriteScore(String str, int score, int lines, boolean append) {
        char[] str2c = str.toCharArray();

        try {
            // Write output to disk
            FileOutputStream file = new FileOutputStream("TopScores.dat", append);
            BufferedOutputStream buff = new BufferedOutputStream(file);
            DataOutputStream data = new DataOutputStream(buff);

            for (int i = 0; i < str2c.length; i++)
               data.writeInt((int)str2c[i]);
               
            data.writeInt((int)'\t');
            data.writeInt(score);
            data.writeInt((int)'\t');
            data.writeInt(lines);
            data.writeInt((int)'\t');
            
            data.close();
        }catch (IOException ioe) {
                System.out.println("Error -- " + ioe.toString());
            return;
        }
    }
    
    public static String[] ListMusic(File folder) {

        // Directory path here
        String file;
        File[] listOfFiles = folder.listFiles();
        
        if(listOfFiles.length == 0)
            return null;
            
        else{
            String[] listOfMusicFiles = new String[listOfFiles.length];
 
            int j=0;
        
            for (int i = 0; i <listOfFiles.length ; i++){
                if (listOfFiles[i].isFile()){
                    file = listOfFiles[i].getName();
                    if (file.endsWith(".wav") || file.endsWith(".WAV")){
                        listOfMusicFiles[j] = file;
                        j++;
                    }
                }
            }
            
            String[] returnArr = new String[j];
            System.arraycopy(listOfMusicFiles, 0, returnArr, 0, j);
            return returnArr;
        }
    }
}