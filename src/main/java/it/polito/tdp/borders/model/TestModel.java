package it.polito.tdp.borders.model;

import java.util.Map;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();

		System.out.println("TestModel -- TODO");
		
		System.out.println("Creo il grafo relativo al 2000");
		model.creaGrafo(2000);
		
		Map<Country, Integer> countries = model.elencoStati();
		System.out.format("Trovate %d nazioni\n", countries.size());

		System.out.format("Numero componenti connesse: %d\n", model.componentiConnesse());
		
		
		for (Country country : countries.keySet())
			System.out.format("%s %d\n", country, countries.get(country));		
		
	}

}
