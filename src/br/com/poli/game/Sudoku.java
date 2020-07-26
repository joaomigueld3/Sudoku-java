package br.com.poli.game;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import br.com.poli.exceptions.*;

public abstract class Sudoku extends Game {

	private double totalTime;
	protected Cell[][] gridPlayer;
	private Cell cell;
	private int[][] gridAnswer;
	public static int accounter;
	Random generator = new Random();

	public double getTotalTime() {

		if ((super.getEndTime().get(Calendar.HOUR_OF_DAY) - super.getStartTime().get(Calendar.HOUR_OF_DAY)) >= 1) {

			return (super.getEndTime().get(Calendar.HOUR_OF_DAY) - super.getStartTime().get(Calendar.HOUR_OF_DAY)) * 60
					+ (super.getEndTime().get(Calendar.MINUTE) - super.getStartTime().get(Calendar.MINUTE));
		}

		return (super.getEndTime().get(Calendar.MINUTE) - super.getStartTime().get(Calendar.MINUTE));

	} // retorna o tempo total

	public void endGame() {
		super.endGame();
		this.totalTime = getTotalTime();

	}

	public double autonomousTime() { // utilizado para calcular o tempo que o autonomo resolve o sudoku
		return (super.getEndTime().get(Calendar.MILLISECOND) - super.getStartTime().get(Calendar.MILLISECOND));

	}

	// inicializa o sudoku preenchido
	public Sudoku(Player player) {

		super(player);
		cell = new Cell(0, false, false);
		gridAnswer = new int[9][9];

		gridPlayer = new Cell[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {

				gridPlayer[i][j] = new Cell(0, false, false);
			}
		}

	}// fim construtor

	public int[][] getGridAnswer() {
		return gridAnswer;
	}

	public void setGridAnswer(int[][] gridAnswer) {
		this.gridAnswer = gridAnswer;
	}

	public Cell[][] getGridPlayer() {
		return gridPlayer;
	}

	public void setGridPlayer(Cell[][] gridPlayer) {
		this.gridPlayer = gridPlayer;
	}

	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	// M�todo para gerar um Sudoku Random v�lido!!!

	// M�todo para que ir� remover c�lulas do Sudoku aleat�riamentedependendo da
	// Dificuldade!!!

	public void setValue(int coordX, int coordY, int value)
			throws CellIsFixedException, CellValueException, GenericException {
		accounter+=1;
		cell.setValue(value);
		cell.setValid(false);

		/*
		 * if (checkValidation(new Cell(value, false, false), x, y) == true) { // se o
		 * numero recebido for true, printa // na matriz o numero // recebido que �
		 * v�lido, n�o fixo gridPlayer[x][y] = new Cell(value, true, false); }
		 */
		if (checkValidation(cell, coordX, coordY) == true) {

			gridPlayer[coordX][coordY].setValue(cell.getValue());

		}

	}

	private int findBeginingX(int x) {
		if (x <= 2) 	// se o x tiver entre a linha 0 e 2, ele retorna 0, que se refere a linha 0, que
						// � o grid 1,2,3
			return 0; 		// volta pra linha 0
		if (x <= 5) 			// grid 4,5,6
			return 3;
		else 			// grid 7,8,9
			return 6;
	}

	private int findBeginingY(int y) {
		if (y <= 2) 		// mesma coisa, s� que para a coluna
			return 0;
		if (y <= 5)
			return 3;
		else
			return 6;
	}

	public boolean checkValidation(Cell cell, int coordX, int coordY)
			throws CellIsFixedException, CellValueException, GenericException {
		// regras

		int value = cell.getValue(); // pega o valor da cell
		int positionFirstElementX = findBeginingX(coordX); // acha inicio recebe como paramentro a coord e
															// joga a fun��o que resolve o grid na vari�vel
		int positionFirstElementY = findBeginingY(coordY);

		/*
		 * if (!(gridPlayer[coordX][coordY].getValue() >0 &&
		 * gridPlayer[coordX][coordY].getValue() <10)) {
		 * 
		 * throw new CellValueException(this.getPlayer().getName() +
		 * ", Valor deve ser entre 1 e 9!!!"); // recebendo apenas numeros entre 1 e 9
		 * // return false; }
		 */
		if (cell.getValue() <= 0 || cell.getValue() >= 10) {

			throw new CellValueException(this.getPlayer().getName() + ", Valor deve ser >1 ou <9!!!");
		}

		else if (coordX < 0 || coordX > 8 || coordY < 0 || coordY > 8) {

			throw new GenericException("Erro: ");
		}

		else if (gridPlayer[coordX][coordY].isFixed() == true) { // se a posicao for fixa, retorna falso e
			// sai do metodo, n�o modificando a matriz
			throw new CellIsFixedException(
					this.getPlayer().getName() + ", voc� n�o pode sobreescrever um n�mero fixo.");
			// return false;
		}

		for (int i = 0; i <= 8; i++) { // percorre o array
			if (gridPlayer[i][coordY].getValue() == value) { // percorre a linha [i] e deixa a coluna coordY fixo.

				throw new CellValueException(this.getPlayer().getName() + ", Valor inv�lido para a Coluna!!!");
				// return false; //se os valores forem iguais retorna falso

			}
			if (gridPlayer[coordX][i].getValue() == value) { // percorre a linha [j] e deixa a coluna coordX fixo.

				throw new CellValueException(this.getPlayer().getName() + ", Valor inv�lido para a Linha!!!");
				// return false;
			}
		} // fim dos fors que verificam linhas e colunas

		for (int i = positionFirstElementX; i <= (positionFirstElementX + 2); i++) { // como achainicio(findBegining)
																						// sempre volta
			// pro come�o do grid o (+2), permite que ele verifique as outras posi��es
			for (int j = positionFirstElementY; j <= (positionFirstElementY + 2); j++) {
				if (gridPlayer[i][j].getValue() == value) {
					throw new CellValueException(this.getPlayer().getName() + ", Valor inv�lido para o Grid!!!");
					// return false;
				}
			} // fim do for para coluna
		} // fim do for para linha

		return true;
	}

	// cell.setValid(true);
	// return cell.isValid();

	// fim do checkValidation

	public boolean generateSudokuRandom(int line, int column) {

		if (line == 9) {		//quando chegar na linha 9, � porque terminou o jogo, retorna true.

			return true;
		} else {				//se n�o, tenta resolver

			boolean returns = false;

			ArrayList<Integer> list = new ArrayList<>();
			
			for (int i = 0; i < 9; i++) {

				if (returns == false) {		//quando returns � false, o jogo n�o est� terminado, portanto ele faz:

					int numberRandom;

					numberRandom = generator.nextInt(9) + 1;	//gera de 1 a 9 a cada itera��o
					
					list.add(numberRandom);		//adiciona o valor gerado � lista.
					
					if (list.size() == 9) {		//se a lista tiver 9 elementos
												//
						return returns;			
					}

					if (list.contains(numberRandom)) {	//se a lista cont�m o valor gerado...
						i--;
						
					}

					
					try {

						if (checkValidation(new Cell(numberRandom, false, false), line, column)==true) {
								//se o numberRandom (checkValidation) for v�lido seta o valor
							gridPlayer[line][column].setValue(numberRandom);

							if (column == 8) {		//se estiver na coluna 8, vai pra pr�xima linha e pra coluna 0

								returns = generateSudokuRandom(line + 1, 0);
							} else {		//se n�o for a 8, ele continua na mesma linha e vai pra pr�xima coluna

								returns = generateSudokuRandom(line, column + 1);
								
								//ele come�a em [0][0], cria o list e o numberRandom, adiciona o numberRandom
								//no list, entra no check, seta o numberRandom em [0][0], v� que n�o ta na coluna 8,
								//cai no else, chama o pr�prio m�todo, na mesma linha e passa pra pr�xima coluna
								
							}
						} else {		//se n�o for v�lido p�e 0, que cai na valueException
							gridPlayer[line][column].setValue(0);
						}
					} catch (CellIsFixedException | CellValueException | GenericException e) {

						gridPlayer[line][column].setValue(0);
					}
				}
			}
			return returns;
			
		}
		
	}

	public void removeCellRandom(int CellsZero) { 
		// Recebe a quantidade de casas zeradas de acordo com a dificuldade.

		for (int i = 0; i <= 8; i++) { // cada i do for � um grid

			if (i == 0) { // grid 0
				removeCellAux(CellsZero, 0, 0);

			} else if (i == 1) { // grid 1
				removeCellAux(CellsZero, 0, 3);

			} else if (i == 2) { // grid 2
				removeCellAux(CellsZero, 0, 6);
			} else if (i == 3) { // grid 3
				removeCellAux(CellsZero, 3, 0);

			} else if (i == 4) { // grid 4
				removeCellAux(CellsZero, 3, 3);

			} else if (i == 5) { // grid 5
				removeCellAux(CellsZero, 3, 6);

			} else if (i == 6) { // grid 6
				removeCellAux(CellsZero, 6, 0);

			} else if (i == 7) { // grid 7
				removeCellAux(CellsZero, 6, 3);

			} else if (i == 8) { // grid 8
				removeCellAux(CellsZero, 6, 6);

			}
		}
	}

	public void removeCellAux(int amountOfZeros, int addK, int addY) { 
		/*addK e addY recebem valores (0,3,6) a serem adicionados ao k,y que s�o �ndices de gridPlayer, 
		*para chegar aos outros grids do sudoku. Ex: no 4 grid k e y est�o entre 3 e 5, assim o generator 
		*gera de 0 a 2 que somado a 3 a 5 engloba o 4 grid.
		*/ 
		int a = 0;
		int k = 0;
		int y = 0;
		while (a < amountOfZeros) { // faz com que at� que tenham x(5,6,7) quantidade de casas geradas ele continue 
									//	tentando gerar posi��es a serem zeradas
									
			k = generator.nextInt(3); //gera de 0 a 2, que � o 1� grid
			y = generator.nextInt(3);
			k += addK;				//soma o n�mero aleat�rio com o par�metro recebino no removeCellRandom()
			y += addY;				//de acordo com cada grid

			if (gridPlayer[k][y].getValue() != 0) { 	//se o n�mero da posi��o for != 0 seta falso
				gridPlayer[k][y].setValue(0);
				gridPlayer[k][y].setFixed(false);
			} else {								//se ele for 0, ele tenta de novo at� n�o ser zero
				amountOfZeros += 1;
			}
			a++;
		}

	}

	

	public abstract void initilizeSudoku(); // inicializa o sudoku dependendo da dificuldade

	@Override
	public String toString() {
		// getTotalTime();
		// initializaSudoku(difficulty.NORMAL);

		String s = "";
		for (int i = 0; i < 9; i++) {
			if (i % 3 == 0 && i != 0)
				s = s + '\n';
			for (int j = 0; j < 9; j++) {
				if (j % 3 == 0)
					s += "  ";

				if (gridPlayer[i][j] == null) { // se o conteudo de gridplayer na posi��o i,j for nulo printa espa�o 0
					s = s + "0";

				} else {
					s = s + "" + gridPlayer[i][j]; // se n�o printa o conteudo de gridplayer

				}

			}
			s = s + '\n';
		}

		return s;
	}

}
