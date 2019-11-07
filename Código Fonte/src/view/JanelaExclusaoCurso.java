package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;

import model.Curso;
import dao.CursoDao;
import javax.swing.JComboBox;
import java.awt.Color;

public class JanelaExclusaoCurso {

	private JPanel panel;
	public JFrame frame;
	private JLabel lblNomeCurso;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JComboBox<Object> comboBoxCurso;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaExclusaoCurso window = new JanelaExclusaoCurso(null);
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
	public JanelaExclusaoCurso(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Excluir curso");
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
		
		comboBoxCurso = new JComboBox<Object>();
		preencheComboBoxCurso();
		comboBoxCurso.setBounds(244, 32, 362, 25);
		panel.add(comboBoxCurso);
		
		btnSalvar = new JButton("Excluir");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Curso cursoSelecionado;
				try {
					cursoSelecionado = (Curso) comboBoxCurso.getSelectedItem();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "ERRO, o curso não foi selecionado");
					return;
				}
				
				int confirmarExlcusao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o curso?");
				
				if (confirmarExlcusao == 1) {
					return;
				}
				
				else if (confirmarExlcusao == 2) {
					limpaCampos();
					return;
				}
				else if(confirmarExlcusao != 0) {
					return;
				}
				
				CursoDao cursoDao = new CursoDao();
				boolean exclusao = cursoDao.excluir(cursoSelecionado);
				
				if (!exclusao) {
					JOptionPane.showMessageDialog(null, "ERRO ao excluir curso do banco de dados");
					limpaCampos();
					return;
				}
				
				limpaCampos();
				preencheComboBoxCurso();
				JOptionPane.showMessageDialog(null, "Curso excluído com sucesso");
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
		
		JLabel labelAviso = new JLabel("* Somente será possível excluir cursos que não possuam nenhuma turma.");
		labelAviso.setForeground(Color.RED);
		labelAviso.setFont(new Font("Dialog", Font.BOLD, 11));
		labelAviso.setBounds(58, 88, 548, 20);
		panel.add(labelAviso);
	}
	
	private void preencheComboBoxCurso() {
		comboBoxCurso.removeAllItems();
		List<Curso> cursos = new ArrayList<>();
		CursoDao cursoDao = new CursoDao();
		cursos = cursoDao.pesquisarPorNomeDeletavel("");
		comboBoxCurso.addItem("Selecione o curso");
		for (Curso curso: cursos) {
			comboBoxCurso.addItem(curso);
		}
	}
	
	private void limpaCampos() {
		comboBoxCurso.setSelectedIndex(0);;
	}
}
