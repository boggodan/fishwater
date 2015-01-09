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
                visibility *= 0.9999999;
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
