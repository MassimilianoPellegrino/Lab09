
package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private ComboBox<Country> cmbNaz;
    
    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	this.txtResult.clear();
    	this.cmbNaz.getItems().clear();
    	int anno;
    	
    	try {
    		anno = Integer.parseInt(this.txtAnno.getText());
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserire un numero");
    		return;
    	}
    	
    	if(anno<1816 || anno>2016) {
    		this.txtResult.setText("Inserire un anno compreso tra 1816 e 2016");
    		return;
    	}
    	
    	model.creaGrafo(anno);
    	Map<Country, Integer> elenco = model.elencoStati();
    	int conn = model.componentiConnesse();
    	
    	this.txtResult.appendText("Numero componenti connesse: "+conn+"\n");
    	for(Country c: elenco.keySet()) {
    		txtResult.appendText(c.getStateNme()+" "+elenco.get(c)+"\n");
    	}
    	
    	this.cmbNaz.getItems().addAll(elenco.keySet());
    }
    
    @FXML
    void doStatiRaggiungibili(ActionEvent event) {
    	this.txtResult.clear();
    	List<Country> stati = model.getStatiRaggiungibili(this.cmbNaz.getValue());
    	for(Country c: stati) {
    		txtResult.appendText(c.getStateNme()+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
