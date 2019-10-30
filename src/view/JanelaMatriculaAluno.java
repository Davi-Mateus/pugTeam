package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPopupMenu;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import model.Aluno;
import model.Matricula;
import model.Turma;
import dao.AlunoDao;
import dao.MatriculaDao;
import dao.TurmaDao;

public class JanelaMatriculaAluno {

	private JPanel panel;
	public JFrame frame;
	private JLabel lblTurma;
	private JLabel lblAluno;
	private JTextField inputAluno;
	private Aluno alunoSelecionado = new Aluno();
	private JComboBox<Object> comboBoxTurma;
	private JButton btnSalvar;
	private JButton btnCancelar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaMatriculaAluno window = new JanelaMatriculaAluno(null);
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
	public JanelaMatriculaAluno(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Matrícula de aluno");
		frame.setBounds(0, 0, 761, 430);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblAluno = new JLabel("Aluno:");
		lblAluno.setFont(new Font("Dialog", Font.BOLD, 14));
		lblAluno.setBounds(58, 32, 130, 20);
		panel.add(lblAluno);
		
		lblTurma = new JLabel("Turma:");
		lblTurma.setFont(new Font("Dialog", Font.BOLD, 14));
		lblTurma.setBounds(58, 88, 130, 20);
		panel.add(lblTurma);
		
		comboBoxTurma = new JComboBox<Object>();
		preencheComboBoxTurma();
		comboBoxTurma.setBounds(244, 88, 362, 25);
		panel.add(comboBoxTurma);
		
		inputAluno = new JTextField();
		inputAluno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				JPopupMenu popupMenu = new JPopupMenu();
				AlunoDao alunoDao = new AlunoDao();
				alunoSelecionado = alunoDao.pesquisarPorNumeroMatricula(Long.parseLong(inputAluno.getText()));
				popupMenu.show(inputAluno, 0, 25);
				inputAluno.requestFocus();
			}
		});
		inputAluno.setColumns(10);
		inputAluno.setBounds(244, 32, 362, 25);
		panel.add(inputAluno);
		
		
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Object turma = comboBoxTurma.getSelectedItem();
				MatriculaDao matriculaDao = new MatriculaDao();
			
				if ((alunoSelecionado.getIdAluno() == 0) && (alunoSelecionado.getNumeroMatricula()==null)) {
					JOptionPane.showMessageDialog(null, "ERRO, o nome do aluno não foi selecionado corretamente");
					return;
				}
				if (turma == "Selecione a turma") {
					JOptionPane.showMessageDialog(null, "ERRO, a turma não foi selecionada");
					return;
				}

				Matricula matricula = new Matricula();
				matricula.setAluno(alunoSelecionado);
				matricula.setTurma((Turma) turma);
				
				boolean MatriculaExistente = matriculaDao.verificarMatriculaExistente(matricula);
				
				if (MatriculaExistente) {
					JOptionPane.showMessageDialog(null, "ERRO, o aluno já está matriculado nessa turma");
					return;
				}
				
				boolean insercao = matriculaDao.inserir(matricula);
				
				if (!insercao) {
					JOptionPane.showMessageDialog(null, "ERRO ao realizar matrícula no banco de dados");
					limpaCampos();
					return;
				}
				
				limpaCampos();
				preencheComboBoxTurma();
				JOptionPane.showMessageDialog(null, "Matrícula realizada com sucesso");
				
			}
		});
		inputAluno.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnSalvar.doClick();
				}
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
	
	private void preencheComboBoxTurma() {
		comboBoxTurma.removeAllItems();
		List<Turma> turmas = new ArrayList<>();
		TurmaDao turmaDao = new TurmaDao();
		turmas = turmaDao.pesquisarPorNome("");
		comboBoxTurma.addItem("Selecione a turma");
		for (Turma turma: turmas) {
			comboBoxTurma.addItem(turma);
		}
	}
	
	private void limpaCampos() {
		comboBoxTurma.setSelectedIndex(0);
		inputAluno.setText("");
		alunoSelecionado = null;
	}
}
