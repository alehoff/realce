package br.com.ale.realce.model.enuns;

public enum FormaPagamento {
	
	DINHEIRO("Dinheiro"),
	CARTAO("Cartão"),
	CHEQUE("Cheque");
	
	private String formaPagamento;
	
	private FormaPagamento(String forma) {
		this.formaPagamento = forma;
	}
	
	public String getFormaPagamento() {
		return formaPagamento;
	}

}
