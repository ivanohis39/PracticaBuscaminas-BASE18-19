import java.util.ArrayList;
import java.util.Random;

/**
 * Clase gestora del tablero de juego. Guarda una matriz de enteros representado
 * el tablero. Si hay una mina en una posici√≥n guarda el n√∫mero -1 Si no hay
 * una mina, se guarda cu√°ntas minas hay alrededor. Almacena la puntuaci√≥n de
 * la partida
 * 
 * @author ivan hisado
 *
 */
public class ControlJuego {

	private final static int MINA = -1;
	final int MINAS_INICIALES = 20;
	final int LADO_TABLERO = 10;

	private int[][] tablero;
	private int puntuacion = 0;

	public ControlJuego() {
		// Creamos el tablero:
		tablero = new int[LADO_TABLERO][LADO_TABLERO];

		// Inicializamos una nueva partida
		inicializarPartida();
	}

	public int generarAleatorio() {
		Random rd = new Random();
		int aleat = rd.nextInt(LADO_TABLERO);
		return aleat;
	}

	/**
	 * MÈtodo para generar un nuevo tablero de partida:
	 * 
	 * @pre: La estructura tablero debe existir.
	 * @post: Al final el tablero se habr·° inicializado con tantas minas como
	 *        marque la variable MINAS_INICIALES. El resto de posiciones que no son
	 *        minas guardan en el entero cuantas minas hay alrededor de la celda
	 */

	public void inicializarPartida() {

		puntuacion = 0;
		// TODO: Repartir minas e inicializar puntaciÛn. Si hubiese un tablero anterior,
		// lo pongo todo a cero para inicializarlo.

		// INTRODUCIMOS UN NUMERO EN EJE DE LAS X y EN DE LAS Y
		int x = 0, y = 0;
		int cuentaMinas = 0;
		do {
			x = generarAleatorio(); // PARA EL EJE X
			y = generarAleatorio(); // PARA EL EJE Y

			if (abrirCasilla(x, y)) {
				tablero[x][y] = MINA;
				cuentaMinas++;
			}
		} while (cuentaMinas < MINAS_INICIALES);

		// Al final del mÈtodo hay que guardar el n˙mero de minas para las casillas que
		// no son mina:
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j] != MINA) {
					tablero[i][j] = calculoMinasAdjuntas(i, j);

				}
			}
		}
	}

	/**
	 * Calculo de las minas adjuntas: Para calcular el numero de minas tenemos que
	 * tener en cuenta que no nos salimos nunca del tablero. Por lo tanto, como
	 * mucho la i y la j valdran LADO_TABLERO-1. Por lo tanto, como poco la i y la j
	 * valdran 0.
	 * 
	 * @param i: posicion vertical de la casilla a rellenar
	 * @param j: posicion horizontal de la casilla a rellenar
	 * @return : El numero de minas que hay alrededor de la casilla [i][j]
	 **/

	private int calculoMinasAdjuntas(int i, int j) {
		int cuentaMinas = 0; // INICIAMOS EL CONTADOR DE LAS MINAS

		for (int x = -1; x < 2; x++) { // INICIAMOS LAS X EN -1, Y PODRA VALER ” -1 ” 0
			for (int y = -1; y < 2; y++) { // INICIAMOS LAS Y EN -1, Y PODRA VALER ” -1 ” 0

				// ESTO LO HACEMOS PARA QUE NO SE NOS SALGA DE LOS LIMITES DEL TABLERO
				// Y PARA QUE NOS A—ADA EN LOS BORDES TAMBIEN EL VALOR DE LAS MINAS

				if (((i + x) >= 0 && i + x < LADO_TABLERO) && ((j + y) >= 0 && j + y < LADO_TABLERO)) {
					if (this.tablero[i + x][j + y] == MINA) {
						cuentaMinas++;
					}
				}
			}
		}

		return cuentaMinas;
	}

	/**
	 * Metodo que nos permite
	 * 
	 * @pre : La casilla nunca debe haber sido abierta antes, no es controlado por
	 *      el ControlJuego. Por lo tanto siempre sumaremos puntos
	 * @param i: posicion verticalmente de la casilla a abrir
	 * @param j: posicion horizontalmente de la casilla a abrir
	 * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
	 */
	public boolean abrirCasilla(int i, int j) {
		// SI NO ES UNA MINA DEVUELVE VERDADERO Y SE SIGUE JUGANDO
	if(this.tablero[i][j] == MINA) {
		puntuacion++;
		return false;
	}
	return true;
		//return (this.tablero[i][j] == MINA ? false : true);
	}

	/**
	 * Metodo que checkea si se ha terminado el juego porque se han abierto todas
	 * las casillas.
	 * 
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son
	 *         minas.
	 **/
	public boolean esFinJuego() {
		// CUANDO SE LLEGA AL NUMERO MAXIMO DE CASILLAS SE ACABA EL JUEGO
		return (puntuacion == ((LADO_TABLERO * LADO_TABLERO) - MINAS_INICIALES) ? true : false);
	}

	/**
	 * Metodo que pinta por pantalla toda la informacion del tablero, se utiliza
	 * para depurar
	 */
	public void depurarTablero() {
		System.out.println("---------TABLERO--------------");
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("\nPuntuacion: " + puntuacion);
	}

	/**
	 * Metodo que se utiliza para obtener las minas que hay alrededor de una celda
	 * 
	 * @pre : El tablero tiene que estar ya inicializado, por lo tanto no hace falta
	 *      calcularlo, simplemente consultarlo
	 * @param i : posicion vertical de la celda.
	 * @param j : posicion horizontal de la cela.
	 * @return Un entero que representa el numero de minas alrededor de la celda
	 */
	public int getMinasAlrededor(int i, int j) {
		return tablero[i][j];
	}

	/**
	 * Metodo que devuelve la puntuacion actual
	 * 
	 * @return Un entero con la puntuacion actual
	 */
	public int getPuntuacion() {

		return puntuacion;
	}

	public int[][] getTablero() {
		return tablero;
	}

	public void setTablero(int[][] tablero) {
		this.tablero = tablero;
	}

	

}
