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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JComboBox;

import model.Curso;
import dao.CursoDao;

public class JanelaAlteracaoCurso {

	private JPanel panel;
	public JFrame frame;
	private JLabel lblNomeCurso;
	private JLabel lblCurso;
	private JTextField inputNomeCurso;
	private JComboBox<Object> comboBoxCurso;
	private JButton btnSalvar;
	private JButton btnCancelar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaAlteracaoCurso window = new JanelaAlteracaoCurso(null);
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
	public JanelaAlteracaoCurso(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Alterar Curso");
		frame.setBounds(0, 0, 761, 430);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblNomeCurso = new JLabel("Novo nome do curso:");
		lblNomeCurso.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNomeCurso.setBounds(58, 88, 186, 20);
		panel.add(lblNomeCurso);
		
		inputNomeCurso = new JTextField();
		inputNomeCurso.setBounds(244, 88, 362, 25);
		panel.add(inputNomeCurso);
		inputNomeCurso.setColumns(10);
		
		lblCurso = new JLabel("Curso:");
		lblCurso.setFont(new Font("Dialog", Font.BOLD, 14));
		lblCurso.setBounds(58, 32, 186, 20);
		panel.add(lblCurso);
		
		comboBoxCurso = new JComboBox<Object>();
		preencheComboBoxCurso();
		comboBoxCurso.setBounds(244, 32, 362, 25);
		comboBoxCurso.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent arg0) {
				try {
					inputNomeCurso.setText(((Curso)comboBoxCurso.getSelectedItem()).toString());
				} catch (Exception e) {
					limpaCampos();
	        		return;
				}
	        }
	    });
		panel.add(comboBoxCurso);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				
				String nome = inputNomeCurso.getText();
				Curso id = (Curso) comboBoxCurso.getSelectedItem();
				
				if (nome.trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "ERRO, o nome do curso não foi preenchido");
					return;
				}

				Curso curso = new Curso();
				curso.setIdCurso(id.getIdCurso());
				curso.setNomeCurso(nome);
				
				CursoDao cursoDao = new CursoDao();
				boolean alteracao = cursoDao.alterar(curso);
				
				if (!alteracao) {
					JOptionPane.showMessageDialog(null, "ERRO ao alterar curso no banco de dados");
					limpaCampos();
					return;
				}
				
				limpaCampos();
				preencheComboBoxCurso();
				JOptionPane.showMessageDialog(null, "Curso alterado com sucesso");
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
	
	private void preencheComboBoxCurso() {
		comboBoxCurso.removeAllItems();
		List<Curso> cursos = new ArrayList<>();
		CursoDao cursoDao = new CursoDao();
		cursos = cursoDao.pesquisarPorNome("");
		comboBoxCurso.addItem("Selecione o curso");
		for (Curso curso: cursos) {
			comboBoxCurso.addItem(curso);
		}
	}
	
	private void limpaCampos() {
		inputNomeCurso.setText("");
	}
}
