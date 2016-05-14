import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class Graph {
	private static int MAXIMALCOST=100000;
	private ArrayList<Integer> vertices = new ArrayList<Integer>();
	private ArrayList<Edge> edges = new ArrayList<Edge>();

	public Graph() {
	}

	public Graph(Graph graph) {
		this.vertices = new ArrayList<Integer>(graph.vertices);
		this.edges = new ArrayList<Edge>(graph.edges);
	}

	public Graph(ArrayList<Integer> vertices, ArrayList<Edge> edges) {
		this.vertices = new ArrayList<Integer>(vertices);
		this.edges = new ArrayList<Edge>(edges);
	}

	public Graph(int[][] graph) {
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph.length; j++) {
				if (j > i) {
					this.addEdge(new Edge(i, j, graph[i][j]));
				}
			}
		}
	}

	public static Graph generateMetricCompleteGraph(int vertices, int bound) {
		int[][] graphTab = new int[vertices][vertices];
		for(int i = 0;i<graphTab[0].length;i++){
			for(int j = 0;j<graphTab[0].length;j++){
				graphTab[i][j]=graphTab[j][i]=ThreadLocalRandom.current().nextInt(bound)+1;
			}
		}
		for(int i = 0;i<graphTab[0].length;i++){
			for(int j = 0;j<graphTab[0].length;j++){
				for(int k = 0;k<graphTab[0].length;k++){
					if(graphTab[i][j]>graphTab[i][k]+graphTab[k][j]){
						graphTab[i][j]=graphTab[j][i]=graphTab[i][k]+graphTab[k][j];
					}
				}
			}
		}
		Graph graph = new Graph(graphTab);
		return graph;
	}

	public ArrayList<Integer> getTwoApproximationPath(int startVertex){
		Graph mst = findMSTPrim();
		ArrayList<Integer> trace = mst.findDFSTrace(startVertex);
		trace.add(trace.get(0));
		return trace;
	}

	public ArrayList<Integer> getThreeOverTwoApproximationPath(int startVertex) {
		Graph mst = findMSTPrim();
		ArrayList<Integer> oddVertices = mst.getListOfOddDegreeVertices();
		ArrayList<Edge> perfectMatch = findPerfectMatchGreedy(oddVertices);
		mst.addEdge(perfectMatch);
		ArrayList<Integer> eulerianCycle = mst.findEulerianRouteHierholzer(0);
		ArrayList<Integer> trace = new ArrayList<Integer>();
		for(int vertex:eulerianCycle){
			if(!trace.contains(vertex)){
				trace.add(vertex);
			}
		}
		trace.add(trace.get(0));
		return trace;
	}

	public ArrayList<Integer> getBruteForceSolution(int startVertex) {
		ArrayList<Integer> optimalTrace = new ArrayList<Integer>();
		ArrayList<Integer> vertices = new ArrayList<Integer>(this.vertices);
		vertices.remove(vertices.indexOf(startVertex));
		int minimalCost = MAXIMALCOST;
		ArrayList<ArrayList<Integer>> feasibleSolutions = PermutationsGeneratingClass.generate(vertices);
		for(ArrayList<Integer> permutation: feasibleSolutions){
			ArrayList<Integer> trace = new ArrayList<Integer>();
			trace.add(startVertex);
			trace.addAll(permutation);
			trace.add(startVertex);
			if(this.getTraceCost(trace)<minimalCost){
				optimalTrace=new ArrayList<Integer>(trace);
				minimalCost = this.getTraceCost(trace);
			}
		}
		return optimalTrace;
	}

	public Graph findMSTPrim() {
		Graph mst = new Graph();
		if (this.vertices.size() < 2) {
			throw new IllegalArgumentException("Cannot find mst for an empty graph or single node!");
		}
		mst.vertices.add(0);
		int minimalCost = MAXIMALCOST, minimalCostTargetNode = 0, minimalCostSourceNode = 0;
		while (mst.vertices.size() < this.vertices.size()) {
			minimalCost = MAXIMALCOST;
			for (int vertex : mst.vertices) {
				HashMap<Integer, Integer> nodesMap = this.getMinimalCostMap(vertex);
				for (int node : nodesMap.keySet()) {
					if (!mst.vertices.contains(node) && nodesMap.get(node) < minimalCost) {
						minimalCostSourceNode = vertex;
						minimalCost = nodesMap.get(node);
						minimalCostTargetNode = node;
					}
				}
			}
			mst.addEdge(new Edge(minimalCostSourceNode, minimalCostTargetNode, minimalCost));
	
		}
		return mst;
	}

	public ArrayList<Integer> findDFSTrace(int startNode) {
		ArrayList<Integer> trace = new ArrayList<Integer>();
	
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(startNode);
		while (!stack.empty()) {
			int vertex = stack.pop();
			if (!trace.contains(vertex)) {
				trace.add(vertex);
				for (Integer node : this.getAdjacentVertices(vertex)) {
					stack.push(node);
				}
			}
	
		}
		return trace;
	}

	public ArrayList<Integer> findEulerianRouteHierholzer(int startVertex) {
		Graph temporaryGraph = new Graph(this);
		ArrayList<Integer> trace = new ArrayList<Integer>();
		trace.add(startVertex);
		int cycleStartVertex = startVertex;
		int currentVertex = cycleStartVertex;
		while (!temporaryGraph.edges.isEmpty()) {
			int lastVertex = currentVertex;
			currentVertex = temporaryGraph.getAdjacentVertices(currentVertex).get(0);
			trace.add(trace.indexOf(lastVertex), currentVertex);
			Edge edgeToRemove = new Edge();
			for (Edge edge : temporaryGraph.edges) {
				if (edge.getOneVertex() == Math.min(currentVertex, lastVertex)
						&& edge.getSecondVertex() == Math.max(currentVertex, lastVertex)) {
					edgeToRemove = new Edge(edge);
					break;
				}
			}
			temporaryGraph.removeEdge(edgeToRemove);
			if (currentVertex == cycleStartVertex) {
				for (Edge edge : temporaryGraph.edges) {
					if (trace.contains(edge.getOneVertex())) {
						cycleStartVertex = edge.getOneVertex();
						currentVertex = cycleStartVertex;
						break;
					}
					if (trace.contains(edge.getSecondVertex())) {
						cycleStartVertex = edge.getSecondVertex();
						currentVertex = cycleStartVertex;
						break;
					}
				}
			} else {
			}
		}
		return trace;
	}

	public ArrayList<Edge> findPerfectMatchGreedy(ArrayList<Integer> vertices) {
		Graph temporaryGraph = new Graph(this);
		for (int vertex : this.vertices) {
			if (!vertices.contains(vertex)) {
				temporaryGraph.removeVertex(vertex);
			}
		}
		ArrayList<Edge> outputEdges = new ArrayList<Edge>();
		Edge minimalCostEdge = new Edge();
		while (temporaryGraph.vertices.size() > 0) {
			minimalCostEdge.setCost(MAXIMALCOST);
			for (Edge edge : temporaryGraph.edges) {
				if (edge.getCost() < minimalCostEdge.getCost()) {
					minimalCostEdge = new Edge(edge);
				}
			}
			outputEdges.add(new Edge(minimalCostEdge));
			temporaryGraph.removeVertex(minimalCostEdge.getOneVertex());
			temporaryGraph.removeVertex(minimalCostEdge.getSecondVertex());
		}
	
		return outputEdges;
	}

	public int getTraceCost(ArrayList<Integer> trace) {
		int cost = 0;
		for (int i = 0; i < trace.size() - 1; i++) {
			cost += getEdgeCost(trace.get(i), trace.get(i + 1));
		}
		return cost;
	}

	public void addEdge(Edge edge) {
		if (!vertices.contains(edge.getOneVertex())) {
			vertices.add(edge.getOneVertex());
		}
		if (!vertices.contains(edge.getSecondVertex())) {
			vertices.add(edge.getSecondVertex());
		}
		edges.add(edge);
	}

	public void addEdge(ArrayList<Edge> edges) {
		for (Edge edge : edges) {
			addEdge(edge);
		}
	}

	public void removeVertex(int vertex) {
		ArrayList<Edge> edges = new ArrayList<Edge>(this.edges);
		if (this.vertices.contains(vertex)) {
			for (Edge edge : edges) {
				if (edge.getOneVertex() == vertex || edge.getSecondVertex() == vertex) {
					removeEdge(edge);
				}
			}
		}
	}

	public void removeEdge(Edge edge) {
		if (edges.contains(edge)) {
			edges.remove(this.edges.indexOf(edge));
		}
		if (!isVertexConnected(edge.getOneVertex())) {
			vertices.remove(this.vertices.indexOf(edge.getOneVertex()));
		}
		if (!isVertexConnected(edge.getSecondVertex())) {
			vertices.remove(this.vertices.indexOf(edge.getSecondVertex()));
		}
	
	}

	public ArrayList<Integer> getVertices() {
		return vertices;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	private HashMap<Integer, Integer> getMinimalCostMap(int vertex) {
		HashMap<Integer, Integer> costMap = new HashMap<Integer, Integer>();
		for (Edge edge : edges) {
			if (edge.getOneVertex() == vertex) {
				if (!costMap.containsKey(edge.getSecondVertex())) {
					costMap.put(edge.getSecondVertex(), edge.getCost());
				}
				continue;
			}
			if (edge.getSecondVertex() == vertex) {
				if (!costMap.containsKey(edge.getOneVertex())) {
					costMap.put(edge.getOneVertex(), edge.getCost());
				}
				continue;
			}
		}
		return costMap;
	}

	private ArrayList<Integer> getAdjacentVertices(int vertex) {
		ArrayList<Integer> adjacentVertices = new ArrayList<Integer>();
		for (Edge edge : edges) {
			if (edge.getOneVertex() == vertex) {
				if (!adjacentVertices.contains(edge.getSecondVertex()) && edge.getSecondVertex() != vertex) {
					adjacentVertices.add(edge.getSecondVertex());
				}
				continue;
			}
			if (edge.getSecondVertex() == vertex) {
				if (!adjacentVertices.contains(edge.getOneVertex()) && edge.getOneVertex() != vertex) {
					adjacentVertices.add(edge.getOneVertex());
				}
				continue;
			}
		}
		return adjacentVertices;
	}

	private ArrayList<Integer> getListOfOddDegreeVertices() {
		ArrayList<Integer> oddGradeVertices = new ArrayList<Integer>();
		for (int vertex : vertices) {
			if (!this.isVertexGradeEven(vertex)) {
				oddGradeVertices.add(vertex);
			}
		}
		return oddGradeVertices;
	}

	private Boolean isVertexGradeEven(int vertex) {
		int grade = 0;
		for (Edge edge : edges) {
			if (edge.getOneVertex() == vertex || edge.getSecondVertex() == vertex) {
				grade++;
			}
		}

		return grade % 2 == 0 ? true : false;
	}

	private Boolean isVertexConnected(int vertex) {
		for (Edge edge : edges) {
			if (edge.getOneVertex() == vertex || edge.getSecondVertex() == vertex) {
				return true;
			}
		}
		return false;
	}

	private int getEdgeCost(Integer vertex_1, Integer vertex_2) {
		int oneVertex = Math.min(vertex_1, vertex_2);
		int secondVertex = Math.max(vertex_1, vertex_2);
		for (Edge edge : edges) {
			if (edge.getOneVertex() == oneVertex && edge.getSecondVertex() == secondVertex) {
				return edge.getCost();
			}
		}
		return 0;
	}


}
