package graphs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;


public class ListGraph {
	private Map<Plats, List<Edge>> data = new HashMap<Plats, List<Edge>>();

	public List<Edge> getEdgesFrom(Plats stad){

		return new ArrayList<Edge>(data.get(stad));

	}

	public void add(Plats ny){

		String namn = new String(ny.getNamn());
		boolean siffra = false;
		for (int i = 0 ; i < namn.length() ; i++)
			if (Character.isDigit(namn.charAt(i)))
				siffra = true;
		if (siffra){
			throw new IllegalArgumentException("En plats kan inte innehålla siffror");
		}

		data.put(ny, new ArrayList<Edge>());

	}


	public void connect(Plats from, Plats to, String namn, int vikt){
		if (!data.containsKey(from) || !data.containsKey(to)){
			throw new NoSuchElementException("Finns ingen sådan stad");
		}
		if (getEdgeBetween(to, from) != null){
			throw new IllegalArgumentException("Finns redan en förbindelse mellan dessa platser");
		}

		if (vikt < 0){
			throw new IllegalArgumentException("Man kan ju inte åka bakåt i tiden, skriv en positiv tid");
		}
		List<Edge> fromVagar = data.get(from);
		Edge e1 = new Edge(to, vikt, namn);
		fromVagar.add(e1);

		List<Edge> toVagar = data.get(to);
		Edge e2 = new Edge(from, vikt, namn);
		toVagar.add(e2);
	}


	public boolean pathExists(Plats from, Plats to){
		Set<Plats> besokta = new HashSet<Plats>();
		dfs(from, besokta);
		return besokta.contains(to);
	}


	public LinkedList<Edge> getAPath(Plats from, Plats to){
		Set<Plats> besokta = new HashSet<Plats>();
		Map<Plats, Plats> via = new HashMap<Plats, Plats>();

		dfs2(from, null, besokta, via);

		LinkedList<Edge> vag = new LinkedList<Edge>();
		Plats whereTo = to;
		while(!whereTo.equals(from)){
			Plats whereFrom = via.get(whereTo);
			Edge e = getEdgeBetween(whereFrom, whereTo);
			vag.addFirst(e);
			whereTo = whereFrom;		
		}
		return vag;
	}


	private void dfs(Plats where, Set<Plats> besokta){
		List<Edge> vagar = data.get(where);
		besokta.add(where);
		for (Edge e : getEdgesFrom(where)){
			if (!besokta.contains(e.getDestination())){
				dfs(e.getDestination(), besokta);
			}
		}
	}

	private void dfs2(Plats where, Plats fromWhere, Set<Plats> besokta, Map<Plats, Plats> via){
		besokta.add(where);
		via.put(where, fromWhere);
		for(Edge e : data.get(where)){
			if (!besokta.contains(e.getDestination())){
				dfs2(e.getDestination(), where, besokta, via);
			}
		}
	}


	public Edge getEdgeBetween(Plats from, Plats to){
		if (!data.containsKey(from) || !data.containsKey(to)){
			throw new NoSuchElementException("Finns ingen sådan plats!");
		}
		if(data.containsKey(from) && data.containsKey(to)){
			for (Edge e : data.get(from)){
				if (e.getDestination().equals(to)){
					return e;
				}	
			}
		}
		return null;	
	}


	public void setConnectionWeight(Plats p1, Plats p2, int cw){
		if (!data.containsKey(p1) || !data.containsKey(p2)){
			throw new NoSuchElementException("Finns ingen sådan plats");
		}
		if (getEdgeBetween(p1, p2) == null){
			throw new NoSuchElementException("Finns ingen sådan förbindelse");
		}
		if (cw < 0){
			throw new IllegalArgumentException("Man kan ju inte åka bakåt i tiden, skriv en positiv tid");
		}

		if (data.containsKey(p1) && data.containsKey(p2)){
			for (Edge e : data.get(p1)){
				if (e.getDestination().equals(p2)){
					e.setWeight(cw);
				}
			}
			for (Edge e : data.get(p2)){
				if (e.getDestination().equals(p1)){
					e.setWeight(cw);
				}
			}
		}
	}


	public ArrayList<Plats> getNodes(){
		ArrayList<Plats> noder = new ArrayList<Plats>();
		for(Plats p : data.keySet()){
			noder.add(p);
		}		
		return noder;
	}


	public String toString(){	
		String str = "";
		for (Map.Entry<Plats, List<Edge>> me : data.entrySet()) {

			str += me.getKey() + "";
			for (Edge e : me.getValue())
				str += e.toString() + "";
			str += "/n";
		}
		return str;
	}	
}
