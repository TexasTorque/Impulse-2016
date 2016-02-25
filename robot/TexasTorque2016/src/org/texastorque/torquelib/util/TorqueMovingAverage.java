package org.texastorque.torquelib.util;

import java.util.ArrayList;

public class TorqueMovingAverage {
	
	private int numberOfMoves = 0;
	private ArrayList<Double> previousMoves = new ArrayList(100);	
	
	public double getAverage(){
		int denominator = 0;
		double numerator = 0;
		for(double move : previousMoves){
			if(move != 0.0)
				denominator++;
			numerator += move;
		}
		return numerator / denominator;
	}
	
	public void addMove(double move){
		if(numberOfMoves < previousMoves.size()){
			previousMoves.add(move);
			numberOfMoves++;
		}
		else {
			previousMoves.remove(0);
			previousMoves.add(move);
		}
	}

}
