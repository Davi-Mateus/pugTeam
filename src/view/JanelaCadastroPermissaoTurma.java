package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JComboBox;

import dao.PermissaoDao;
import dao.PermiteTurmaDao;
import dao.TurmaDao;
import model.Permissao;
import model.Turma;
import com.toedter.calendar.JDateChooser;

public class JanelaCadastroPermissaoTurma {

	private JPanel panel;
	public JFrame frame;
	private JLabel lblNomeTurma;
	private JLabel lblResponsvel;
	private JLabel lblDataDaPermissao;
	private JLabel lblTipoDePermisso;
	private JTextField inputResponsavel;
	private JComboBox<String> comboBoxTipo;
	private JComboBox<Object> comboBoxTurma;
	private JDateChooser inputDataPermissao;
	private JButton btnSalvar;
	private JButton btnCancelar;
	
	private Turma turmaSelecionada;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaCadastroPermissaoTurma window = new JanelaCadastroPermissaoTurma(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JanelaCadastroPermissaoTurma(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Cadastro de permissão para turma");
		frame.setBounds(0, 0, 761, 430);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblNomeTurma = new JLabel("Turma:");
		lblNomeTurma.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNomeTurma.setBounds(58, 32, 153, 20);
		panel.add(lblNomeTurma);
		
		lblResponsvel = new JLabel("Responsável:");
		lblResponsvel.setFont(new Font("Dialog", Font.BOLD, 14));
		lblResponsvel.setBounds(58, 144, 181, 15);
		panel.add(lblResponsvel);
		
		inputResponsavel = new JTextField();
		inputResponsavel.setColumns(10);
		inputResponsavel.setBounds(244, 144, 362, 25);
		panel.add(inputResponsavel);
		
		lblDataDaPermissao = new JLabel("Data da permissao:");
		lblDataDaPermissao.setFont(new Font("Dialog", Font.BOLD, 14));
		lblDataDaPermissao.setBounds(58, 200, 181, 15);
		panel.add(lblDataDaPermissao);
		
		lblTipoDePermisso = new JLabel("Tipo de permissão:");
		lblTipoDePermisso.setFont(new Font("Dialog", Font.BOLD, 14));
		lblTipoDePermisso.setBounds(58, 88, 181, 15);
		panel.add(lblTipoDePermisso);
		
		comboBoxTipo = new JComboBox<String>();
		comboBoxTipo.setBounds(244, 88, 362, 25);
		panel.add(comboBoxTipo);
		comboBoxTipo.addItem("Selecione o tipo da permissão");
		comboBoxTipo.addItem("entrada");
		comboBoxTipo.addItem("saída");
		comboBoxTipo.addItem("entrada e saída");
		
		comboBoxTurma = new JComboBox<Object>();
		comboBoxTurma.setBounds(244, 32, 362, 25);
		List<Turma> turmas = new ArrayList<>();
		TurmaDao turmaDao = new TurmaDao();
		turmas = turmaDao.pesquisarPorNome("");
		comboBoxTurma.addItem("Selecione a turma");
		for (Turma turma: turmas) {
			comboBoxTurma.addItem(turma);
		}
		panel.add(comboBoxTurma);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Date dataPermissao = inputDataPermissao.getDate();
				String responsavel = inputResponsavel.getText();
				String tipoPermissao = (String) comboBoxTipo.getSelectedItem();
				
				try {
					turmaSelecionada = (Turma) comboBoxTurma.getSelectedItem();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "ERRO, a turma não foi selecionada");
					limpaCampos();
					return;
				}
				
				if (tipoPermissao == "Selecione o tipo da permissão") {
					JOptionPane.showMessageDialog(null, "ERRO, tipo da permissão não foi selecionada");
					return;
				}
				if (responsavel.length() == 0) {
					JOptionPane.showMessageDialog(null, "ERRO, o responsável pela permissão não foi preenchido");
					return;
				}
				if (dataPermissao == null) {
					JOptionPane.showMessageDialog(null, "ERRO, a data da permissão não foi preenchida");
					return;
				}
				
				PermissaoDao permissaoDao = new PermissaoDao();
				
				Permissao permissao = new Permissao();
				
				permissao.setDataPermissao(dataPermissao);
				permissao.setResponsavel(responsavel);
				permissao.setTipoPermissao(tipoPermissao);
				
				boolean insercaoPermissao = permissaoDao.inserirPermissao(permissao);
				
				if (!insercaoPermissao) {
					JOptionPane.showMessageDialog(null, "ERRO ao inserir permissão à turma no banco de dados");
					return;
				}
				
				permissao.setIdPermissao(permissaoDao.buscarUltimoId());
				
				int idTurma = turmaSelecionada.getIdTurma();
				int idPermissao = permissao.getIdPermissao();
				
				PermiteTurmaDao permiteTurmaDao = new PermiteTurmaDao();
				
				boolean insercaoPermiteTurma = permiteTurmaDao.inserir(idTurma, idPermissao);
				
				if (!insercaoPermiteTurma) {
					JOptionPane.showMessageDialog(null, "ERRO ao inserir permissão à turma no banco de dados");
					limpaCampos();
					return;
				}
				
				limpaCampos();
				instanciaJanelaPrincipal.preencheTurmasComPermissao();
				JOptionPane.showMessageDialog(null, "Permissão à turma inserida com sucesso");
			}
		});
		btnSalvar.setBounds(257, 333, 117, 25);
		panel.add(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaCampos();
				frame. dispose();
			}
		});
		btnCancelar.setBounds(451, 333, 117, 25);
		panel.add(btnCancelar);				
		
		inputDataPermissao = new JDateChooser();
		inputDataPermissao.setDateFormatString("dd/MM/yyyy");
		inputDataPermissao.setBounds(244, 200, 362, 25);
		panel.add(inputDataPermissao);
	}
	
	private void limpaCampos() {
		comboBoxTurma.setSelectedIndex(0);
		comboBoxTipo.setSelectedIndex(0);
		inputResponsavel.setText("");
		inputDataPermissao.setDate(null);
	}
}
