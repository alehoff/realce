package br.com.ale.realce.sessao;

import br.com.ale.realce.model.dao.UsuarioDao;
import br.com.ale.realce.model.entidade.Usuario;
import br.com.ale.realce.model.enuns.Privilegio;
import br.com.ale.realce.util.Md5;
import javafx.scene.control.Label;

public class UsuarioSessao {

	private static Integer acessos = 3;
	private static Usuario usuario = null;

	public static void conecta(Usuario usuario1) {
		usuario = usuario1;
	}

	public static void confereSenha(String senha, Label mensagem) throws Exception {
		if (senha.isEmpty()) {
			throw new Exception("favor informar a senha");
		}
		if (!usuario.getSenha().equals(Md5.criptografar(senha))) {
			acessos--;
			mensagem.setText("Você possui " + acessos + " tentativas antes de ser bloqueado");

			if (acessos < 1) {
				mensagem.setText("");
				usuario.setPrivilegio(Privilegio.BLOQUEADO);
				new UsuarioDao().editar(usuario);
			}

			throw new Exception("Usuário/Senha inválidos");
		}
	}

	public static void usuarioEhCadastrado() throws Exception {
		if (null == usuario) {
			throw new Exception("Usuário não cadastrado");
		}
	}

	public static void usuarioApto() throws Exception {

		if (usuario.getPrivilegio().equals(Privilegio.A_DEFINIR)) {
			throw new Exception("Acesso ao sistema em análise");
		}

		if (usuario.getPrivilegio().equals(Privilegio.BLOQUEADO)) {
			throw new Exception("Usuário bloqueado após 3 tentativas de acesso");
		}
	}

	public static void desconecta() {
		usuario = null;
	}

	public static Usuario getUsuario() {
		return usuario;
	}
	
	/**
	 * Informa se usuário sessão é um administrador
	 * @return
	 */
	public static boolean possuiAcesso() {		
		return usuario.getPrivilegio().equals(Privilegio.ADMIN);
	}

}
