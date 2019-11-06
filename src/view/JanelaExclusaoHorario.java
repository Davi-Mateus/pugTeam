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
import dao.HorarioDao;
import dao.PossuiDao;
import model.Horario;
import java.awt.Color;

public class JanelaExclusaoHorario {

	public JFrame frame;
	private JPanel panel;
	private JLabel labelHorario;
	private JComboBox<Object> comboBoxHorario;
	private JButton btnExcluir;
	private JButton btnCancelar;
	private JLabel lblAviso;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaExclusaoHorario window = new JanelaExclusaoHorario(null);
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
	public JanelaExclusaoHorario(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Excluir horários de aula");
		frame.setBounds(0, 0, 761, 430);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		labelHorario = new JLabel("horários:");
		labelHorario.setFont(new Font("Dialog", Font.BOLD, 14));
		labelHorario.setBounds(58, 32, 130, 20);
		panel.add(labelHorario);
		
		comboBoxHorario = new JComboBox<Object>();
		preencheComboBoxHorarios();
		comboBoxHorario.setBounds(244, 32, 362, 25);
		panel.add(comboBoxHorario);
							
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Horario horario;
				
				try {
					horario = (Horario) comboBoxHorario.getSelectedItem();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "ERRO, o horários não foi selecionado");
					return;
				}	
				int confirmarExlcusao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o horários?");
				
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
				
				PossuiDao possuiDao = new PossuiDao();
				boolean exclusaoPossui = possuiDao.excluirPorHorario(horario.getIdHorario());
				
				if (!exclusaoPossui) {
					JOptionPane.showMessageDialog(null, "ERRO ao excluir o horários da turma no banco de dados.");
					limpaCampos();
					return;
					
				}
				
				DiaAulaDao diaAulaDao = new DiaAulaDao();
				boolean exclusaoDiaAula = diaAulaDao.excluirPorTurma(horario.getIdHorario());
				
				if (!exclusaoDiaAula) {
					JOptionPane.showMessageDialog(null, "ERRO ao excluir o dia de aula da turma no banco de dados.");
					limpaCampos();
					return;
					
				}
				
				HorarioDao horarioDaoNovo = new HorarioDao();
				boolean exclusao = horarioDaoNovo.excluir(horario);
				
				if (!exclusao) {
					JOptionPane.showMessageDialog(null, "ERRO ao excluir o horários de aula do banco de dados ou turma cadastrada em alguma turma.");
					limpaCampos();
					return;
				}
				
				limpaCampos();
				preencheComboBoxHorarios();	
				JOptionPane.showMessageDialog(null, "horários de aula excluído com sucesso");
			}
		});
		btnExcluir.setBounds(257, 333, 117, 25);
		panel.add(btnExcluir);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaCampos();
				frame.dispose();
			}
		});
		btnCancelar.setBounds(451, 333, 117, 25);
		panel.add(btnCancelar);
		
		lblAviso = new JLabel("* Somente será possível excluir horários que não estejam sendo usados por nenhuma turma.");
		lblAviso.setForeground(Color.RED);
		lblAviso.setFont(new Font("Dialog", Font.BOLD, 11));
		lblAviso.setBounds(58, 88, 660, 20);
		panel.add(lblAviso);
	}
	
	private void preencheComboBoxHorarios() {
		comboBoxHorario.removeAllItems();
		List<Horario> horarios = new ArrayList<>();
		HorarioDao quadroHorariosDao = new HorarioDao();
		horarios = quadroHorariosDao.pesquisarPorHorarioDeletavel("");
		comboBoxHorario.addItem("Selecione o horários");
		for (Horario horario: horarios) {
			comboBoxHorario.addItem(horario);
		}
	}
	
	private void limpaCampos() {
		comboBoxHorario.setSelectedIndex(0);
	}
}
