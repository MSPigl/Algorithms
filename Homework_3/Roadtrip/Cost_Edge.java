public class Cost_Edge implements Comparable<Cost_Edge>
{
    protected double cost;
    protected HighwayEdge edge;
    
    public Cost_Edge (double cost, HighwayEdge edge)
    {
        this.cost = cost;
        this.edge = edge;
    }
    
    public int compareTo (Cost_Edge e)
    {
        if (this.cost > e.cost)
        {
            return 1;
        }
        if (this.cost < e.cost)
        {
            return -1;
        }
        return 0;
    }
}