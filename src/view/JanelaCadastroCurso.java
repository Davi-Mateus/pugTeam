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

import model.Curso;
import dao.CursoDao;

public class JanelaCadastroCurso {

	private JPanel panel;
	public JFrame frame;
	private JLabel lblNomeCurso;
	private JTextField inputNomeCurso;
	private JButton btnSalvar;
	private JButton btnCancelar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaCadastroCurso window = new JanelaCadastroCurso(null);
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
	public JanelaCadastroCurso(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Cadastro de curso");
		frame.setBounds(0, 0, 761, 430);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblNomeCurso = new JLabel("Nome do curso:");
		lblNomeCurso.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNomeCurso.setBounds(58, 32, 130, 20);
		panel.add(lblNomeCurso);
		
		inputNomeCurso = new JTextField();
		inputNomeCurso.setBounds(244, 32, 362, 25);
		panel.add(inputNomeCurso);
		inputNomeCurso.setColumns(10);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String nome = inputNomeCurso.getText();
				if (nome.length() == 0) {
					JOptionPane.showMessageDialog(null, "ERRO, o nome do curso não foi preenchido");
					return;
				}

				Curso curso = new Curso();
				curso.setNomeCurso(nome);
				
				CursoDao cursoDao = new CursoDao();
				boolean insercao = cursoDao.inserir(curso);
				
				if (!insercao) {
					JOptionPane.showMessageDialog(null, "ERRO ao inserir curso no banco de dados");
					limpaCampos();
					return;
				}
				
				limpaCampos();
				
				JOptionPane.showMessageDialog(null, "Curso inserido com sucesso");
			}
		});
		btnSalvar.setBounds(257, 333, 117, 25);
		panel.add(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaCampos();
				frame.dispose();
			}
		});
		btnCancelar.setBounds(451, 333, 117, 25);
		panel.add(btnCancelar);
	}
	
	private void limpaCampos() {
		inputNomeCurso.setText("");
	}
}
