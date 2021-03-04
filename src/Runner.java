import java.awt.*;

public class Runner {
    public static void main(String [] args)
    {
        //Initializing Variables
        StdDraw.setCanvasSize(600,600);
        StdDraw.setXscale(0,10);
        StdDraw.setYscale(0,10);
        StdDraw.setPenColor(Color.BLACK);
        double radius = 3;
        double arcDist = -10;
        double width = 1;
        int numWeights = 2;
        int maxEpoch = 100;
        double learningRate = 0.05;
        double maxError = 0;
        double w1 = Math.random() * 0.5 - 0.25;
        double w2 = Math.random() * 0.5 - 0.25;
        Point[] points = new Point[2000];
        double[] outputs = new double[2000];

        String response = "Y";
        String gate = "a";
        String learnRate = "0";
        String dist = "-10";
        String wide = "0";
        String rad = "0";
        int errorResponse = 0;
        while(response.toUpperCase().equals("Y"))
        {
            response = "Y";
            gate = "a";
            learnRate = "0";
            dist = "-10";
            wide = "0";
            rad = "0";
            errorResponse = 0;

            //Learning Rate
            while(!(Double.parseDouble(learnRate) > 0 && Double.parseDouble(learnRate) <= 0.1 && Double.parseDouble(learnRate) != 1)) {
                errorResponse = 1;
                if (errorResponse == 1) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Please enter in a double value for the learning rate between 0 and 0.1 inclusive (not 1)");
                }
                try {
                    errorResponse = 0;
                    learnRate = javax.swing.JOptionPane.showInputDialog(null, "Please enter in a learning rate between 0 and 0.1 inclusive (not 1)");
                    Double testRate = Double.parseDouble(learnRate);
                } catch (Exception e) {
                    errorResponse = 1;
                    learnRate = "0";
                }
            }
            learningRate = Double.parseDouble(learnRate);

            //Distance between half moons
            while(!(Double.parseDouble(dist) >= -1.5 && Double.parseDouble(dist) <= 1.5)) {
                errorResponse = 1;
                dist = "0";
                if (errorResponse == 1) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Please enter in a double value for the distance between the two half moons between -1.5 and 1.5 inclusive");
                }
                try {
                    errorResponse = 0;
                    dist = javax.swing.JOptionPane.showInputDialog(null, "Please enter in a distance between -1.5 and 1.5 inclusive");
                    Double testRate = Double.parseDouble(dist);
                } catch (Exception e) {
                    errorResponse = 1;
                    dist = "0";
                }
            }
            arcDist = Double.parseDouble(dist)/2;

            //Radius
            while(!(Double.parseDouble(rad) >= 1 && Double.parseDouble(rad) <= 3)) {
                errorResponse = 1;
                rad = "0";
                if (errorResponse == 1) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Please enter in a double value between 1 and 3 inclusive for the radius of the half moons");
                }
                try {
                    errorResponse = 0;
                    rad = javax.swing.JOptionPane.showInputDialog(null, "Please enter in a radius between 1 and 3 inclusive");
                    Double testRate = Double.parseDouble(rad);
                } catch (Exception e) {
                    errorResponse = 1;
                    rad = "0";
                }
            }
            radius = Double.parseDouble(rad);


            //Width
            while(!(Double.parseDouble(wide) >= 0.5 && Double.parseDouble(wide) <= 1.5 && Double.parseDouble(wide) <= radius)) {
                errorResponse = 1;
                wide = "0";
                if(errorResponse == 1)
                {
                    javax.swing.JOptionPane.showMessageDialog(null, "Please enter in a double value between 0.5 and 1.5 inclusive for the width of the half moons (Has to be less than radius)");
                }
                try
                {
                    errorResponse = 0;
                    wide = javax.swing.JOptionPane.showInputDialog(null, "Please enter in a width between 0.5 and 1.5 inclusive (Less than radius)");
                    Double testRate = Double.parseDouble(wide);
                }
                catch(Exception e)
                {
                    errorResponse = 1;
                    wide = "0";
                }
            }
            width = Double.parseDouble(wide);

            int shuffle = 1;
            //Initializing Training Set Points
            for(int i = 0; i < 2000; i++)
            {
                if(shuffle == 1)
                {
                    Double ang = Math.random()*180;
                    Double magnitude = Math.random()*width+(radius-width);
                    double x = magnitude*Math.cos(Math.toRadians(ang));
                    double y = magnitude*Math.sin(Math.toRadians(ang))+arcDist;
                    points[i] = new Point(x,y, "TOP");
                    outputs[i] = 1;
                    shuffle = 0;
                }
                else
                {
                    Double ang = Math.random()*180+180;
                    Double magnitude = Math.random()*width+(radius-width);
                    double x = magnitude*Math.cos(Math.toRadians(ang));
                    double y = magnitude*Math.sin(Math.toRadians(ang))-arcDist;
                    points[i] = new Point(x,y, "BOTTOM");
                    outputs[i] = 0;
                    shuffle = 1;
                }
            }

            //Train Perceptron
            Perceptron halfMoon = new Perceptron(numWeights,learningRate,maxEpoch,maxError,w1,w2,points,outputs,radius,width,arcDist);
            halfMoon.train();

            //Initializing Testing Set
            Point[] testPoints = new Point[2000];
            for(int i = 0; i < 1000; i++)
            {
                Double ang = Math.random()*180;
                Double magnitude = Math.random()*width+(radius-width);
                double x = magnitude*Math.cos(Math.toRadians(ang));
                double y = magnitude*Math.sin(Math.toRadians(ang))+arcDist;
                testPoints[i] = new Point(x,y, "UNK");
            }
            for(int i = 1000; i < 2000; i++)
            {
                Double ang = Math.random()*180+180;
                Double magnitude = Math.random()*width+(radius-width);
                double x = magnitude*Math.cos(Math.toRadians(ang));
                double y = magnitude*Math.sin(Math.toRadians(ang))-arcDist;
                testPoints[i] = new Point(x,y, "UNK");
            }

            //Testing Set
            halfMoon.test(testPoints);
            response = javax.swing.JOptionPane.showInputDialog(null, "Would you like to test another gate? \nNote: Enter \"Y\" for \"Yes\"");
        }
    }
}