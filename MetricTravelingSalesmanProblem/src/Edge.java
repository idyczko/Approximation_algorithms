
public class Edge {
	private int oneVertex;
	private int secondVertex;
	private int cost;

	public void setCost(int cost) {
		this.cost = cost;
	}

	public Edge(int oneVertex, int secondVertex, int cost) {
		if(cost<=0 || oneVertex<0 || secondVertex<0){
			throw new IllegalArgumentException("No edge for cost less or equal to 0 or vertices numbers less than 0!");
		}
		
		this.oneVertex= Math.min(oneVertex, secondVertex);
		this.secondVertex=Math.max(oneVertex, secondVertex);
		this.cost = cost;
	}

	public Edge() {
		// TODO Auto-generated constructor stub
	}

	public Edge(Edge edge) {
		this.oneVertex = edge.oneVertex;
		this.secondVertex = edge.secondVertex;
		this.cost = edge.cost;
	}

	public int getOneVertex() {
		return oneVertex;
	}

	public int getSecondVertex() {
		return secondVertex;
	}

	public int getCost() {
		return cost;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		final Edge other = (Edge) obj;
		if (this.oneVertex != other.oneVertex || this.secondVertex != other.secondVertex || this.cost != other.cost) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return this.oneVertex*(this.oneVertex+ 2*this.secondVertex+3*this.cost);
	}
}
