class Droplet
{
  
  PVector pos, vel;
  
  Droplet(float x, float y)
  {
    pos = new PVector(x+random(2),y);
    vel = new PVector(0,3+random(0.2));
  }
  
  void draw()
  {
    if(pos.y<displayHeight)
    {
    fishwater.fill(100,168,189,200);
    fishwater.noStroke();
    fishwater.ellipse(pos.x,pos.y,5*2,vel.y);
    }
  }
  
  void process()
  {
    pos.add(vel);
    vel.add(new PVector(0.0,0.8));
  }
}
