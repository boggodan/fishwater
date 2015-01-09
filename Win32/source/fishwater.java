import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class fishwater extends PApplet {

ArrayList<Droplet> droplets;

float startLevel = 0;
PImage image;
int cursorBlink=0;
int currentRow = 0;

PFont f;
int nTextLines = 1;
// Variable to store text currently being typed
String typing = "";

// Variable to store saved text when return is hit
String saved = "";
PGraphics textImage;
PGraphics fishwater;
PGraphics textArea;
public void setup()
{
  size(800,800);
  frameRate(60);
  fishwater = createGraphics(800,800);
  
  image = loadImage("fishtext.png");
  droplets = new ArrayList<Droplet>();
  resampleImage(image);
  currentRow=image.height-1;
  fishwater.image(image,0,0);
  f = createFont("Helvetica-Bold",100,true);
  
  textImage = createGraphics(800,800);
  textImage.beginDraw();
  textImage.background(255);
  textImage.endDraw();
  
  textArea = createGraphics(150,150);
}

public void draw()
{
  background(0);
  fishwater.beginDraw();
  fishwater.background(50,52,55);
  for (Droplet d: droplets){
  d.process();
  d.draw();
  }
  doWater();

  fishwater.endDraw();
  image(fishwater,0,0);
  
  textArea.beginDraw();
  //image(textImage,0,0);
  textArea.background(0,0,0,50);
  textArea.strokeWeight(1.0f);
  textArea.stroke(255,255,255,50);
  String theText;
      if(cursorBlink<25)
       theText = typing + " |";
      else 
      theText = typing;
  // Display everything
  textArea.fill(255);
  textArea.textFont(f,20);
  //text("Type some text, then hit return to display it. ", 25, 15);
   textArea.text(theText,10,25);
   textArea.endDraw();
   
   image(textArea,0,650);
   fill(255);
   text("Type Here",47,640);
   cursorBlink++;
   if(cursorBlink==50)
   cursorBlink=0;

}

public void mousePressed()
{
  //droplets.add(new Droplet(mouseX, mouseY));
}

public void doWater()
{
  for (int x = 0; x < image.width; x++){
    // Use the formula to find the 1D location
    int loc = x + currentRow * image.width;
      if(textImage.pixels[loc] != -1){
        droplets.add(new Droplet(x*7,startLevel));
      }
  }
  
  currentRow--;
  if(currentRow==0) currentRow=image.height-1;
}

public void resampleImage(PImage inImage)
{
  inImage.resize(150,150);
  inImage.filter(THRESHOLD);
}


public void keyPressed() {
  // If the return key is pressed, save the String and clear it
  if (key == '\n' ) {
    // A String can be cleared by setting it equal to ""
    typing = typing + '\n'; 
    
  } else {
    // Otherwise, concatenate the String
    // Each character typed by the user is added to the end of the String variable.
    typing = typing + key; 
    
 
  }

    if(key == BACKSPACE){
      
     if(typing.length()>1)
     typing = typing.substring(0,typing.length()-2);
  }  
  
   //regenerate text image
   

   
    textImage = createGraphics(800,800);
    textImage.beginDraw();
    textImage.background(255);
    textImage.fill(0);
    textImage.textFont(f,100);
    textImage.text(typing,10,200);
    textImage.endDraw();
    
    textImage.resize(150,150);
    textImage.filter(THRESHOLD);
}
//import javawt.Color;
//import javutil.*;

//import org.apache.tools.ant.taskdefs.LoadFile;

public class Console {

    Timer time;
    int x, y;
    int w, h;
    PFont font;
    int fsize;
    int clr;

    ArrayList messages;

    float visibility;

    public Console(int x, int y, int w, int h) {
            time = new Timer(4000);
            visibility = 0;
            font = loadFont("SegoeUI-48.vlw");

            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            fsize = displayHeight / 20;
            messages = new ArrayList();
            clr = color(255, 255, 255);
        }
        /**
         * print a black line of text
         * @param s
         */
    public void println(String s) {
        messages.add(new ConsoleMessage(s, color(0, 0, 0, 255)));
        visibility = 1.0f;
        time.start();
    }

    /**
     * print a clred line of text
     * @param s
     * @param clr
     */
    public void println(String s, int clr) {
        messages.add(new ConsoleMessage(s, clr));
        visibility = 1.0f;
        time.start();
    }

    /**
     * draw the console
     */
    public void draw() {

        if (time.isFinished()) {
            if (visibility >= 0)
                visibility *= 0.9999999f;
        }


        textFont(font, 20);

        for (int i = messages.size() - 1; i > 0; i--) {
            ConsoleMessage m = (ConsoleMessage) messages.get(i);
            fill(red(m.clr), green(m.clr), blue(m.clr), (float)(255 - (messages.size() - i) * 20) * visibility);

            text(m.message, 20, y + h - (messages.size() - i) * 20);



        }


    }

    /**
     * parses and processes console commands.
     * In some cases it might add messages to the console.
     * @param text
     */
    public void processCommand(String text) {

        if (text.startsWith("/")) {
            String[] command;
            command = text.split(" ");

            if (command[0].equals("/ping")) {
                if (command.length < 2)
                        println("no");
                else {
                    if (command[1].equals("-server"))
                        println("no");
                    else
                    if (command[1].equals("-all"))
                        println("no");
                }

            } else noSuchCommandError();

        }
    }

    /**
     * informs the user that they entered a bad command
     */
    public void noSuchCommandError() {
        println("No such command.", color(255, 0, 0));
    }

}
public class ConsoleMessage 
{
  String message;
  int clr;
 public  ConsoleMessage(String message, int clr)
  {
    this.message = message;
    this.clr = clr;
  }
}
class Droplet
{
  
  PVector pos, vel;
  
  Droplet(float x, float y)
  {
    pos = new PVector(x+random(2),y);
    vel = new PVector(0,3+random(0.2f));
  }
  
  public void draw()
  {
    if(pos.y<displayHeight)
    {
    fishwater.fill(100,168,189,200);
    fishwater.noStroke();
    fishwater.ellipse(pos.x,pos.y,5*2,vel.y);
    }
  }
  
  public void process()
  {
    pos.add(vel);
    vel.add(new PVector(0.0f,0.8f));
  }
}

public class Timer{
   
    int savedTime; // When Timer started
    int totalTime; // How long Timer should last
    Timer(int tempTotalTime) {
      totalTime = tempTotalTime;
    }
    
    // Starting the timer
    public void start() {
      // When the timer starts it stores the current time in milliseconds.
      savedTime = millis(); 
    }
    
    // The function isFinished() returns true if 5,000 ms have passed. 
    // The work of the timer is farmed out to this method.
    public boolean isFinished() { 
      // Check how much time has passed
      int passedTime = millis()- savedTime;
      if (passedTime > totalTime) {
        return true;
      } else {
        return false;
      }
    }
  }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "fishwater" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
