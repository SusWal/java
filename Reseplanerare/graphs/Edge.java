package graphs;


public class Edge {

	private Plats dest;
	private int weight;
	private String name;

	public Edge(Plats dest, int weight, String name){
		this.dest = dest;
		this.weight = weight;
		this.name = name;
	}

	public Plats getDestination(){ 
		return dest; 
	}

	public int getWeight(){
		return weight;
	}

	public void setWeight(int w){
		weight = w;
	}

	public String getName(){
		return name;
	}


	public String toString(){
		return name + " till " + dest + "(" + weight + ")" + "\n";
	}
}
