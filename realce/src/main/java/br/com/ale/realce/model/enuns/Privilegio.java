package br.com.ale.realce.model.enuns;

public enum Privilegio {

	USUARIO("Usuário"), ADMIN("Administrador"), A_DEFINIR("A definir"),BLOQUEADO("Bloqueado");

	private String privilegio;

	private Privilegio(String privilegio) {
		this.privilegio = privilegio;
	}

	public String getPrivilegio() {
		return privilegio;
	}

}
