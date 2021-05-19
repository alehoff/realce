package br.com.ale.realce.model.enuns;

public enum Operacao {
	
	CREDITO("Crédito"),
	DEBITO("Débito");
	
	private String operacao;
	
	private Operacao(String op) {
		this.operacao = op;
	}
	
	public String getOperacao() {
		return operacao;
	}

}
