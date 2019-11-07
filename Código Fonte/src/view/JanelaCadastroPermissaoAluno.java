package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JComboBox;

import dao.AlunoDao;
import dao.PermissaoDao;
import dao.PermiteAlunoDao;
import model.Aluno;
import model.Permissao;
import com.toedter.calendar.JDateChooser;

public class JanelaCadastroPermissaoAluno {

	private JPanel panel;
	public JFrame frame;
	private JLabel lblNomeAluno;
	private JLabel lblResponsvel;
	private JLabel lblDataDaPermissao;
	private JLabel lblTipoDePermisso;
	private JTextField inputAluno;
	private JTextField inputResponsavel;
	private JComboBox<String> comboBoxTipo;
	private JDateChooser inputDataPermissao;
	private JButton btnSalvar;
	private JButton btnCancelar;
	
	private Aluno alunoSelecionado = new Aluno();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaCadastroPermissaoAluno window = new JanelaCadastroPermissaoAluno(null);
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
	public JanelaCadastroPermissaoAluno(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Cadastro de permissão para aluno");
		frame.setBounds(0, 0, 761, 430);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblNomeAluno = new JLabel("Aluno:");
		lblNomeAluno.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNomeAluno.setBounds(58, 32, 153, 20);
		panel.add(lblNomeAluno);
		
		inputAluno = new JTextField();
		inputAluno.setBounds(244, 32, 362, 25);
		inputAluno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				JPopupMenu popupMenu = new JPopupMenu();
				List<Aluno> alunos = new ArrayList<>();
				AlunoDao alunoDao = new AlunoDao();
				alunos = alunoDao.pesquisarPorNome(inputAluno.getText());
				for (Aluno aluno: alunos) {
					JMenuItem item = new JMenuItem(aluno.getNomeAluno().toString());
					popupMenu.add(item);
					item.addActionListener(new ActionListener() {
			            public void actionPerformed(ActionEvent e) {
			                inputAluno.setText(aluno.getNomeAluno().toString());
			                alunoSelecionado = aluno;
			            }
			        });
				}
				popupMenu.show(inputAluno, 0, 25);
				inputAluno.requestFocus();
			}
		});
		panel.add(inputAluno);
		inputAluno.setColumns(10);
		
		lblResponsvel = new JLabel("Responsável:");
		lblResponsvel.setFont(new Font("Dialog", Font.BOLD, 14));
		lblResponsvel.setBounds(58, 144, 181, 15);
		panel.add(lblResponsvel);
		
		inputResponsavel = new JTextField();
		inputResponsavel.setColumns(10);
		inputResponsavel.setBounds(244, 144, 362, 25);
		panel.add(inputResponsavel);
		
		lblDataDaPermissao = new JLabel("Data da permissão:");
		lblDataDaPermissao.setFont(new Font("Dialog", Font.BOLD, 14));
		lblDataDaPermissao.setBounds(58, 200, 181, 15);
		panel.add(lblDataDaPermissao);
		
		lblTipoDePermisso = new JLabel("Tipo da permissão:");
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
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String responsavel = inputResponsavel.getText();
				String tipoPermissao = (String) comboBoxTipo.getSelectedItem();
				Date dataPermissao = inputDataPermissao.getDate();
												
				if (alunoSelecionado.getIdAluno() == 0) {
					JOptionPane.showMessageDialog(null, "ERRO, o aluno não foi selecionado corretamente");
					return;
				}
				if (tipoPermissao == "Selecione o tipo da permissão") {
					JOptionPane.showMessageDialog(null, "ERRO, tipo de permissão não foi selecionada");
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
				
				permissao.setResponsavel(responsavel);
				permissao.setTipoPermissao(tipoPermissao);
				permissao.setDataPermissao(dataPermissao);
				
				boolean insercaoPermissao = permissaoDao.inserirPermissao(permissao);
				
				if (!insercaoPermissao) {
					JOptionPane.showMessageDialog(null, "ERRO ao inserir permissão ao aluno no banco de dados");
					limpaCampos();
					return;
				}
				
				permissao.setIdPermissao(permissaoDao.buscarUltimoId());
				
				PermiteAlunoDao permiteAlunoDao = new PermiteAlunoDao();
				
				boolean insercaoPermiteAluno = permiteAlunoDao.inserir(alunoSelecionado.getIdAluno(), permissao.getIdPermissao());

				if (!insercaoPermiteAluno) {
					JOptionPane.showMessageDialog(null, "ERRO ao inserir permissão ao aluno no banco de dados");
					limpaCampos();
					return;
				}
				
				limpaCampos();
				instanciaJanelaPrincipal.preencheAlunosComPermissao();
				JOptionPane.showMessageDialog(null, " Permissão ao aluno inserida com sucesso");
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
		inputAluno.setText("");
		comboBoxTipo.setSelectedIndex(0);
		inputResponsavel.setText("");
		inputDataPermissao.setDate(null);
	}
}
