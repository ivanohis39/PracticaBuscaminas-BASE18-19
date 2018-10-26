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
	private int puntuacion;

	public ControlJuego() {
		// Creamos el tablero:
		tablero = new int[LADO_TABLERO][LADO_TABLERO];

		// Inicializamos una nueva partida
		inicializarPartida();
	}

	public int generarAleatorio() {
		Random rd = new Random();
		int aleat = rd.nextInt(10);
		return aleat;
	}

	/**
	 * MÈtodo para generar un nuevo tablero de partida:
	 * 
	 * @pre: La estructura tablero debe existir.
	 * @post: Al final el tablero se habr·° inicializado con tantas minas como
	 *        marque la variable MINAS_INICIALES. El resto de posiciones que no son
	 *        minas guardan en el entero cu√°ntas minas hay alrededor de la celda
	 */

	public void inicializarPartida() {

		// INTRODUCIMOS UN NUMERO EN EJE DE LAS X y EN DE LAS Y
		int x = 0, y = 0;

		// TODO: Repartir minas e inicializar puntaciÛn. Si hubiese un tablero anterior,
		// lo pongo todo a cero para inicializarlo.

		for (int i = 0; i < MINAS_INICIALES; i++) {
			int r = generarAleatorio(); // PARA EL EJE X
			int m = generarAleatorio(); // PARA EL EJE Y

			// EN CASO DE QUE SEAN IGUALES VOLVEMOS A GENERAS LOS ALEATORIOS
			while (tablero[x][y] == tablero[r][m]) {
				r = generarAleatorio(); // GENERAMOS NUEVOS ALEATORIOS
				m = generarAleatorio();
			}
			// SI SON DIRERENTRES SE LO A—ADIMOS A LOS RESPECTIVOS EJES
			x = r;
			y = m;
			tablero[x][y] = MINA;
		}

		// Al final del mÈtodo hay que guardar el n˙mero de minas para las casillas que
		// no son mina:
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j] != MINA) {
					// SI LA CASILLA NO ES UNA MINA LE A—ADIMOS EL VALOR DE LAS MINAS
					// QUE TIENE ALREDEDOR
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
	 * @param i:
	 *            posicion vertical de la casilla a rellenar
	 * @param j:
	 *            posicion horizontal de la casilla a rellenar
	 * @return : El numero de minas que hay alrededor de la casilla [i][j]
	 **/
	private int calculoMinasAdjuntas(int i, int j) {
		int cuentaMinas = 0; // INICIAMOS EL CONTADOR DE LAS MINAS
		for (i = 0; i < tablero.length-1; i++) {
			for (j = 0; j < tablero[i].length-1; j++) {
				if (tablero[i][j] == MINA) {
					cuentaMinas++;
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
	 * @param i:
	 *            posicion verticalmente de la casilla a abrir
	 * @param j:
	 *            posicion horizontalmente de la casilla a abrir
	 * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
	 */
	public boolean abrirCasilla(int i, int j) {
		boolean noExplota = true;
		// SI LA CASILLA ABIERTA ES UNA MINA ENVIA FALSO Y POR LO TANTO EXPLOTA
		// MIENTRAS NO SEA UNA MINA NOS ENVIARA TRUE Y POR LO TANTO NO EXLOTARA
		if (tablero[i][j] == MINA) {
			noExplota = false;
		}

		return noExplota;
	}

	/**
	 * Metodo que checkea si se ha terminado el juego porque se han abierto todas
	 * las casillas.
	 * 
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son
	 *         minas.
	 **/
	public boolean esFinJuego() {
		boolean esFIN = false;
		int cuentaAcierto = 0;
		
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j] != MINA) {
					cuentaAcierto++;
				}
			}
		}
		if (cuentaAcierto == 80) {
			esFIN = true;
		}
		return esFIN;
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
	 * @param i
	 *            : posicion vertical de la celda.
	 * @param j
	 *            : posicion horizontal de la cela.
	 * @return Un entero que representa el numero de minas alrededor de la celda
	 */
	public int getMinasAlrededor(int i, int j) {
		int cuentaMinas = 0;
		if (tablero[i][j] == MINA) {
			cuentaMinas++;
		}
		return cuentaMinas;
	}

	/**
	 * Metodo que devuelve la puntuacion actual
	 * 
	 * @return Un entero con la puntuacion actual
	 */
	public int getPuntuacion() {
		puntuacion = 0;
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j] != MINA) {
					puntuacion++;
				}
			}
		}
		return puntuacion;
	}

}
