package it.polito.tdp.borders.db;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Border;

public class TestDAO {

	public static void main(String[] args) {

		BordersDAO dao = new BordersDAO();
		Map<Integer, Country> map = new TreeMap<>();

		System.out.println("Lista di tutte le nazioni:");
		dao.loadAllCountries(map);
		System.out.println(map.values());
		List<Border> confini = dao.getCountryPairs(1920);
		
		System.out.println(confini);
	}
}
