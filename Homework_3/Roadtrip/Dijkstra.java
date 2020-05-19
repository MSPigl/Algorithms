import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.HashMap;

/**
 * Starter code for Dijkstra's algorithm using METAL graph data.
 *
 * @author Jim Teresco
 */
public class Dijkstra 
{
    public static final boolean DEBUG = true;

    /**
     * The main method for the Dijkstra's algorithm driver program,
     * which will take command-line parameters of a graph to read,
     * starting and ending points for driving directions, and an
     * optional filename to write a METAL ".pth" file to display
     * the directions in HDX.
     *
     * @param args command-line parameters, which include<br>
     * args[0]: name of graph file<br>
     * args[1]: starting waypoint label<br>
     * args[2]: destination waypoint label<br>
     * args[3]: (optional) .pth file name for mappable route
     */
    public static void main(String args[]) throws IOException 
    {
        // check for command-line parameters
        if (args.length != 3 && args.length != 4) {
            System.err.println("Usage: java Dijkstra graphfile start destination [pthfile]");
            System.exit(1);
        }

        // read in the file to construct the graph
        Scanner s = new Scanner(new File(args[0]));
        HighwayGraph g = new HighwayGraph(s);
        s.close();

        if (DEBUG) 
        {
            System.out.println("Successfully read graph " + args[0] + " with |V|=" + g.vertices.length + " |E|=" + g.numEdges);
        }

        // find the vertex objects for the starting and destination points
        HighwayVertex start = g.getVertexByName(args[1]);
        if (start == null) 
        {
            System.err.println("No vertex found with label " + args[1]);
            System.exit(1);
        }
        if (DEBUG) 
        {
            System.out.println("Start: " + start.label + ", vertexNum=" + start.vNum);
        }

        HighwayVertex dest = g.getVertexByName(args[2]);
        if (dest == null) 
        {
            System.err.println("No vertex found with label " + args[2]);
            System.exit(1);
        }
        if (DEBUG) 
        {
            System.out.println("Dest: " + dest.label + ", vertexNum=" + dest.vNum);
        }

        // create empty HashMap and Priority Queue
        PriorityQueue<Cost_Edge> queue = new PriorityQueue<Cost_Edge>();
        HashMap<String, HighwayEdge> map = new HashMap<String, HighwayEdge>();

        // mark all viertices as unvisited
        g.markAllUnvisited();

        if (DEBUG)
        {
            System.out.println("Adding " + start.label + "to the queue");
        }

        // add the start vertex to the map and mark it as visited
        map.put(start.label, null);
        start.visited = true;

        // HighwayEdge object to scroll through start's adjacent edges
        HighwayEdge scroll = start.head;

        // add all edges adjacent to start into the queue
        while (scroll != null)
        {
            if (DEBUG)
            {
                System.out.println("Attempting to add edge " + scroll.label + " to queue");
            }
            queue.add(new Cost_Edge(scroll.length, scroll));
            if (DEBUG)
            {
                System.out.println("Edge " + scroll.label + " successfully added to queue");
            }
            scroll = scroll.next;
        }

        while (!map.containsKey(dest.label))
        {
            // dequeue and store the edge in a temporary variable
            Cost_Edge curr = queue.poll();
            HighwayEdge findEdge = curr.edge;

            if (DEBUG)
            {
                System.out.println("Dequeued " + curr.edge.label + " from queue");
            }

            // loop until an unvisited edge is found
            while (g.vertices[findEdge.dest].visited)
            {
                curr = queue.poll();
                findEdge = curr.edge;

                if (DEBUG)
                {
                    System.out.println("Dequeued " + curr.edge.label + " from queue");
                }
            }

            // found a new shortest path to a vertex
            map.put(g.vertices[findEdge.dest].label, findEdge);
            g.vertices[findEdge.dest].visited = true;

            if (DEBUG)
            {
                System.out.println("Found a new shortest path to " + g.vertices[findEdge.dest].label);
            }

            // end main loop if the map contains our destination
            if (map.containsKey(dest.label))
            {
                if (DEBUG)
                {
                    System.out.println("Destination " + dest.label + " was found");
                }
                break;
            }

            // enqueue all adjacent vertices
            scroll = g.vertices[findEdge.dest].head;
            while (scroll != null)
            {
                if (!g.vertices[scroll.dest].visited)
                {
                    if (DEBUG)
                    {
                        System.out.println("Attempting to add edge " + scroll.label + " to queue");
                    }
                    queue.add(new Cost_Edge(curr.cost + scroll.length, scroll));
                    if (DEBUG)
                    {
                        System.out.println("Edge " + scroll.label + " successfully added to queue");
                    }
                }
                scroll = scroll.next;
            }
        }

        // array of HighwayEdges
        java.util.ArrayList<HighwayEdge> path = new java.util.ArrayList<HighwayEdge>();
        String current = dest.label;
        while (!current.equals(start.label)) {
            HighwayEdge hop = map.get(current);
            path.add(hop);
            current = g.vertices[hop.source].label;
        }

        // ouput detailed directions
        System.out.println("\nDetailed instructions:");
        double distance = 0;
        for (int i = path.size() - 1; i >=0; i--)
        {
            HighwayEdge temp = path.get(i);
            distance += temp.length;
            System.out.println("Travel from " + g.vertices[temp.source].label + " to " + g.vertices[temp.dest].label);
            System.out.printf(" for %.2f along %s, total %.2f\n", temp.length, temp.label, distance);
        }

        // write to .pth file if args[3] exists
        if (args.length == 4)
        {
            java.io.PrintWriter writer = new java.io.PrintWriter(args[3]);
            writer.println("START " + start.label + " " + start.point);
            for (int i = path.size() - 1; i >= 0; i--)
            {
                HighwayEdge temp = path.get(i);
                writer.print(temp.label + " ");
                if (temp.shapePoints != null)
                {
                    for (int j = 0; j < temp.shapePoints.length; i++)
                    {
                        writer.print(temp.shapePoints[j] + " ");
                    }
                }
                writer.println(g.vertices[temp.dest].label + " " + g.vertices[temp.dest].point);
            }
            writer.close();
        }

        System.out.println("\nSimplified instructions:");
        distance = 0;
        for (int i = path.size() - 1; i >= 0; i--)
        {
            HighwayEdge temp = path.get(i);
            System.out.print("Travel from " + g.vertices[temp.source].label + " to ");
            String route = temp.label;
            double tempLength = temp.length;
            while (i > 0 && path.get(i - 1).label.equals(route))
            {
                tempLength += path.get(i - 1).length;
                i--;
            }
            distance += tempLength;
            
            System.out.println(g.vertices[path.get(i).dest].label);
            System.out.printf(" for %.2f miles along %s, total %.2f\n", tempLength, route, distance);
        }
    }
}