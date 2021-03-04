public class Point
{
    private double x, y;
    public String tag;

    public Point(double x1, double y1, String label)
    {
        //Initializing x, y, label
        x = x1;
        y = y1;
        tag = label;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public String getTag()
    {
        return tag;
    }
}
