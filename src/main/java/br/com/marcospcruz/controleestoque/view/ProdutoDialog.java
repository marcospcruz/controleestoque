package br.com.marcospcruz.controleestoque.view;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.hibernate.LazyInitializationException;

import br.com.marcospcruz.controleestoque.controller.ProdutoController;
import br.com.marcospcruz.controleestoque.model.Produto;
import br.com.marcospcruz.controleestoque.model.SubTipoProduto;
import br.com.marcospcruz.controleestoque.model.TipoProduto;
import br.com.marcospcruz.controleestoque.util.ConstantesEnum;
import br.com.marcospcruz.controleestoque.util.MyFormatador;
import br.com.marcospcruz.controleestoque.util.NumberDocument;

public class ProdutoDialog extends AbstractDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2443699410031970101L;

	private static Object[] COLUNAS_TABLE_MODEL = {
			ConstantesEnum.CODIGO_LABEL.getValue().toString(),
			ConstantesEnum.TIPO_PRODUTO_LABEL.getValue().toString(),
			ConstantesEnum.DESCRICAO_LABEL.getValue().toString(),
			ConstantesEnum.UNIDADE_MEDIDA_LABEL.getValue().toString(),
			ConstantesEnum.VALOR_UNITARIO_LABEL.getValue().toString() };

	private static final String DESCRICAO_LABEL = (String) COLUNAS_TABLE_MODEL[2]
			+ ":";

	private static final String TIPO_PRODUTO_LABEL = COLUNAS_TABLE_MODEL[1]
			+ ":";

	private static final String VALOR_UNITARIO_LABEL = COLUNAS_TABLE_MODEL[4]
			+ ":";

	private ProdutoController controller;
	private JFormattedTextField txtUnidadeMedida;
	private JLabel lblValorUnitario;
	private JFormattedTextField txtValorUnitario;

	private JComboBox cmbTiposDeProduto;
	private JComboBox cmbSubTiposDeProduto;

	public ProdutoDialog(JFrame owner) {

		super(owner, "Cadastro de Produtos", true);

		controller = new ProdutoController();

		configuraJPanel();

		setVisible(true);

	}

	public void actionPerformed(ActionEvent arg0) {

		String actionCommand = arg0.getActionCommand();

		if (arg0.getSource() instanceof JComboBox) {

			cmbSubTiposDeProduto
					.setModel(selectModelSubTiposDeProduto((JComboBox) arg0
							.getSource()));

		} else {

			try {

				selecionaAcao(actionCommand);

			} catch (LazyInitializationException e) {

				e.printStackTrace();

			} catch (Exception e) {

				e.printStackTrace();

				JOptionPane.showMessageDialog(null, e.getMessage(), "Alerta",
						JOptionPane.ERROR_MESSAGE);

			} finally {

				atualizaTableModel(controller.getProdutos());

			}

		}

		acaoBuscar = false;

	}

	/**
	 * 
	 * @param combo
	 * @return
	 */
	private ComboBoxModel selectModelSubTiposDeProduto(JComboBox combo) {

		DefaultComboBoxModel model = null;

		if (combo.getSelectedIndex() > 0)

			model = carregaSubTiposProdutoModel(combo.getSelectedItem());

		else

			model = carregaSubTiposProdutoModel(new SubTipoProduto());

		return model;

	}

	@Override
	protected void selecionaAcao(String actionCommand) throws Exception {

		if (actionCommand.equals(SALVAR_BUTTON_LBL)) {

			salvarItem();

		} else if (actionCommand.equals(NOVO_BUTTON_LBL)) {

			limpaFormulario();

			btnDeletar.setEnabled(false);

		} else if (actionCommand.equals(EXCLUIR_BUTTON_LBL)) {

			excluiItem();

		} else {

			acaoBuscar = true;

			limpaFormulario();

			controller.busca(txtBusca.getText());

			populaFormulario();

		}

	}

	@Override
	protected void excluiItem() throws Exception {

		int confirmacao = confirmaExclusaoItem();

		if (confirmacao == 0) {

			controller.deletaProduto();

			limpaFormulario();

			btnDeletar.setEnabled(false);

		}

	}

	private void validaValorUnitario(String valorUnitario) throws Exception {

		int contadorVirgula = 0;

		for (int i = 0; i < valorUnitario.length(); i++) {

			if (valorUnitario.charAt(i) == ',')

				contadorVirgula++;

		}

		if (contadorVirgula > 1) {

			throw new Exception(COLUNAS_TABLE_MODEL[4] + " inválido!");

		}

	}

	/**
	 * x
	 * 
	 */
	private void limpaFormulario() {

		if (!acaoBuscar) {

			controller.setProduto(null);

			controller.setPecasRoupa(null);

			txtBusca.setText("");

			cmbTiposDeProduto.setModel(carregaComboTiposProdutoModel());

			cmbTiposDeProduto.setSelectedIndex(0);

			cmbSubTiposDeProduto
					.setModel(carregaSubTiposProdutoModel(new SubTipoProduto()));

		}

		txtDescricao.setText("");

		txtUnidadeMedida.setText("");

		txtValorUnitario.setText("");

	}

	/**
	 * 
	 * @param selectedItem
	 * @return
	 */
	private DefaultComboBoxModel carregaSubTiposProdutoModel(Object selectedItem) {

		List<SubTipoProduto> tiposProduto = (List<SubTipoProduto>) ((SubTipoProduto) selectedItem)
				.getSubTiposProduto();

		Object[] arrayTipos = null;

		DefaultComboBoxModel model = null;

		if (tiposProduto == null)

			tiposProduto = new ArrayList<SubTipoProduto>();

		try {

			arrayTipos = new Object[tiposProduto == null ? 1 : tiposProduto
					.size() + 1];

			arrayTipos[0] = ITEM_ZERO_COMBO;

			for (int i = 0; i < tiposProduto.size(); i++)

				arrayTipos[i + 1] = tiposProduto.get(i);

			model = new DefaultComboBoxModel(arrayTipos);

		} catch (LazyInitializationException e) {

			e.printStackTrace();

			throw new LazyInitializationException(e.getMessage());

		}

		return model;

	}

	public void mouseClicked(MouseEvent e) {

		if (e.getSource() instanceof JTable) {

			JTable table = (JTable) e.getSource();

			int indiceLinha = table.getSelectedRow();

			int idPecaRoupa = (Integer) table.getModel().getValueAt(
					indiceLinha, 0);

			controller.busca(idPecaRoupa);

			populaFormulario();

			btnDeletar.setEnabled(true);

		}

	}

	@Override
	protected void populaFormulario() {

		Produto produto = controller.getProduto();

		txtUnidadeMedida.setText(produto.getUnidadeMedida());

		txtDescricao.setText(produto.getDescricaoProduto());

		String valor = Float.toString(produto.getValorUnitario()).replace('.',
				',');

		txtValorUnitario.setText(valor);

		SubTipoProduto subTipoProduto = (SubTipoProduto) produto
				.getTipoProduto();

		TipoProduto tipoProduto = subTipoProduto.getSuperTipoProduto();

		cmbTiposDeProduto.getModel().setSelectedItem(tipoProduto);

		cmbSubTiposDeProduto.setModel(carregaSubTiposProdutoModel(tipoProduto));

		cmbSubTiposDeProduto.getModel().setSelectedItem(subTipoProduto);

	}

	@Override
	protected void configuraJPanel() {

		int y = 0;

		jPanelBusca = carregaJPanelBusca();

		jPanelBusca.setBounds(0, y, getWidth() - 17, txtBusca.getHeight() + 30);

		add(jPanelBusca);

		y += jPanelBusca.getHeight();

		jPanelFormulario = carregaJpanelFormulario();

		jPanelFormulario.setBounds(0, y, getWidth() - 17, 170);

		add(jPanelFormulario);

		y += jPanelFormulario.getHeight();

		jPanelActions = carregaJpanelActions();

		jPanelActions.setBounds(0, y, getWidth() - 17, btnDeletar.getHeight());

		add(jPanelActions);

		y += jPanelActions.getHeight();

		jPanelTable = carregaJpanelTable(y);

		jPanelTable.setBounds(0, y, getWidth() - 17, 320);

		add(jPanelTable);

	}

	@Override
	protected JPanel carregaJpanelFormulario() {

		JPanel jPanel = new JPanel();

		jPanel.setBorder(new TitledBorder("Produto"));

		jPanel.setLayout(null);

		int y = 10;

		JLabel lbl = new JLabel(TIPO_PRODUTO_LABEL);

		lbl.setBounds(10, y, 200, 50);

		jPanel.add(lbl);

		cmbTiposDeProduto = new JComboBox();

		cmbTiposDeProduto.setModel(carregaComboTiposProdutoModel());

		cmbTiposDeProduto.setBounds(150, y + 15, 150, TXT_HEIGHT);

		cmbTiposDeProduto.addActionListener(this);

		jPanel.add(cmbTiposDeProduto);

		JLabel lb = new JLabel("Sub-Tipo:");

		lb.setBounds(cmbTiposDeProduto.getWidth() + 155, y, 200, 50);

		jPanel.add(lb);

		cmbSubTiposDeProduto = new JComboBox();

		cmbSubTiposDeProduto
				.setModel(carregaSubTiposProdutoModel(new SubTipoProduto()));

		cmbSubTiposDeProduto.setBounds(cmbTiposDeProduto.getWidth() + 210,
				y + 15, 150, TXT_HEIGHT);

		jPanel.add(cmbSubTiposDeProduto);

		y += 45;

		JLabel lbl1 = new JLabel(DESCRICAO_LABEL);

		lbl1.setBounds(10, y, 200, TXT_HEIGHT);

		jPanel.add(lbl1);
		// 20
		y += 10;

		txtDescricao = new JFormattedTextField();

		txtDescricao.setBounds(150, y - 10, 350, TXT_HEIGHT);

		jPanel.add(txtDescricao);
		// 45
		// y += 18;

		JLabel lbl2 = new JLabel("Un. Medida:");

		lbl2.setBounds(10, y, 200, 50);

		jPanel.add(lbl2);
		// 55
		y += 10;

		txtUnidadeMedida = new JFormattedTextField();

		txtUnidadeMedida.setBounds(150, y + 10, 200, TXT_HEIGHT);

		jPanel.add(txtUnidadeMedida);

		y += 35;

		lblValorUnitario = new JLabel(VALOR_UNITARIO_LABEL);

		lblValorUnitario.setBounds(10, y, 150, 30);

		jPanel.add(lblValorUnitario);

		txtValorUnitario = new JFormattedTextField();

		txtValorUnitario.setDocument(new NumberDocument(true));

		txtValorUnitario.setBounds(150, y, 150, TXT_HEIGHT);

		jPanel.add(txtValorUnitario);

		return jPanel;

	}

	private DefaultComboBoxModel carregaComboTiposProdutoModel() {

		Object[] tmp = controller.getArrayTiposProduto();

		Object[] items = new Object[tmp.length + 1];

		items[0] = ITEM_ZERO_COMBO;

		for (int i = 0; i < tmp.length; i++) {

			items[i + 1] = tmp[i];

		}

		DefaultComboBoxModel comboModel = new DefaultComboBoxModel(items);

		return comboModel;

	}

	@Override
	protected JPanel carregaJpanelTable(int y) {

		JPanel jPanel = new JPanel(null);

		jPanel.setBounds(0, y, getWidth() - 17, getHeight() - 315);

		jPanel.setBorder(new TitledBorder("Produto"));

		carregaTableModel();

		jTable = inicializaJTable();

		jScrollPane = new JScrollPane(jTable);

		jScrollPane.setBounds(6, 15, jPanel.getWidth() - 15,
				jPanel.getHeight() - 20);

		jPanel.add(jScrollPane);

		return jPanel;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	protected void atualizaTableModel(Object object) {

		// Produto produto = (Produto) object;

		try {

			// List linhas = new ArrayList();
			List linhas = (List) object;

			// linhas.add(produto);

			carregaTableModel(carregaLinhasTableModel(linhas),
					COLUNAS_TABLE_MODEL);

		} catch (NullPointerException e) {

			e.printStackTrace();

			carregaTableModel();

		}

		Rectangle retangulo = jScrollPane.getBounds();

		jPanelTable.remove(jScrollPane);

		jTable = inicializaJTable();

		jScrollPane = new JScrollPane(jTable);

		jScrollPane.setBounds(retangulo);

		jPanelTable.add(jScrollPane);

		jPanelTable.repaint();

	}

	@Override
	public JTable inicializaJTable() {

		JTable jTable = super.inicializaJTable();

		DefaultTableCellRenderer direita = new DefaultTableCellRenderer();

		direita.setHorizontalAlignment(SwingConstants.RIGHT);

		jTable.getColumnModel().getColumn(3).setCellRenderer(direita);

		return jTable;

	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void carregaTableModel() {

		List linhas = carregaLinhasTableModel(controller.getProdutos());

		carregaTableModel(linhas, COLUNAS_TABLE_MODEL);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected List carregaLinhasTableModel(List lista) {

		List linhas = new ArrayList();

		for (Object objeto : lista) {

			Produto produto = (Produto) objeto;

			String valorUnitario = "R$ "
					+ MyFormatador.formataStringDecimais(produto
							.getValorUnitario());

			Object[] linha = { produto.getIdProduto(),
					produto.getTipoProduto(), produto.getDescricaoProduto(),
					produto.getUnidadeMedida(), valorUnitario };

			linhas.add(linha);

		}

		return linhas;

	}

	@Override
	protected JPanel carregaJPanelBusca() {

		JPanel jPanel = new JPanel(null);

		jPanel.setBorder(BUSCAR_TITLED_BORDER);

		JLabel lbl = new JLabel(DESCRICAO_LABEL);

		lbl.setBounds(10, 15, 400, 30);

		jPanel.add(lbl);

		txtBusca = new JFormattedTextField();

		txtBusca.setBounds(150, 20, 250, TXT_HEIGHT);

		jPanel.add(txtBusca);

		btnBusca = inicializaJButton("Buscar", 450, 20, 90,
				txtBusca.getHeight());

		jPanel.add(btnBusca);

		return jPanel;

	}

	@Override
	protected void salvarItem() throws Exception {
		// TODO Auto-generated method stub

		if (confirmaSalvamentoItem() == 0) {

			if (cmbTiposDeProduto.getSelectedIndex() == 0) {

				throw new Exception(
						ConstantesEnum.SELECAO_TIPO_PRODUTO_INVALIDA_EXCEPTION_MESSAGE
								.getValue().toString());

			} else if (cmbSubTiposDeProduto.getSelectedIndex() == 0)

				throw new Exception(
						ConstantesEnum.SELECAO_SUB_TIPO_PRODUTO_INVALIDA_EXCEPTION_MESSAGE
								.getValue().toString());

			Object tipoProduto = cmbTiposDeProduto.getSelectedItem();

			Object subTipoProduto = cmbSubTiposDeProduto.getSelectedItem();

			String descricao = txtDescricao.getText();

			String unidadeMedida = txtUnidadeMedida.getText();

			String valorUnitario = txtValorUnitario.getText();

			validaValorUnitario(valorUnitario);

			controller.salva(tipoProduto, subTipoProduto, descricao,
					unidadeMedida, valorUnitario);

			mostraMensagemConfirmacaoSalvamento();

			limpaFormulario();

		}

	}

}
