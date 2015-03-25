package de.htwg.fivewins.model.ai;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Deque;
import java.util.List;



import de.htwg.fivewins.model.field.Field;
/*
 * please don't use it. It's bad, because it didn't work well. 
 */
public class StrongAI extends AIAdapter{

	private static final int STOP = 3;
	private static final int MULTIPLIER = 100;
	private static final int FIVEWINS = 5;
	private HashMap<Deque<Integer>, HashMap<Deque<Integer>, Double>> bigTree;
	private LinkedList<Integer> liste;
	private int needToWin = 0;
	private boolean win = false;
	private String winner = null;
	private int lastx;
	private int lasty;
	private int turn =  0;
	
	public StrongAI(String sign, Field field) {
		if(sign.equals("X") || sign.equals("O")) {
			this.whichPlayer = sign;
			this.field = field;
		} else {
			throw new IllegalArgumentException();
		}
	}



	private boolean isFree(int column, int row, String[][] f){
		boolean returnValue = false;
		if(f[column][row].equals("-")) {
			returnValue = true;
		}
		return returnValue;
	}


	/*
	 * isFreeList
	 * Sammelt alle freien Felder in einer Liste.
	 * Dort sind sie abgespeichert zB feld(12, 7) mit 1207.
	 */
	private List<Integer> isFreeList(String[][] f){
		liste = new LinkedList<Integer>();
		for (int i = 0; i < field.getSize(); i++){
			for(int j = 0; j < field.getSize(); j++){
				if(isFree(i, j, f)){
					int k = i*MULTIPLIER+j;
					liste.add(k);
				}
			}
		}
		return liste;
	}



	@Override
	public String calculateCommand() {
		//bigTree initialisieren mit 0
		bigTree = new HashMap<Deque<Integer>, HashMap<Deque<Integer>, Double>>();
		
		String tempField [][] = new String[field.getSize()][field.getSize()];
		tempField = arrayCopy(field.getGameField(), tempField);
		needToWin = calculateNeedToWin();
		
		//Liste aufbaun mit allen verbleibendenfreien Feldern
		isFreeList(tempField);
		
		//bigTree aufbauen.
		buildTree(0, liste);
		
		//bigTree Wege berechnen
		calculateTree(0, liste, tempField);
		
		
		//bigTree günstigsten Weg suchen. Aufaddieren.
		sumTree(0, 0);
		
		//koordinaten vom besten Weg nehmen.
		int t = max(0);
		int column = t/MULTIPLIER;
		int row = t - column*MULTIPLIER;
		return ++column + "," + ++row;
	}

	
	private void  buildTree(int z, LinkedList<Integer> l1){
		Deque<Integer> i = new LinkedList<Integer>();
		i.add(z);
		bigTree.put(i, new HashMap<Deque<Integer>, Double>());
		buildTree(i, l1, 1);
	}
	
	
	private HashMap<Deque<Integer>, HashMap<Deque<Integer>, Double>>  buildTree(Deque<Integer> z, LinkedList<Integer> l1, int depth){
		if(depth <= STOP){
			for(int i : l1){
				Deque<Integer> tmpDe = new LinkedList<Integer>();
				tmpDe.addAll(z);
				tmpDe.add(i);
				bigTree.put(tmpDe, new HashMap<Deque<Integer>, Double>());
				bigTree.get(z).put(tmpDe, 0.0);
				
				LinkedList<Integer> tmpLi = new LinkedList<Integer>();
				tmpLi.addAll(l1);
				tmpLi.remove(l1.indexOf(i));
				int depth2 = depth + 1;
				buildTree(tmpDe, tmpLi, depth2);
			}
		}
		return bigTree;
	}
	
	
	
	private void calculateTree(int z, List<Integer> l1, String[][] f){
		
		for(int i : l1){
			int column = i / MULTIPLIER;
			int row = i - column*MULTIPLIER;
			lastx = column;
			lasty = row;
			String tmpF[][] = f.clone();
			String sign = getPlayerSign();
			tmpF[column][row] = sign;
			getTurn(f);
			testWin();
			
			Deque<Integer> tmpDe = new LinkedList<Integer>();
			tmpDe.add(z);
			tmpDe.add(i);
			
			LinkedList<Integer> tmpLi = new LinkedList<Integer>();
			tmpLi.addAll(l1);
			tmpLi.remove(l1.indexOf(i));
			
			if(win){
				win = false;
				if( getWinnerSign().equals("O")){
					fillTree(tmpDe, tmpLi, 1.0, 1);
				} else if( getWinnerSign().equals("X")){
					fillTree(tmpDe, tmpLi, -1.0, 1);
				}
			} else{
				calculateTree(tmpDe, tmpLi, tmpF, 1);
			}
		}
	}
	

	private void calculateTree(Deque<Integer> z, LinkedList<Integer> l1, String[][] f, int depth){
		if(depth <= STOP){
			for(int i : l1){
				int column = i / MULTIPLIER;
				int row = i - column*MULTIPLIER;
				lastx = column;
				lasty = row;
				String tmpF[][] = f.clone();
				String sign = getPlayerSign();
				tmpF[column][row] = sign;
				getTurn(f);
				testWin();
				
				Deque<Integer> tmpDe = new LinkedList<Integer>();
				tmpDe.addAll(z);
				tmpDe.add(i);
				
				LinkedList<Integer> tmpLi = new LinkedList<Integer>();
				tmpLi.addAll(l1);
				tmpLi.remove(l1.indexOf(i));
				
				if(win){
					win = false;
					if( getWinnerSign().equals("O")){
						fillTree(tmpDe, tmpLi, 1.0, depth);
					} else if( getWinnerSign().equals("X")){
						fillTree(tmpDe, tmpLi, -1.0, depth);
					}
				} else{
					int depth2 = depth +1;
					calculateTree(tmpDe, tmpLi, tmpF, depth2);
				}
			}
		}
	}



	/*
	 * sumTree
	 * durchläuft den Baum von unten und addiert alle Werte der Kindknoten zum Wert des Elternknoten
	 * UND LÖSCHT DIESE DANN!!!
	 */
	private void sumTree(int z, int depth){
		Deque<Integer> tmpDe = new LinkedList<Integer>();
		tmpDe.add(z);
		sumTree(tmpDe, depth);
	}
	
	
	private void sumTree(Deque<Integer> z, int depth){
		if(bigTree.get(z).keySet().size() > 2){
			for(Deque<Integer> i : bigTree.get(z).keySet()){
				int depth2 = depth -1;
				sumTree(i, depth2);
			}
		} else {
			double sum = 0.0;
			for(Deque<Integer> i : bigTree.get(z).keySet()){
				for(Deque<Integer> j : bigTree.get(i).keySet()){
					sum += bigTree.get(i).get(j);
					bigTree.remove(j);
				}
				bigTree.get(z).put(i, sum);
			}
		}
	}



	/*
	 * fillTree
	 * füllt den Baum ab einem bestimmmten Knoten mit immer dem selben wert.
	 */
	private void fillTree(Deque<Integer> z, LinkedList<Integer> l1, double d, int depth){
		if(depth <= STOP && z.size() <= field.getSize()*field.getSize()){
			for(int i : l1){
				Deque<Integer> tmpDe = new LinkedList<Integer>();
				tmpDe.addAll(z);
				tmpDe.add(i);
				bigTree.get(z).put(tmpDe, d);
				
				LinkedList<Integer> tmpLi = new LinkedList<Integer>();
				tmpLi.addAll(l1);
				tmpLi.remove(l1.indexOf(i));
				int depth2 = depth +1;
				fillTree(tmpDe, tmpLi, d, depth2);
			}
		}
	}
	

	
	
	private int max(int z){
		int r = -100000;
		Deque<Integer> tmpDe = new LinkedList<Integer>();
		tmpDe.add(z);
		for(Deque<Integer> i : bigTree.get(tmpDe).keySet()){
			if(r < i.getLast()){
				r = i.getLast();
			}
		}
		return r;
	}


private int calculateNeedToWin() {
	if(field.getSize() < FIVEWINS) {
		return field.getSize();
	} 
	return needToWin;
}


private void getTurn(String[][] f){
	turn = field.getSize() - isFreeList(f).size();
}


private String getPlayerSign() {
	int result = turn % 2;
	if(result == 0) {
		return "X";
	} else {
		return "O";
	} 
}


private String getWinnerSign() {
	return winner;
}


private void testWin() {
	int horizontal = testWinHorizontal(lastx, lasty, 0, getPlayerSign(), true) 
			+ testWinHorizontal(lastx, lasty, 0, getPlayerSign(), false) + 1;
	int vertical = testWinVertical(lastx, lasty, 0, getPlayerSign(), true) 
			+ testWinVertical(lastx, lasty, 0, getPlayerSign(), false) + 1;
	int diagonal = testWinDiagonal(lastx, lasty, 0, getPlayerSign(), true) 
			+ testWinDiagonal(lastx, lasty, 0, getPlayerSign(), false) + 1;
	int diagonalReflected = testWinDiagonalReflected(lastx, lasty, 0, getPlayerSign(), true) 
			+ testWinDiagonalReflected(lastx, lasty, 0, getPlayerSign(), false) + 1;
	
	if(vertical >= needToWin || horizontal >= needToWin || diagonal >= needToWin ||
			diagonalReflected >= needToWin) {
		win = true;
		winner = getPlayerSign();
	}
}


//operator true => Minus
private int testWinHorizontal(int value, int fixValue, int n, String currentPlayer, 
		boolean operator) {
	int result = 0;
	if(value < 0 || value >= field.getSize() || 
			!field.getCellValue(value, fixValue).equals(currentPlayer) ) {
		return n-1;
	}
	
	if(operator) {
		result = testWinHorizontal(value-1, fixValue,n+1, currentPlayer, operator);
	} else {
		result = testWinHorizontal(value+1, fixValue,n+1, currentPlayer, operator);
	}
	return result;
}


//operator true => Minus
private int testWinVertical(int fixValue, int value, int n, String currentPlayer, 
		boolean operator) {
	int result = 0;
	if(value < 0 || value >= field.getSize() || 
			!field.getCellValue(fixValue, value).equals(currentPlayer) ) {
		return n-1;
	}
	
	if(operator) {
		result = testWinVertical(fixValue, value-1, n+1, currentPlayer, operator);
	} else {
		result = testWinVertical(fixValue, value+1, n+1, currentPlayer, operator);
	}
	return result;
}


//operator true => doppel --
private int testWinDiagonal(int value1, int value2, int n, String currentPlayer, 
		boolean operator) {
	int result = 0;
	if(value1 < 0 || value1 >= field.getSize() || value2 < 0 || value2 >= field.getSize() ) {
		return n-1;
	}
	if(!field.getCellValue(value1, value2).equals(currentPlayer) ) {
		return n-1;
	}
	
	if(operator) {
		result = testWinDiagonal(value1-1, value2-1,n+1, currentPlayer, operator);
	} else {
		result = testWinDiagonal(value1+1, value2+1,n+1, currentPlayer, operator);
	}
	return result;
}



//operator true => +-
	private int testWinDiagonalReflected(int value1, int value2, int n, String currentPlayer, 
			boolean operator) {
		int result = 0;
		if(value1 < 0 || value1 >= field.getSize() || value2 < 0 || value2 >= field.getSize() ) {
			return n-1;
		}
		if(!field.getCellValue(value1, value2).equals(currentPlayer)) {
			return n-1;
		}
		
		if(operator) {
			result = testWinDiagonalReflected(value1+1, value2-1,n+1, currentPlayer, operator);
		} else {
			result = testWinDiagonalReflected(value1-1, value2+1,n+1, currentPlayer, operator);
		}
		return result;
	}



	private String[][] arrayCopy(String[][] s, String[][] t){
		for (int i = 0; i < s.length; i++) {
		    System.arraycopy(s[i], 0, t[i], 0, s[0].length);
		}
		return t;
	}



	@Override
	public void updateField(Field field) {
		// TODO Auto-generated method stub
		
	}
	
	
}