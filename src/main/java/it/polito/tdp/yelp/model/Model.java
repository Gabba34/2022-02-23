package it.polito.tdp.yelp.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	YelpDao dao;
	List<String> città;
	List<Business> businesses;
	List<Review> reviews;
	Graph<Review, DefaultWeightedEdge> grafo;
	List<Consecutivo> cons;
	List<Review> uscenti;
	int d;
	List<Review> miglioramenti;
	
	public Model() {
		dao = new YelpDao();
	}
	
	public List<String> getCittà(){
		città = new ArrayList<>(dao.getCities());
		return città;
	}
	
	public List<Business> getBusinesses(String città){
		businesses = new ArrayList<>(dao.getBusinesses(città));
		return businesses;
	}
	
	public List<Review> getReviews(Business b){ // Vertici
		reviews = new ArrayList<>(dao.getReviews(b));
		return reviews;
	}
	
	public String creaGrafo(Business b) {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, getReviews(b));
		for(Consecutivo c : getConsecutivi()) {
			Graphs.addEdge(this.grafo, c.getPartenza(), c.getArrivo(), c.getPeso());
		}
		for (Review r : reviews) {
			trovaMiglioramento(r);
		}
		return "Grafo creato con\nvertici: "+this.grafo.vertexSet().size()+"\narchi: "+this.grafo.edgeSet().size()
			  +"\n"+getUscenti().toString()+" Archi uscenti: "+d+"\n"+miglioramenti;
	}

	public List<Consecutivo> getConsecutivi(){
		cons = new ArrayList<>();
		for(Review r1 : reviews) {
			for(Review r2 : reviews) {
				double peso = 0;
				peso = Math.abs(ChronoUnit.DAYS.between(r1.getDate(), r2.getDate()));
				if(peso!=0);{
					if(r1.getDate().isBefore(r2.getDate())) {
						cons.add(new Consecutivo(r1, r2, peso));
					}
				}
			}
		}
		return cons;
	}
	
	public List<Review> getUscenti(){
		uscenti = new ArrayList<>();
		d = 0;
		for(Review r : reviews) {
			if(uscenti.isEmpty()) {
				uscenti.add(r);
			} else {
				if(d<this.grafo.outgoingEdgesOf(r).size()) {
					d = this.grafo.outgoingEdgesOf(r).size();
					uscenti.remove(uscenti.size()-1);
					uscenti.add(r);
				} else if(d==this.grafo.outgoingEdgesOf(r).size()){
				uscenti.add(r);
				}
			}
		}
		return uscenti;
	}
	
	// ricorsione
		public List<Review> trovaMiglioramento(Review r){
			miglioramenti = new ArrayList<>();
			Set<Review> componenteConnessa;
			ConnectivityInspector<Review, DefaultWeightedEdge> ci = new ConnectivityInspector<>(this.grafo);
			componenteConnessa = ci.connectedSetOf(r);
			List<Review> reviewValide = new ArrayList<>();
			reviewValide.add(r);
			componenteConnessa.remove(r);
			reviewValide.addAll(componenteConnessa);
			List<Review> parziale = new ArrayList<>();
			parziale.add(r);
			cerca(parziale,reviewValide, 1);
			return miglioramenti;
		}

		private void cerca(List<Review> parziale, List<Review> reviewValide, int livello) {
			if(parziale.size()>miglioramenti.size()) {
				miglioramenti = new ArrayList<>(parziale);
			}
			if(livello==reviewValide.size()) {
				return;
			}
			parziale.add(reviewValide.get(livello));
			cerca(parziale, reviewValide, livello+1);
			parziale.remove(reviewValide.get(livello));
			cerca(parziale, reviewValide, livello+1);
		}

}
