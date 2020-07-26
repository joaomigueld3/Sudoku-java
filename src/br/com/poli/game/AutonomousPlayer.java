package br.com.poli.game;

import br.com.poli.exceptions.*;

import java.util.Calendar;
import java.util.Random;

public class AutonomousPlayer {

	private Sudoku sudoku;
	Random generator = new Random();

	public Sudoku getSudoku() {
		return sudoku;
	}

	public void setSudoku(Sudoku sudoku) {
		this.sudoku = sudoku;
	}

	public AutonomousPlayer(Sudoku sudoku) {

		this.sudoku = sudoku;
	}

	
    public int executeMove(int add) {	//joga um valor para o execute game.
    									//vai receber um numero de 0 a 9 do for abaixo.

    	int number = 1;		//tenta 1, tenta 2,3,4...
		return number + add;

    }

    public void executeGame() {
    	executeGameAux(0, 0);
    }
    
	//M�todo auxiliar de executeGame
	public boolean executeGameAux( int line, int column) {

		if (line == 9) { //quando chegar na linha 9, � porque terminou o jogo, retorna true.
			return true;
		} else {		//enquanto n�o chegar, ainda n�o terminou
			boolean returns = false;

			//Checar caso a posi��o tenha Fixed == true
			if (sudoku.getGridPlayer()[line][column].isFixed() == true) {
				if (column == 8) {		//se estiver na coluna 8, vai pra pr�xima linha e pra coluna 0
					return executeGameAux(line + 1, 0);
				} else {				//se n�o for a 8, ele continua na mesma linha e vai pra pr�xima coluna
					return executeGameAux(line, column + 1);
				}
			}
			//Looping para a quantidade de testes de valores do Number
			for (int i = 0; i < 10; i++) {

				if (returns == false) { 	//se a linha !=9, quer dizer que n�o resolveu ainda.
					
					try {
						//Se n�o for fixo, checa se � v�lido.
						sudoku.setValue(line, column, executeMove(i)); //se for v�lido seta.

							if (column == 8) {	
								returns = executeGameAux(line + 1, 0);
							} else {
								returns = executeGameAux(line, column + 1);
							}
						
						}
					 catch (CellIsFixedException | CellValueException e) {
						sudoku.getGridPlayer()[line][column].setValue(0);
					}
				}
			}
			//Retorno dependendo da situa��o.
			return returns;
		}
	}
	
	public boolean AutoMove(Sudoku sudoku) {
		int positionX;
        int positionY;

        positionX = generator.nextInt(9);
        positionY = generator.nextInt(9);

        
        if(sudoku.getGridPlayer()[positionX][positionY].isFixed() == true) {	//pega uma posi��o fixa do gridResposta
        													//que armazena o sudoku gerado
            return AutoMove(sudoku);
        }
        //Se n�o, seta o valor de gridAnswer[positionX][positionY] em gridPlayer!!!
        else {
            sudoku.getGridPlayer()[positionX][positionY].setValue(sudoku.getGridAnswer()[positionX][positionY]);
            return true;
        }
	}
	
	
}

