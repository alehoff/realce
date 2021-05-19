package br.com.ale.realce.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javafx.scene.control.TableView;

public class Util {

	/**
	 * Valida uma tableView informada verificando se possui registros e se um deles
	 * está selecionado
	 * 
	 * @param tabela
	 * @throws Exception
	 */
	public static void validaTableView(TableView<?> tabela) throws Exception {
		if (tabela.getItems().isEmpty()) {
			throw new Exception("Não existe itens para edição");
		}
		if (tabela.getSelectionModel().getSelectedIndex() < 0) {
			throw new Exception("Selecione um item para edição");
		}
	}

	/**
	 * Converte para String um valor BigDecimal informado
	 * 
	 * @param valor
	 * @return
	 */
	public static String toReal(BigDecimal valor) {
		try {
			return NumberFormat.getInstance(Locale.getDefault()).format(valor);
		} catch (Exception e) {
			return null;
		}
	}

	public static BigDecimal toBigDecimal(String valor){
		try {
			return new BigDecimal(valor.replace(",","."));
		}catch (Exception e){
			return null;
		}
	}

	/**
	 * Converte para Moeda um BigDecimal informado
	 * 
	 * @param valor
	 * @return
	 */
	public static String toMoeda(BigDecimal valor) {
		try {
			return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(valor);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Converte para Percentual um valor Double informado
	 * 
	 * @param valor
	 * @return
	 */
	public static String toPercentual(Double valor) {
		try {
			return NumberFormat.getPercentInstance(Locale.getDefault()).format(valor / 100);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Converte para Double um valor informado
	 * 
	 * @param valor
	 * @return
	 */
	public static Double toDouble(String valor) {
		try {
			return Double.valueOf(valor.replace(",", "."));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Converte para Integer um valor informado
	 * 
	 * @param valor
	 * @return
	 */
	public static Integer toInteger(String valor) {
		try {
			if (valor.isEmpty()) {
				return 0;
			}
			return Integer.valueOf(valor);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Retorna caixa/unidade para uma quantidade informada
	 * 
	 * @param quantidade
	 * @param atacado
	 * @return
	 */
	public static String toCaixaUnidade(Integer quantidade, Integer atacado) {
		if (quantidade == null) {
			return "00 / 00";
		}
		if (quantidade == 0) {
			return "00 / 00";
		}
		Integer caixa = (int) quantidade / atacado;
		Integer unidade = (quantidade % atacado);

		return caixa.toString() + " / " + unidade.toString();

	}

	/**
	 * Converte para BigDecimal um valor informado
	 * 
	 * @param valor
	 * @return
	 */
	public static BigDecimal stringParaBigDecimal(String valor) {
		try {
			return new BigDecimal(valor.replace(",", "."));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Converte para BigDecimal um valor informado
	 * 
	 * @param valor
	 * @return
	 */
	public static BigDecimal toBigDecimal(Integer valor) {
		try {
			return new BigDecimal(valor);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Retorna uma string para um valor informado
	 * 
	 * @param object
	 * @return
	 */
	public static String toString(Integer object) {
		try {
			return object.toString().replace(".", ",");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Calcula valor de caixa e unidade para um unico valor
	 * 
	 * @param caixa
	 * @param unidade
	 * @param atacado
	 * @return
	 */
	public static Integer getValorCaixaUnidade(Integer caixa, Integer unidade, Integer atacado) {

		if (caixa < 1) {
			return unidade;
		}
		return (caixa * atacado) + unidade;
	}

	public static String arredondaDouble(Double valor) {
		try {
			if (valor == null) {
				return "0.0";
			}
			if (valor < 1d) {
				return valor.toString();
			}
			return new DecimalFormat("0.00").format(valor);
		} catch (Exception e) {
			return "0.0";
		}
	}

	public static String toString(double margem) {
		try {
			return Double.toString(margem).replace(".", ",");
		} catch (Exception e) {
			return null;
		}
	}

	public static String toString(Long numero){
		try {
			return numero.toString();
		}catch (Exception e){
			return null;
		}
	}

	public static String toString(BigDecimal valorUnidade) {
		try {
			return valorUnidade.toString().replace(".", ",");
		} catch (Exception e) {
			return null;
		}
	}

	public static String formataNumeroPedido(Long numeroPedido){
		String numero = toString(numeroPedido);
		if(numero==null){
			return "0000";
		}
		int tamanho = numero.length();

		switch (tamanho){
			case 1:
				return "000"+numero;
			case 2:
				return "00"+numero;
			case 3:
				return "0"+numero;
			default:
				return numero;
		}
	}

}
