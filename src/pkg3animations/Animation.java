package pkg3animations;

import java.awt.Image; //Superclass of all graphics
import java.util.ArrayList;

public class Animation {
    /*
    There is 2 types of animation. Still(not moving) and dynamic(moving) animation.
    */
    private ArrayList scenes;
    private int sceneIndex; //Index variable for the elements in the scenes ArrayList
    private long movieTime; //How long the animation is running. This will be comared against totalTime so if movieTime exceeds totalTime we will restart the animation.
    private long totalTime; //Total time animation is allowed run. 
    
    //CONSTRUCTOR.. set everything to 0 and start object
    public Animation(){
        //set everything to 0 before starting. This is because this constructor will be called over and over as the animation restarts.
        scenes = new ArrayList();
        totalTime = 0;
        start();
    }
    
    //ADDSCENE METHOD SYNCHRONIZED.. add scene to ArrayList and set time for each scene
    public synchronized void addScene(Image i, long t){ //The image and the time the image will appear for
         //Remember if one thread is in the addScene it locks the object so no other thread can.
         totalTime += t; //must add to totalTime because it is the total of all the image show times
         scenes.add(new oneScene(i, totalTime)); //We are going to create a new oneScene object that takes an image and the totalTime as arguements and add it to the ArrayList
         
    }
    
    //START METHOD SYNCHRONIZED... start animation from beginning. make sure everything is set to 0.
    private synchronized void start(){
        movieTime = 0; 
        sceneIndex = 0;
    }
    
    //UPDATE METHOD SYNCHRONIZED
    public synchronized void update(long timePassed){ //timePassed is the time passed from last update
        if(scenes.size() > 1){ //size() returns size of ArrayList. We only want to run this code if there is more than one picture.
            movieTime += timePassed; //Add the timePassed since last update to the movieTime
            if(movieTime >= totalTime){ //If the movieTime is greater than the totalTime we want to restart the animation or else the last pic in the movie would just stay static
                movieTime = 0; //When
                sceneIndex = 0;
            }
            while(movieTime > getScene(sceneIndex).endTime){ //when we get to the end of the scene go to the next picture
                sceneIndex++; 
            }
        }
    }
    
    //GETIMAGE METHOD.. get animations current scene(aka image)
    public synchronized Image getImage(){
        if(scenes.size() ==0){ //if no scenes
            return null;
        }else{
            return getScene(sceneIndex).pic; //get the pic from the scene
        }
    }
    
    //GETSCENE
    private oneScene getScene(int x){
       return (oneScene)scenes.get(x); //get the schene from the ArrayList
    }
    
    ///// PRIVATE INNER CLASS ONESCENE.. creates an object for the image //////
    private class oneScene{
        Image pic;
        long endTime;

        //constructor
        public oneScene(Image pic, long endTime) {
            this.pic = pic;
            this.endTime = endTime;
        }
    }
}
