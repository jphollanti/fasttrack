package ft;

import org.apache.commons.io.LineIterator;
import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import static ft.BestRoute.getLine;
import static ft.BestRoute.getScoreOfBestRoute;
import static ft.BestRoute.skipLineWithSeed;
import static ft.LineIteratorProvider.getLineIterator;
import static org.junit.Assert.assertEquals;

public class BestRouteTest {

    @Test
    public void canProvideBestRoute() throws URISyntaxException {
        verifyGivenAndExpected(getShortRouteFile(), 23);
        verifyGivenAndExpected(getLongerRouteFile(), 122);
    }

    private void verifyGivenAndExpected(File routeFile, int expectedScore) throws URISyntaxException {
        int score = getScoreOfBestRoute(routeFile);
        assertEquals(expectedScore, score);
    }

    private File getShortRouteFile() throws URISyntaxException {
        return getFile("/short-route.txt");
    }

    private File getLongerRouteFile() throws URISyntaxException {
        return getFile("/longer-route.txt");
    }

    private File getRealRouteFile() throws URISyntaxException {
        return getFile("/real-route.txt");
    }

    private File getFile(String fileName) throws URISyntaxException {
        return new File(getClass().getResource(fileName).toURI());
    }

    @Test
    public void providesSameResultAsBellmanFord() throws URISyntaxException {
        File routeFile = getRealRouteFile();
        assertEquals( getMaxPathWeight(getGraphWithInverseEdgeValues(routeFile))
                    , getScoreOfBestRoute(routeFile));
    }

    private int getMaxPathWeight(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph) {
        String root = getRootVertex(graph);
        int smallestWeight = Integer.MAX_VALUE;
        for (String leaf : getLeafVertices(graph)) {
            int weight = getRootToLeafShortestPathWeight(graph, root, leaf);
            if (weight < smallestWeight) {
                smallestWeight = weight;
            }
        }
        return -smallestWeight;
    }

    private int getRootToLeafShortestPathWeight(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph, String root, String leaf) {
        int mySum = 0;
        for (DefaultWeightedEdge e : BellmanFordShortestPath.findPathBetween(graph, root, leaf)) {
            mySum += graph.getEdgeWeight(e);
        }
        return mySum;
    }

    private Set<String> getLeafVertices(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph) {
        Set<String> leafs = new HashSet<String>();
        for (String vertex : graph.vertexSet()) {
            if (graph.outDegreeOf(vertex) == 0) {
                leafs.add(vertex);
            }
        }
        return leafs;
    }

    private String getRootVertex(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph) {
        String root = null;
        for (String vertex : graph.vertexSet()) {
            if (graph.inDegreeOf(vertex) == 0) {
                root = vertex;
                break;
            }
        }
        return root;
    }

    private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> getGraphWithInverseEdgeValues(File routeFile) throws URISyntaxException {
        SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph =
                new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>
                        (DefaultWeightedEdge.class);
        LineIterator it = getLineIterator(routeFile);
        skipLineWithSeed(it);
        populateGraph(graph, it);
        return graph;
    }

    private void populateGraph(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph, LineIterator it) {
        int lineNumber = 1;
        setRootNode(graph, lineNumber, it);
        while (it.hasNext()) {
            lineNumber++;
            populateGraphForLine(graph, it, lineNumber);
        }
    }

    private void populateGraphForLine(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph, LineIterator it, int lineNumber) {
        int[] line = getLine(it.nextLine());
        addVertices(line, lineNumber, graph);
        addEdges(line, lineNumber, graph);
    }

    private void setRootNode(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph, int lineNumber, LineIterator it) {
        graph.addVertex("ROOT");
        int[] line = getLine(it.nextLine());
        graph.addVertex(getNodeName(lineNumber, 0));
        addEdgeWithWeight(graph, getNodeName(lineNumber, 0), -line[0], "ROOT");
    }

    private void addVertices(int[] line, int lineNumber, SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph) {
        for (int i=0; i<line.length; i++) {
            graph.addVertex(getNodeName(lineNumber, i));
        }
    }

    public void addEdges(int[] line, int lineNumber, SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph) {
        for (int i=0; i<line.length; i++) {
            String nodeName = getNodeName(lineNumber, i);
            int nodeValue = -line[i];
            String rightParent = getNodeName(lineNumber - 1, i);
            if (firstInLine(i)) {
                addEdgeWithWeight(graph, nodeName, nodeValue, rightParent);
                continue;
            }
            String leftParent = getNodeName(lineNumber - 1, i - 1);
            if (lastInLine(i, line)) {
                addEdgeWithWeight(graph, nodeName, nodeValue, leftParent);
                continue;
            }
            addEdgeWithWeight(graph, nodeName, nodeValue, leftParent);
            addEdgeWithWeight(graph, nodeName, nodeValue, rightParent);
        }
    }

    private void addEdgeWithWeight(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph, String nodeName, int nodeValue, String rightParent) {
        DefaultWeightedEdge e = graph.addEdge(rightParent, nodeName);
        graph.setEdgeWeight(e, nodeValue);
    }

    private boolean lastInLine(int i, int[] line) {
        return i + 1 == line.length;
    }

    private String getNodeName(int line, int column) {
        return line + ":" + column;
    }

    private boolean firstInLine(int i) {
        return i == 0;
    }
}
