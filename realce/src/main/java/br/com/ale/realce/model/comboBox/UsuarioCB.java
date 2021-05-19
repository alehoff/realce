package br.com.ale.realce.model.comboBox;

/**
 * @author mbs-6
 *
 */
public class UsuarioCB {

	private long id;
	private String usuario;

	/**
	 * @param id
	 * @param usuario
	 */
	public UsuarioCB(long id, String usuario) {
		super();
		this.id = id;
		this.usuario = usuario;
	}

	public long getId() {
		return id;
	}

	public String getUsuario() {
		return usuario;
	}

	@Override
	public String toString() {
		return getUsuario();
	}

}
