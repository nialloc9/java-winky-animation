package pkg3animations;

import java.awt.*;
import javax.swing.JFrame;

/*
There are esentially 3 types of games you can make in java. A windowed game that a window appears. An applet game. A full screen game.
*/
public class Screen {
    private GraphicsDevice vc; //This is basically an interface to the video/graphics card. It allows us access the graphics/vidoe card with our vc object.
    //The grahics card controls what goes on our screen so if we control it with the vc object we control what goes on the screen.
    
    public Screen(){
        /*
        To use the vc object we need to go through the graphics environment. This environment is a collection of all our graphics device objects.
        This is all our graphics objects not just our screen.
        */
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        vc = env.getDefaultScreenDevice(); //Look here we gor our local graphics environment from our GraphicsEnvironment and we then got the default screen that the computer uses.
        //Now we have access to the entire monitor(the default screen) and instead of just a window we can use the full screen.
    }
    
    public void setFullScreen(DisplayMode dm, JFrame window){ //Takes 2 parameters
        /*
        DisplayMode takes 4 parameters 1&2: resolution(x and y) 3:bit-depth(How many colors that you can store) 4:refresh rate(How many times your monitor refreshes itself every second)
        We are going to create a JFrame window and convert it to a full screen.
        */
        
        window.setUndecorated(true); //We don't want any titles bars or scroll bars or anything like that so we set undecorated to true. Gets rid of pretty much everything except what is inside the JFrame.
        window.setResizable(false); //User can't resize the window. So if the screen is full screen user can't make it smaller
        vc.setFullScreenWindow(window); //Ths is a built in method that we can use because we have control of the graphics card which gave use control of the localEnvironment which gave us control of the default screen so we can make this full screen.
        
        if(dm != null && vc.isDisplayChangeSupported()){ //Check if there are monitor settings in the DisplayMode and if the graphics card allows us to change the monitor settings
           try{
               vc.setDisplayMode(dm); //Change the displayMode settings to the the new displayMode
           }catch(Exception ex){
               ex.printStackTrace();
           }
        }
    }
    
    public Window getFullScreenWindow(){
        return vc.getFullScreenWindow(); //returns our window to the screen
    }
    
    public void restoreScreen(){
        Window w = vc.getFullScreenWindow(); //Gets the window
        
        if(w != null){
            w.dispose(); //Good practice wwhen closing a window to dispose of the other one.
        }
        
        vc.setFullScreenWindow(null); //When we pass null as the parameter it makes sure the window is no longer in full screen.
    }
}
