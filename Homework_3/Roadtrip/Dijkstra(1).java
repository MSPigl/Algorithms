import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Starter code for Dijkstra's algorithm using METAL graph data.
 *
 * @author Jim Teresco
 */
public class Dijkstra {

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
    public static void main(String args[]) throws IOException {

	// check for command-line parameters
	if (args.length != 3 && args.length != 4) {
	    System.err.println("Usage: java Dijkstra graphfile start destination [pthfile]");
	    System.exit(1);
	}
	
        // read in the file to construct the graph
        Scanner s = new Scanner(new File(args[0]));
        HighwayGraph g = new HighwayGraph(s);
        s.close();
	if (DEBUG) {
	    System.out.println("Successfully read graph " + args[0] + " with |V|=" + g.vertices.length + " |E|=" + g.numEdges);
	}

	// find the vertex objects for the starting and destination points
	HighwayVertex start = g.getVertexByName(args[1]);
	if (start == null) {
	    System.err.println("No vertex found with label " + args[1]);
	    System.exit(1);
	}
	if (DEBUG) {
	    System.out.println("Start: " + start.label + ", vertexNum=" + start.vNum);
	}

	HighwayVertex dest = g.getVertexByName(args[2]);
	if (dest == null) {
	    System.err.println("No vertex found with label " + args[2]);
	    System.exit(1);
	}
	if (DEBUG) {
	    System.out.println("Dest: " + dest.label + ", vertexNum=" + dest.vNum);
	}

	/* REMOVE this comment and replace it with your implementation of
	   Dijkstra's algorithm and a whole lot of comments of your own */
    }
}
