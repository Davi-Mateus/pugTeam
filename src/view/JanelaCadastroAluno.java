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
import java.awt.event.ActionEvent;
import java.awt.Font;
import dao.AlunoDao;
import model.Aluno;

public class JanelaCadastroAluno {

	private JPanel panel;
	public JFrame frame;
	private JLabel lblNomeAluno;
	private JLabel lblNumeroMatricula;
	private JTextField inputNomeAluno;
	private JTextField inputNumeroMatricula;
	private JButton btnSalvar;
	private JButton btnCancelar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaCadastroAluno window = new JanelaCadastroAluno(null);
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
	public JanelaCadastroAluno(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Cadastro de aluno");
		frame.setBounds(0, 0, 761, 430);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblNomeAluno = new JLabel("Nome do aluno:");
		lblNomeAluno.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNomeAluno.setBounds(58, 32, 130, 20);
		panel.add(lblNomeAluno);
		
		inputNomeAluno = new JTextField();
		inputNomeAluno.setBounds(244, 32, 362, 25);
		panel.add(inputNomeAluno);
		inputNomeAluno.setColumns(10);
		
		lblNumeroMatricula = new JLabel("Número da matricula:");
		lblNumeroMatricula.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNumeroMatricula.setBounds(58, 88, 181, 15);
		panel.add(lblNumeroMatricula);
		
		inputNumeroMatricula = new JTextField();
		inputNumeroMatricula.setBounds(244, 88, 362, 25);
		panel.add(inputNumeroMatricula);
		inputNumeroMatricula.setColumns(10);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String nome = inputNomeAluno.getText();
				String txtNmeroMatricula = inputNumeroMatricula.getText();
				Long numeroMatricula;
				
				if (nome.trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "ERRO, o nome do aluno não foi preenchido");
					return;
				}
				if (txtNmeroMatricula.trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "ERRO, o número da matrícula não foi preenchido");
					return;
				}
				
				try {
					numeroMatricula = Long.parseLong(inputNumeroMatricula.getText(), 10);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "ERRO, o número da matricula não foi preenchido corretamente");
					return;
				}
				Aluno aluno = new Aluno();
				aluno.setNomeAluno(nome);
				aluno.setNumeroMatricula(numeroMatricula);
				
				AlunoDao alunoDao = new AlunoDao();
				boolean insercao = alunoDao.inserir(aluno);
				
				if (!insercao) {
					JOptionPane.showMessageDialog(null, "ERRO ao inserir aluno no banco de dados");
					limpaCampos();
					return;
				}
				
				limpaCampos();
				
				JOptionPane.showMessageDialog(null, "Aluno inserido com sucesso");
				
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
	}
	
	private void limpaCampos() {
		inputNomeAluno.setText("");
		inputNumeroMatricula.setText("");
	}
}
