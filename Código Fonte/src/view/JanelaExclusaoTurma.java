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
import javax.swing.JComboBox;

import dao.DiaAulaDao;
import dao.PermiteTurmaDao;
import dao.PossuiDao;
import dao.TurmaDao;
import model.Turma;
import java.awt.Color;

public class JanelaExclusaoTurma {

	public JFrame frame;
	private JPanel panel;
	private JLabel labelTurma;
	private JComboBox<Object> comboBoxTurma;
	private JButton btnExcluir;
	private JButton btnCancelar;
	private JLabel lblSomenteSer;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaExclusaoTurma window = new JanelaExclusaoTurma(null);
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
	public JanelaExclusaoTurma(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Excluir turma");
		frame.setBounds(0, 0, 761, 430);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
				
		comboBoxTurma = new JComboBox<Object>();
		preencheComboBoxTurma();
		comboBoxTurma.setBounds(244, 32, 362, 25);
		panel.add(comboBoxTurma);
		
		labelTurma = new JLabel("Turma:");
		labelTurma.setFont(new Font("Dialog", Font.BOLD, 14));
		labelTurma.setBounds(58, 32, 130, 20);
		panel.add(labelTurma);
							

		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Turma turma;
				try {
					turma = (Turma) comboBoxTurma.getSelectedItem();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "ERRO, a turma não foi selecionada");
					return;
				}
				
				int confirmarExlcusao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir a turma?");
				
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
				
				DiaAulaDao diaAulaDao = new DiaAulaDao();
				boolean exclusaoDiaAula = diaAulaDao.excluirPorTurma(turma.getIdTurma());
				
				if (!exclusaoDiaAula) {
					JOptionPane.showMessageDialog(null, "ERRO ao excluir aulas da turma do banco de dados");
					limpaCampos();
					return;
				}
				
				PermiteTurmaDao permiteTurmaDao = new PermiteTurmaDao();
				boolean exclusaoPermiteTurma = permiteTurmaDao.excluir(turma.getIdTurma());
				
				if (!exclusaoPermiteTurma) {
					JOptionPane.showMessageDialog(null, "ERRO ao excluir permissões da turma do banco de dados");
					limpaCampos();
					return;
				}
				
				PossuiDao possuiDao = new PossuiDao();
				boolean exclusaoPossui = possuiDao.excluir(turma.getIdTurma());
				
				if (!exclusaoPossui) {
					JOptionPane.showMessageDialog(null, "ERRO ao excluir horários da turma do banco de dados");
					limpaCampos();
					return;
				}
				
				TurmaDao turmaDao = new TurmaDao();
				boolean exclusaoTurma = turmaDao.excluir(turma);
				
				if (!exclusaoTurma) {
					JOptionPane.showMessageDialog(null, "ERRO ao excluir a turma do banco de dados");
					limpaCampos();
					return;
				}
				
				limpaCampos();
				preencheComboBoxTurma();		
				JOptionPane.showMessageDialog(null, "Turma excluída com sucesso");
			}
		});
		btnExcluir.setBounds(257, 333, 117, 25);
		panel.add(btnExcluir);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaCampos();
				frame. dispose();
			}
		});
		btnCancelar.setBounds(451, 333, 117, 25);
		panel.add(btnCancelar);
		
		lblSomenteSer = new JLabel("* Não será possível excluir turmas com alunos matriculados e/ou histórico de registros.");
		lblSomenteSer.setForeground(Color.RED);
		lblSomenteSer.setFont(new Font("Dialog", Font.BOLD, 11));
		lblSomenteSer.setBounds(58, 88, 602, 20);
		panel.add(lblSomenteSer);
	}
	
	private void preencheComboBoxTurma() {
		comboBoxTurma.removeAllItems();
		List<Turma> turmas = new ArrayList<>();
		TurmaDao turmaDao = new TurmaDao();
		turmas = turmaDao.pesquisarPorNomeDeletavel("");
		comboBoxTurma.addItem("Selecione a turma");
		for (Turma turma: turmas) {
			comboBoxTurma.addItem(turma);
		}
	}
	
	private void limpaCampos() {
		comboBoxTurma.setSelectedIndex(0);
	}
}
