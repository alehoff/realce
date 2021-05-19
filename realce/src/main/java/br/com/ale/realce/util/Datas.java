package br.com.ale.realce.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Datas {

	private static SimpleDateFormat dateFormate;
	private static SimpleDateFormat timeFormate;

	static {
		dateFormate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		timeFormate = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault());
	}

	public static LocalDate getInicioMes() {

		Calendar cld = Calendar.getInstance();

		int mes = cld.get(Calendar.MONTH) + 1;
		int ano = cld.get(Calendar.YEAR);

		return LocalDate.of(ano, mes, 1);
	}

	public static LocalDate getDataAtual() {
		return LocalDate.now();
	}

	public static Date converteParaDate(LocalDate localDate) {
		try {
			Date atual = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			return atual;
		} catch (Exception e) {
			return new Date();
		}
	}

	public static Date getDataSistema() {
		return new Date();
	}

	public static String formata(Date data) {
		return dateFormate.format(data);
	}

	public static String formataHoraLocal(Date data) {
		return timeFormate.format(data);
	}

	public static boolean ehSuperior(Date inicio, Date termino) {

		return inicio.equals(termino) || inicio.before(termino);

	}

}
