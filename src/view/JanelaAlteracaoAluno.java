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
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import dao.AlunoDao;
import model.Aluno;

public class JanelaAlteracaoAluno {

	private JPanel panel;
	public JFrame frame;
	private JLabel lblNumeroMatricula;
	private JLabel lblAluno;
	private JLabel lblNomeAluno;
	private JTextField inputAluno;
	private JTextField inputNomeAlunoNovo;
	private JTextField inputNumeroMatriculaNova;
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
					JanelaAlteracaoAluno window = new JanelaAlteracaoAluno(null);
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
	public JanelaAlteracaoAluno(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Alterar Aluno");
		frame.setBounds(0, 0, 761, 430);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblNomeAluno = new JLabel("Novo nome aluno:");
		lblNomeAluno.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNomeAluno.setBounds(58, 88, 156, 20);
		panel.add(lblNomeAluno);
		
		inputNomeAlunoNovo = new JTextField();
		inputNomeAlunoNovo.setBounds(244, 88, 362, 25);
		panel.add(inputNomeAlunoNovo);
		inputNomeAlunoNovo.setColumns(10);
		
		lblNumeroMatricula = new JLabel("Novo número da matrícula:");
		lblNumeroMatricula.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNumeroMatricula.setBounds(58, 144, 203, 15);
		panel.add(lblNumeroMatricula);
		
		inputNumeroMatriculaNova = new JTextField();
		inputNumeroMatriculaNova.setBounds(244, 144, 362, 25);
		panel.add(inputNumeroMatriculaNova);
		inputNumeroMatriculaNova.setColumns(10);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				
				String nome = inputNomeAlunoNovo.getText();
				String txtNmeroMatricula = inputNumeroMatriculaNova.getText();
				Long numeroMatricula;
				
				if (alunoSelecionado.getIdAluno() == 0) {
					JOptionPane.showMessageDialog(null, "ERRO, o aluno não foi selecionado corretamente");
					return;
				}
				if (nome.trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "ERRO, o nome do aluno não foi preenchido");
					return;
				}
				if (txtNmeroMatricula.trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "ERRO, o número da matrícula não foi preenchido");
					return;
				}
				try {
					numeroMatricula = Long.parseLong(inputNumeroMatriculaNova.getText(), 10);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "ERRO, o número da matrícula não foi preenchido corretamente");
					return;
				}
				
				Aluno aluno = new Aluno();
				aluno.setIdAluno(alunoSelecionado.getIdAluno());
				aluno.setNomeAluno(nome);
				aluno.setNumeroMatricula(numeroMatricula);
				
				AlunoDao alunoDao = new AlunoDao();
				boolean alteracao = alunoDao.alterar(aluno);
				
				if (!alteracao) {
					JOptionPane.showMessageDialog(null, "ERRO ao alterar aluno no banco de dados");
					limpaCampos();
					return;
				}
				
				limpaCampos();
				
				JOptionPane.showMessageDialog(null, "Aluno alterado com sucesso");
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
		
		inputAluno = new JTextField();
		inputAluno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (inputAluno.getText().length() == 0) {
					limpaCampos();
					return;
				}
				JPopupMenu popupMenu = new JPopupMenu();
				List<Aluno> alunos = new ArrayList<>();
				AlunoDao alunoDao = new AlunoDao();
				alunos = alunoDao.pesquisarPorNome(inputAluno.getText());
				for (Aluno aluno: alunos) {
					JMenuItem item = new JMenuItem(aluno.getNomeAluno() + " - " + aluno.getNumeroMatricula());
					popupMenu.add(item);
					item.addActionListener(new ActionListener() {
			            public void actionPerformed(ActionEvent e) {
			                inputAluno.setText(aluno.getNomeAluno() + " - " + aluno.getNumeroMatricula());
			                alunoSelecionado = aluno;
			             
							inputNomeAlunoNovo.setText(alunoSelecionado.getNomeAluno());
							inputNumeroMatriculaNova.setText(Long.toString(alunoSelecionado.getNumeroMatricula()));
			            }
			        });
				}
				popupMenu.show(inputAluno, 0, 25);
				inputAluno.requestFocus();
			}
		});
		inputAluno.setColumns(10);
		inputAluno.setBounds(244, 32, 362, 25);
		panel.add(inputAluno);
		
		lblAluno = new JLabel("Aluno:");
		lblAluno.setFont(new Font("Dialog", Font.BOLD, 14));
		lblAluno.setBounds(58, 32, 130, 20);
		panel.add(lblAluno);
	}
	
	private void limpaCampos() {
		inputAluno.setText("");
		inputNomeAlunoNovo.setText("");
		inputNumeroMatriculaNova.setText("");
	}
}
