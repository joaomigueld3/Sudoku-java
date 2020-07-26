package br.com.poli.game;

import br.com.poli.exceptions.CellIsFixedException;

import br.com.poli.exceptions.CellValueException;
import br.com.poli.exceptions.GenericException;
import main.TestSudoku;

public class Teste {
	// Jo�o Miguel Descendente, Jo�o Vitor Santos
	public static void main(String[] args) throws GenericException, CellIsFixedException, CellValueException {
		Player player = new Player("jo�o");
		SudokuEasy s1 = new SudokuEasy(player);
		SudokuNormal s2 = new SudokuNormal(player);
		SudokuHard s3 = new SudokuHard(player);
		s1.startGame();
		s1.getStartTime();
		s1.endGame();
		AutonomousPlayer auto = new AutonomousPlayer(s3);
		
		
		
		System.out.println(); // printa o sudoku completo
		
		/*
		 * s1.setValue(0, 0, 3); //formato: (linha, coluna, valor) s1.setValue(1, 0, 4);
		 * //n�o pode pois, j� tem 4 na coluna s1.setValue(2, 8, 1); //n�o pode pela
		 * regra da linha s1.setValue(1, 2, 3); //n�o pode pela regra do grid
		 * s1.setValue(0, 4 ,1); //n�o pode sobreescrever numero fixo s1.setValue(1, 2,
		 * 10);//n�o pode, pois � maior que 9.
		 */

		s3.initilizeSudoku();

		// System.out.println("Player:" + " "+ s1.getPlayer().getName() + " "+
		// s1.getTotalTime() + " ms");
		System.out.println(s3.toString() + " ");
		System.out.println();
		auto.executeGame();
		System.out.println(s3.toString());
		
		System.out.println(Sudoku.accounter);
		String retorno = TestSudoku.execute(auto);
		System.out.println(retorno);
		
		// printa a matriz
		//s1.autonomousTime();
		//s2.setValue(0, 9, 2);
		//System.out.println(s2.toString() + " ");
	}
}
