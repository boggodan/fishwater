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
void setup()
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

void draw()
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
  textArea.strokeWeight(1.0);
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

void mousePressed()
{
  //droplets.add(new Droplet(mouseX, mouseY));
}

void doWater()
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

void resampleImage(PImage inImage)
{
  inImage.resize(150,150);
  inImage.filter(THRESHOLD);
}


void keyPressed() {
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
