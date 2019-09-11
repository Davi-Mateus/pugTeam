package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
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

public class JanelaCancelamentoMatriculaAluno {

	private JPanel panel;
	public JFrame frame;
	private JLabel lblTurma;
	private JLabel lblAluno;
	private JTextField inputAluno;
	private Aluno alunoSelecionado;
	JComboBox<Object> comboBoxTurma;
	private JButton btnSalvar;
	private JButton btnCancelar;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaCancelamentoMatriculaAluno window = new JanelaCancelamentoMatriculaAluno(null);
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
	public JanelaCancelamentoMatriculaAluno(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Cancelamento de matrícula de aluno");
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
		comboBoxTurma.setBounds(244, 88, 362, 25);
		comboBoxTurma.addItem("Selecione a turma");
		panel.add(comboBoxTurma);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Object turma = comboBoxTurma.getSelectedItem();
				String aluno = inputAluno.getText();
			
				if (aluno.length() == 0) {
					JOptionPane.showMessageDialog(null, "ERRO, o nome do aluno não foi selecionado");
					return;
				}
				if (turma == "Selecione a turma") {
					JOptionPane.showMessageDialog(null, "ERRO, a turma não foi selecionada");
					return;
				}
							
				Matricula matricula = new Matricula();
				matricula.setAluno(alunoSelecionado);
				matricula.setTurma((Turma) turma);
				
				MatriculaDao matriculaDao = new MatriculaDao();
				boolean cancelamento = matriculaDao.cancelar(matricula);
				
				if (!cancelamento) {
					JOptionPane.showMessageDialog(null, "ERRO ao cancelar matrícula no banco de dados");
					limpaCampos();
					return;
				}
				
				limpaCampos();
				
				JOptionPane.showMessageDialog(null, "Cancelamento de matrícula realizada com sucesso");
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
				JPopupMenu popupMenu = new JPopupMenu();
				List<Aluno> alunos = new ArrayList<>();
				AlunoDao alunoDao = new AlunoDao();
				alunos = alunoDao.pesquisarPorNome(inputAluno.getText());
				for (Aluno aluno: alunos) {
					JMenuItem item = new JMenuItem(aluno.toString());
					popupMenu.add(item);
					item.addActionListener(new ActionListener() {
			            public void actionPerformed(ActionEvent e) {			            	
			            	preencheComboBoxTurma(aluno);
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
		
	}
	
	private void preencheComboBoxTurma(Aluno aluno) {
		comboBoxTurma.removeAllItems();
        inputAluno.setText(aluno.toString());
        alunoSelecionado = aluno;
		TurmaDao turmaDao = new TurmaDao();
		List<Turma> turmas = turmaDao.pesquisarPorAluno(alunoSelecionado);
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
