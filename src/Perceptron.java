import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Perceptron
{
    private Point[] inputs;
    private double[] weights, output;
    private double theta, n, w1, w2, maxError, arcRadius, arcWidth, arcDist;
    private int numW, maxEpoch;

    public Perceptron(int numWeights,double learningRate,int maxIterator,double maxDiff,double weight1,double weight2,Point[] samples,double[] outputs,double radius,double width,double dist)
    {
        //Initializing Variables
        StdDraw.setCanvasSize(600,600);
        StdDraw.setXscale(0,10);
        StdDraw.setYscale(0,10);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.enableDoubleBuffering();
        Font font = new Font("Sans Serif", Font.BOLD, 25);
        StdDraw.setFont(font);
        n = learningRate;
        maxEpoch = maxIterator;
        maxError = maxDiff;
        w1 = weight1;
        w2 = weight2;
        inputs = samples;
        output = outputs;
        numW = numWeights;
        theta = 1;
        arcRadius = radius;
        arcWidth = width;
        arcDist = dist;

        //Draw Training Set Points
        drawAxis();
        StdDraw.text(5,9.5,"Initializing Training Set:");
        for(int i = 0; i < inputs.length; i++)
        {
            if(inputs[i].getTag().equals("TOP"))
            {
                StdDraw.setPenColor(Color.BLUE);
                StdDraw.filledCircle(inputs[i].getX()+(10-(arcRadius-(arcWidth/2)+arcRadius*2))/2+arcRadius,inputs[i].getY()+5,0.05);
            }
            else if(inputs[i].getTag().equals("BOTTOM"))
            {
                StdDraw.setPenColor(Color.RED);
                StdDraw.filledCircle(inputs[i].getX()+(10-(arcRadius-(arcWidth/2)+arcRadius*2))/2+arcRadius-(arcWidth/2)+arcRadius,inputs[i].getY()+5,0.05);
            }
            StdDraw.show(1);
        }
    }

    public void train()
    {
        int iterator = 1;
        double[] tempOutput = new double[output.length];
        double totalError = 1;

        //Training Formula
        while(iterator < maxEpoch && totalError != 0)
        {
            StdDraw.clear();
            drawAxis();
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.text(5,9.5,"Training:");
            for(int i = 0; i < inputs.length; i++)
            {
                if(inputs[i].getTag().equals("TOP"))
                {
                    StdDraw.setPenColor(Color.BLUE);
                    StdDraw.filledCircle(inputs[i].getX()+(10-(arcRadius-(arcWidth/2)+arcRadius*2))/2+arcRadius,inputs[i].getY()+5,0.05);
                }
                else if(inputs[i].getTag().equals("BOTTOM"))
                {
                    StdDraw.setPenColor(Color.RED);
                    StdDraw.filledCircle(inputs[i].getX()+(10-(arcRadius-(arcWidth/2)+arcRadius*2))/2+arcRadius-(arcWidth/2)+arcRadius,inputs[i].getY()+5,0.05);
                }
            }
            StdDraw.show();
            totalError = 0;
            for (int i = 0; i < inputs.length; i++)
            {
                double target = output[i];
                double sampleOutput = activate(inputs[i]);
                double error = (target - sampleOutput);
                double deltaW = n * error * inputs[i].getX();
                w1 += deltaW;
                double deltaW1 = n * error * inputs[i].getY();
                w2 += deltaW1;
                double deltaTheta = n * error * (-1);
                theta += deltaTheta;
                tempOutput[i] = sampleOutput;
                totalError += Math.abs(error);
            }

            //Draw Temp Best Fit Line & Print Epochs/Error
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.filledRectangle(5,9,2,0.2);
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.text(3,9,"Epoch: " + iterator);
            StdDraw.text(7,9,"Error: " + totalError);
            //System.out.println("Epoch: " + iterator);
            //System.out.println("Error: " + totalError);
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.line(0,(-w1*((0-5)/w2)+theta/w2)+5,10,(-w1*((10-5)/w2)+theta/w2)+5);
            StdDraw.show(5);
            iterator++;
        }

        //Draw Best Fit Line
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.filledRectangle(5,9.5,3,0.2);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(5,9.5,"Found Line of Best Fit:");
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(Color.RED);
        StdDraw.line(0,(-w1*((0-5)/w2)+theta/w2)+5,10,(-w1*((10-5)/w2)+theta/w2)+5);
        StdDraw.setPenRadius();
        StdDraw.show(2000);
    }

    public void test(Point[] testSet)
    {
        //Draw Testing Set Points
        StdDraw.clear();
        drawAxis();
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(5,9.5,"Initializing Test Set:");
        StdDraw.show();
        for(int i = 0; i < testSet.length-1000; i++)
        {
            StdDraw.filledCircle(testSet[i].getX()+(10-(arcRadius-(arcWidth/2)+arcRadius*2))/2+arcRadius,testSet[i].getY()+5,0.05);
            StdDraw.show(1);
        }
        for(int i = 1000; i < testSet.length; i++)
        {
            StdDraw.filledCircle(testSet[i].getX()+(10-(arcRadius-(arcWidth/2)+arcRadius*2))/2+arcRadius-(arcWidth/2)+arcRadius,testSet[i].getY()+5,0.05);
            StdDraw.show(1);
        }

        //Draw Best Fit Line
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.filledRectangle(5,9.5,3,0.2);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(5,9.5,"Line of Best Fit:");
        StdDraw.setPenColor(Color.RED);
        StdDraw.setPenRadius(0.01);
        StdDraw.line(0,(-w1*((0-5)/w2)+theta/w2)+5,10,(-w1*((10-5)/w2)+theta/w2)+5);
        StdDraw.show(2000);
        StdDraw.setPenRadius();

        //Draw Activated Points (Top & Bottom)
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.filledRectangle(5,9.5,3,0.2);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(5,9.5,"Activating Points:");
        StdDraw.show();
        for(int i = 0; i < testSet.length-1000; i++)
        {

            if(activate(testSet[i]) == 1)
            {
                StdDraw.setPenColor(Color.BLUE);
                StdDraw.filledCircle(testSet[i].getX()+(10-(arcRadius-(arcWidth/2)+arcRadius*2))/2+arcRadius,testSet[i].getY()+5,0.05);
            }
            else
            {
                StdDraw.setPenColor(Color.RED);
                StdDraw.filledCircle(testSet[i].getX()+(10-(arcRadius-(arcWidth/2)+arcRadius*2))/2+arcRadius,testSet[i].getY()+5,0.05);
            }

            /*
            if(testSet[i].getY() > (-w1*((testSet[i].getX()-(10-(arcRadius-(arcWidth/2)+arcRadius*2))/2)/w2)+theta/w2))
            {
                StdDraw.setPenColor(Color.BLUE);
                StdDraw.filledCircle(testSet[i].getX()+(10-(arcRadius-(arcWidth/2)+arcRadius*2))/2+arcRadius,testSet[i].getY()+5,0.05);
            }
            else
            {
                StdDraw.setPenColor(Color.RED);
                StdDraw.filledCircle(testSet[i].getX()+(10-(arcRadius-(arcWidth/2)+arcRadius*2))/2+arcRadius,testSet[i].getY()+5,0.05);
            }
            */
            StdDraw.show(1);

        }
        for(int i = 1000; i < testSet.length; i++)
        {

            if(activate(testSet[i]) == 1)
            {
                StdDraw.setPenColor(Color.BLUE);
                StdDraw.filledCircle(testSet[i].getX()+(10-(arcRadius-(arcWidth/2)+arcRadius*2))/2+arcRadius-(arcWidth/2)+arcRadius,testSet[i].getY()+5,0.05);
            }
            else
            {
                StdDraw.setPenColor(Color.RED);
                StdDraw.filledCircle(testSet[i].getX()+(10-(arcRadius-(arcWidth/2)+arcRadius*2))/2+arcRadius-(arcWidth/2)+arcRadius,testSet[i].getY()+5,0.05);
            }

            /*
            if(testSet[i].getY() > (-w1*((testSet[i].getX()+(10-(arcRadius-(arcWidth/2)+arcRadius*2))/2)/w2)+theta/w2))
            {
                StdDraw.setPenColor(Color.BLUE);
                StdDraw.filledCircle(testSet[i].getX()+(10-(arcRadius-(arcWidth/2)+arcRadius*2))/2+arcRadius-(arcWidth/2)+arcRadius,testSet[i].getY()+5,0.05);
            }
            else
            {
                StdDraw.setPenColor(Color.RED);
                StdDraw.filledCircle(testSet[i].getX()+(10-(arcRadius-(arcWidth/2)+arcRadius*2))/2+arcRadius-(arcWidth/2)+arcRadius,testSet[i].getY()+5,0.05);
            }
            */
            StdDraw.show(1);
        }

        //Saving Weights/Theta
        String response = javax.swing.JOptionPane.showInputDialog(null, "Would you like to save the weights & theta? \nNote: Enter \"Y\" for \"Yes\"");
        if(response.toUpperCase().equals("Y"))
        {
            try
            {
                write();
            }
            catch(Exception e)
            {
                System.out.println("");
            }
        }
    }

    //Activate values
    public double activate(Point point)
    {
        if(point.getX()*w1 + point.getY()*w2 > theta)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    public void drawAxis()
    {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.line(0.5,0.5,0.5,9.5);
        StdDraw.line(0.5,0.5,9.5,0.5);
        StdDraw.line(0.4,9,0.5,9.5);
        StdDraw.line(0.6,9,0.5,9.5);
        StdDraw.line(9,0.4,9.5,0.5);
        StdDraw.line(9,0.6,9.5,0.5);
        for(int i = 1; i < 10; i++)
        {
            StdDraw.line(0.4,i,0.6,i);
            StdDraw.text(0.2,i,"" + i);
            StdDraw.line(i,0.4,i,0.6);
            StdDraw.text(i,0.1,""+ i);
        }
        StdDraw.setPenRadius();
    }

    public void write() throws IOException
    {
        String str = "Weight 1: " + w1 + "\nWeight 2: " + w2 + "\nTheat: " + theta;
        BufferedWriter writer = new BufferedWriter(new FileWriter("Weights.txt"));
        writer.write(str);
        writer.close();
    }
}