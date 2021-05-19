package br.com.ale.realce.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidaUtil<T> {

	public void valida(T t) throws Exception {

		StringBuilder mensagem;

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();

		Set<ConstraintViolation<T>> validacoes = validator.validate(t);

		if (!validacoes.isEmpty()) {
			mensagem = new StringBuilder();
			for (ConstraintViolation<T> cv : validacoes) {
				mensagem.append(cv.getMessage()).append("\n");
			}
			throw new Exception(mensagem.toString());
		}
	}

	public void ehCadastrado(boolean ok, Object object) throws Exception {
		if (ok) {
			throw new Exception(object.getClass() + " possui cadastro");
		}
	}
}
