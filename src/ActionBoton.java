import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que implementa el listener de los botones del Buscaminas.
 * De alguna manera tendr� que poder acceder a la ventana principal.
 * Se puede lograr pasando en el constructor la referencia a la ventana.
 * Recuerda que desde la ventana, se puede acceder a la variable de tipo ControlJuego
 * @author ivan hisado
 **
 */
public class ActionBoton implements ActionListener{

	private int i;
	private int j;
	private VentanaPrincipal ventana;

	public ActionBoton(VentanaPrincipal v, int iInt, int jInt) {
			this.ventana = v;
			this.i = iInt;
			this.j = jInt;
	}
	
	/**
	 * Acci�n que ocurrir� cuando pulsamos uno de los botones.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ventana.mostrarNumMinasAlrededor(i, j);
		ventana.actualizarPuntuacion();
	}

}