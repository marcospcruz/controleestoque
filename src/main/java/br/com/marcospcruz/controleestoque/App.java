package br.com.marcospcruz.controleestoque;

import java.io.File;

import br.com.marcospcruz.controleestoque.view.PrincipalGUI;

public class App {

	public static final String CONTROLE_ESTOQUE_HOME = "C:\\controleestoque";
	private static final String DB_HOME = CONTROLE_ESTOQUE_HOME + "\\db";

	public static void main(String args[]) {

		initApp();

	}

	/**
	 * s
	 */
	private static void initApp() {

		// File file = new File(CONTROLE_ESTOQUE_HOME);
		//
		// if (!file.exists()) {
		//
		// file.mkdir();
		//
		// File subdir = new File(DB_HOME);
		//
		// subdir.mkdir();
		//
		// }

		new PrincipalGUI("Controle de Estoque");

	}

}
