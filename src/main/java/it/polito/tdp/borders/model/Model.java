package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	BordersDAO dao;
	Map<Integer, Country> idMap;
	Graph<Country, DefaultEdge> grafo;
	
	public Model() {
		dao = new BordersDAO();
		idMap = new TreeMap<>();
		dao.loadAllCountries(idMap);
	}
	
	public void creaGrafo(int anno) {
		grafo = new SimpleGraph<>(DefaultEdge.class);
		
		List<Border> confini = dao.getCountryPairs(anno);
		
		for(Border b: confini) {
			grafo.addVertex(idMap.get(b.getState1no()));
			grafo.addVertex(idMap.get(b.getState2no()));
			grafo.addEdge(idMap.get(b.getState1no()), idMap.get(b.getState2no()));
		}
	}
	
	public Map<Country, Integer> elencoStati(){
		
		Map<Country, Integer> map = new HashMap<>();
		
		for(Country c: grafo.vertexSet())
			map.put(c, Graphs.successorListOf(grafo, c).size());
		
		return map;
	}
	
	public int componentiConnesse() {
		
		ConnectivityInspector<Country, DefaultEdge> conn = new ConnectivityInspector<>(grafo);
		
		return conn.connectedSets().size();
		
	}
	 
	public List<Country> getStatiRaggiungibili(Country c){
		
		BreadthFirstIterator<Country, DefaultEdge> it = new BreadthFirstIterator<>(grafo, c);
		List<Country> result = new LinkedList<>();
		
		while(it.hasNext())
			result.add(it.next());
		
		
		return result;
		
	}
	
	public List<Country> getStatiRaggiungibili2(Country c){
		
		List<Country> result = new LinkedList<>();
		result.add(c);
		
		cerca(result);
		
		return result;
		
	}
	
	public void cerca(List<Country> result) {
		for(Country c: Graphs.neighborListOf(grafo, result.get(result.size()-1))) {
			if(!result.contains(c)) {
				result.add(c);
				cerca(result);
			}
		}
	}
	
	public List<Country> getStatiRaggiungibili3(Country country){
		List<Country> daVisitare = new LinkedList<>();
		List<Country> visitati = new LinkedList<>();
		List<Country> prova = new LinkedList<>();
		
		daVisitare.add(country);
		
		Country c;
		
		while(daVisitare.size()>0) {
			
			c=daVisitare.get(0);
						
			for(Country c2: Graphs.neighborListOf(grafo, c)) {
				if(!prova.contains(c2)) {
					prova.add(c2);
					daVisitare.add(c2);
				}		
			}
			
			daVisitare.remove(c);
			visitati.add(c);
		}
		
		return visitati;
	}

}
