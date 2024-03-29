package br.com.marcospcruz.controleestoque.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import br.com.marcospcruz.controleestoque.util.ConstantesEnum;
import br.com.marcospcruz.controleestoque.util.NumberDocument;
import br.com.marcospcruz.controleestoque.view.util.MyTableModel;

public abstract class AbstractDialog extends JDialog implements ActionListener,
		MouseListener {

	protected static final String NOVO_BUTTON_LBL = "Novo";

	protected static final String EXCLUIR_BUTTON_LBL = "Excluir";

	protected static final String SALVAR_BUTTON_LBL = "Salvar";

	protected static final int TXT_HEIGHT = 20;

	protected static final String ITEM_ZERO_COMBO = ConstantesEnum.SELECIONE_PRIMEIRA_MSG
			.getValue().toString();

	protected static final String TITLE_CONFIRMING_DELETE = ConstantesEnum.CONFIRMACAO_EXCLUSAO_TITLE
			.getValue().toString();

	protected static final String MESSAGE_CONFIRMING_DELETE = ConstantesEnum.CONFIRMACAO_EXCLUSAO
			.getValue().toString();

	private JButton btnNovo;
	private JButton btnSalvar;
	protected boolean acaoBuscar;
	protected JButton btnDeletar;
	protected JPanel jPanelBusca;
	protected JButton btnBusca;
	protected JPanel jPanelFormulario;
	protected JPanel jPanelActions;
	protected JScrollPane jScrollPane;
	protected JPanel jPanelTable;
	protected JFormattedTextField txtBusca;
	protected JFormattedTextField txtDescricao;
	protected JTable jTable;

	protected MyTableModel myTableModel;

	protected static final Border BUSCAR_TITLED_BORDER = new TitledBorder(
			"Busca");

	private static final String TITLE_CONFIRMING_SAVING = ConstantesEnum.CONFIRMACAO_SALVAMENTO_TITLE
			.getValue().toString();;

	private static final String MESSAGE_CONFIRMING_SAVING = ConstantesEnum.CONFIRMACAO_SALVAMENTO
			.getValue().toString();

	public AbstractDialog(JFrame owner, String tituloJanela, boolean modal) {

		super(owner, tituloJanela, modal);

		setSize(new Dimension(600, 400));

		setDefaultCloseOperation(HIDE_ON_CLOSE);

		setLayout(null);

	}

	/**
	 * xxx
	 */
	protected abstract void configuraJPanel();

	protected abstract JPanel carregaJPanelBusca();

	protected abstract void selecionaAcao(String actionCommand)
			throws Exception;

	protected abstract JPanel carregaJpanelFormulario();

	protected abstract JPanel carregaJpanelTable(int y);

	protected abstract void atualizaTableModel(Object object);

	protected abstract void carregaTableModel();

	protected abstract List carregaLinhasTableModel(List lista);

	/**
	 * 
	 * @throws Exception
	 */
	protected abstract void excluiItem() throws Exception;

	protected abstract void salvarItem() throws Exception;

	protected abstract void populaFormulario();

	/**
	 * 
	 * @return
	 */
	protected JPanel carregaJpanelActions() {

		JPanel jPanel = new JPanel();

		jPanel.setBorder(new TitledBorder(""));

		jPanel.setPreferredSize(new Dimension(getWidth(), 50));

		btnNovo = inicializaJButton(NOVO_BUTTON_LBL, 10, 100, 130, 50);

		jPanel.add(btnNovo);

		btnSalvar = inicializaJButton(SALVAR_BUTTON_LBL, 10, 100, 130, 50);

		jPanel.add(btnSalvar);

		btnDeletar = inicializaJButton(EXCLUIR_BUTTON_LBL, 10, 100, 130, 50);

		btnDeletar.setEnabled(false);

		jPanel.add(btnDeletar);

		return jPanel;

	}

	/**
	 * M�todo respons�vel em inicializar JButton.
	 * 
	 * @param text
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	protected JButton inicializaJButton(String text, int x, int y, int width,
			int height) {

		JButton jButton = inicializaJButton(text);

		jButton.setBounds(x, y, width, height);

		return jButton;

	}

	protected JButton inicializaJButton(String text) {

		JButton jButton = new JButton(text);

		jButton.addActionListener(this);

		return jButton;
	}

	protected void carregaTableModel(List linhas, Object[] colunas) {

		myTableModel = new MyTableModel(linhas, colunas);

		myTableModel.fireTableDataChanged();

	}

	public JTable inicializaJTable() {

		JTable jTable = new JTable(myTableModel);

		jTable.addMouseListener(this);

		return jTable;

	}

	protected JFormattedTextField inicializaNumberField() {

		JFormattedTextField txtField = new JFormattedTextField();

		txtField.setDocument(new NumberDocument());

		txtField.setColumns(10);

		return txtField;
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @param title
	 * @param message
	 * @return
	 */
	protected int confirmaExclusaoItem() {

		return showConfirmationMessage(TITLE_CONFIRMING_DELETE,
				MESSAGE_CONFIRMING_DELETE);
	}

	private int showConfirmationMessage(String titleConfirmationDialog,
			String confirmationQuestion) {
		// TODO Auto-generated method stub
		return JOptionPane.showConfirmDialog(null, confirmationQuestion,
				titleConfirmationDialog, JOptionPane.YES_NO_OPTION);
	}

	protected int confirmaSalvamentoItem() {

		return showConfirmationMessage(TITLE_CONFIRMING_SAVING,
				MESSAGE_CONFIRMING_SAVING);

	}

	public void mostraMensagemConfirmacaoSalvamento() {
		// TODO Auto-generated method stub

		JOptionPane.showMessageDialog(this,
				ConstantesEnum.CONFIRMACAO_REGISTRO_ATUALIZADO.getValue()
						.toString(),
				ConstantesEnum.CONFIRMANDO_ATUALIZACAO_MSG_TITLE.getValue()
						.toString(), JOptionPane.INFORMATION_MESSAGE);

	}
}
