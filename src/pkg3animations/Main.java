package pkg3animations;

import com.sun.tracing.dtrace.ArgsAttributes;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        DisplayMode dm = new DisplayMode(800, 600, 16, DisplayMode.REFRESH_RATE_UNKNOWN); //1: screen resolustion 2: bit depth 16 bit or colors is faster than 32 4:refresh rate(How many times the screen refreshes every second.. If you don't know this use DisplayMode.REFRESH_RATE_UNKNOWN)
        Main b = new Main();
        b.run(dm);
    }
    
    private Screen screen; //Our full screen
    private Image bg; //background image
    private Animation a; //has access to our animation class
    
    //LOADPIC METHOD.... load pics to var so we can use them in game
    public void loadPics(){
        bg = new ImageIcon("C:\\java\\newBoston\\advanced\\CreatingAScreen\\src\\creatingascreen\\background.jpg").getImage();
        Image face1 = new ImageIcon("C:\\java\\newBoston\\advanced\\CreatingAScreen\\src\\creatingascreen\\smiley.jpg").getImage();
        Image face2 = new ImageIcon("C:\\java\\newBoston\\advanced\\CreatingAScreen\\src\\creatingascreen\\wink.jpg").getImage();
        
        a = new Animation();
        
        //add pics to scene
        a.addScene(face1, 250);
        a.addScene(face2, 250);
    }
    
    //RUN METHOD.. main engine to run
    public void run(DisplayMode dm){
        screen = new Screen();
        try{
            screen.setFullScreen(dm, new JFrame());
            loadPics();
            movieLoop(); //Going to loop through and play movie
        }finally{
            screen.restoreScreen(); //After movie restore screen
        }
    }
    
    //MAIN MOVIE LOOP METHOD.. goes from pic to pic
    public void movieLoop(){
        long startingTime = System.currentTimeMillis(); //This gets the current time from your computer
        long cumTime = startingTime; //cummilutivet time it's been running for
        while(cumTime - startingTime <5000){
            long timePassed = System.currentTimeMillis() - cumTime;
            cumTime += timePassed;
            a.update(timePassed); //passes time passed to the update method of the snimation class which keeps track o how long the movie has been playing and if this is more than the time it should be playing it restarts the animation.
            
            Graphics g = screen.getFullScreenWindow().getGraphics(); //creating a graphics object from our screen class.
            draw(g);
            g.dispose(); //good house keeping to dispose of our graphics object after we are done with it
            
            try{
                Thread.sleep(20);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    //DRAW METHOD .. draws the graphic to the screen
    public void draw(Graphics g){
        g.drawImage(bg,0,0,screen.getFullScreenWindow().getWidth(),screen.getFullScreenWindow().getHeight(),0,0,bg.getWidth(null),bg.getHeight(null),null);
        g.drawImage(a.getImage(), 800, 500, null); //getImage() gets the image of the current Animation 
    }
    
}
