package de.htwg.fivewins.field;

//import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;

public class StrongAI extends AIAdapter{
	
	HashMap<Integer, HashMap<Integer, Double>> bigTree;
	ArrayList<Integer> liste;
	
	
	public StrongAI(String sign, Field field) {
		if(sign.equals("X") || sign.equals("O")) {
			this.whichPlayer = sign;
			this.field = field;
		} else {
			throw new IllegalArgumentException();
		}
	}



	private boolean isFree(int column, int row){
		boolean returnValue = false;
		if(field.getCellValue(--column, --row).equals("-")) {
			returnValue = true;
		}
		return returnValue;
	}



	/*
	 * isFreeList
	 * Sammelt alle freien Felder in einer Liste.
	 * Dort sind sie abgespeichert zB feld(12, 7) mit 1207.
	 */
	private ArrayList<Integer> isFreeList(){
		liste = new ArrayList<Integer>();
		for (int i = 1; i <= field.getSize(); i++){
			for(int j = 1; j <= field.getSize(); j++){
				if(isFree(i, j)){
					int k = i*100+j;
					liste.add(k);
				}
			}
		}
		return liste;
	}



	@Override
	public String calculateCommand() {
		//bigTree initialisieren mit 0;
		bigTree = new HashMap< Integer, HashMap<Integer, Double>>();
		bigTree.put(0, new HashMap<Integer, Double>());
		//Liste aufbaun mit allen verbleibendenfreien Feldern;
		isFreeList();
		//bigTree aufbauen.
		buildTree(0, liste);
		
		
		//aktuelles Feld kopieren oder nachbauen, damit diese Klasse hier drauf zugreifen kann.
		//bigTree Wege berechnen
		calculateTree(0, liste);
		
		
		//bigTree günstigsten Weg suchen. Aufaddieren.
		sumTree(0);
		
		//koordinaten vom besten Weg nehmen.
		int t = max(0);
		int column = t/100;                             // z = 1307;   1307:100 => 13;   1307-1300 = 7
		int row = t - column*100;
		return column + "," + row;
	}



	private HashMap<Integer, HashMap<Integer, Double>> buildTree(int z, ArrayList<Integer> l1){
		for(int i : l1){
			bigTree.get(z).put(i, 0.0);
			l1.remove(i);
			buildTree(z, l1);
		}
		return bigTree;
	}



	private void calculateTree(int z, ArrayList<Integer> l1){
		for(int i : l1){
			int column = z/100;                             // z = 1307;   1307:100 => 13;   1307-1300 = 7
			int row = z - column*100;
			String sign = "O";
			field.setValue(column, row, sign);
			
			//Temporäres Feld zusammenbauen.
			// TODO Zugriff auf field.gamefield das private und non static ist.
			//Gewinnabfrage für temporäres Feld.
			// TODO Zugriff auf Controller.
			/*
			if( .equals("O")){
				bigTree.get(z).put(i, 1.0);
			} else if( .equals("X")){
				bigTree.get(z).put(i, -1.0);
			}*/
			
			l1.remove(i);
			calculateTree(z, l1);
		}
	}



	/*
	 * sumTree
	 * durchläuft den Baum von unten und addiert alle Werte der Kindknoten zum Wert des Elternknoten.
	 */
	private void sumTree(int z){
		if(bigTree.get(z).keySet() != null){
			for(int i : bigTree.get(z).keySet()){
				sumTree(i);
				double w = bigTree.get(z).get(i);    // TODO noch nicht die richtigen werte, bzw. Zuweisung
				bigTree.get(z).put(i, w);
			}
			
		}
	}
	
	
	
	
	private int max(int z){
		int r = -100000;
		for(int i : bigTree.get(z).keySet()){
			if(r < i){
				r = i;
			}
		}
		return r;
	}
}



