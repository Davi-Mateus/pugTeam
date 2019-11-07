package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import java.awt.GridLayout;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import dao.DiaAulaDao;
import dao.TurmaDao;
import model.DiaAula;
import model.Turma;


public class JanelaExclusaoDiaAula {

	public JFrame frame;
	private JPanel panel;
	private JPanel panelCheckBoxAulas;
	private JLabel labelTurma;
	private JComboBox<Object> comboBoxTurma;
	private JButton btnExcluir;
	private JButton btnCancelar;
	private JLabel lblData;
	private JLabel lblAulaASeremExcluidas;
	private JDateChooser inputData;
	
	private List<DiaAula> diasHorasSelecionados = new ArrayList<>();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaExclusaoDiaAula window = new JanelaExclusaoDiaAula(null);
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
	public JanelaExclusaoDiaAula(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Excluir dia de aula");
		frame.setBounds(0, 0, 761, 430);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
				
		labelTurma = new JLabel("Turma:");
		labelTurma.setFont(new Font("Dialog", Font.BOLD, 14));
		labelTurma.setBounds(58, 32, 130, 20);
		panel.add(labelTurma);
		
		comboBoxTurma = new JComboBox<Object>();
		comboBoxTurma.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				criaComboBoxDiasAula();
			}
		});
		comboBoxTurma.setBounds(244, 32, 362, 25);
		preencheComboBoxTurma();
		panel.add(comboBoxTurma);
		
		
		lblData = new JLabel("Data:");
		lblData.setFont(new Font("Dialog", Font.BOLD, 14));
		lblData.setBounds(58, 88, 130, 20);
		panel.add(lblData);
		
		inputData = new JDateChooser();
		inputData.setDateFormatString("dd/MM/yyyy");
		inputData.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				criaComboBoxDiasAula();
			}
		});
		inputData.setBounds(244, 88, 362, 25);
		panel.add(inputData);
		
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				@SuppressWarnings("unused")
				Turma turma;
				Date data = inputData.getDate();
							
				try {
					turma = (Turma) comboBoxTurma.getSelectedItem();
				} catch (Exception e1) {
					String campoTurma = (String) comboBoxTurma.getSelectedItem();
					if (campoTurma == "Todas as turmas") {
						DiaAulaDao diaAulaDao = new DiaAulaDao();
						boolean exclusaoDiaAula = diaAulaDao.excluirPorData(data);
						if (!exclusaoDiaAula) {
							JOptionPane.showMessageDialog(null, "ERRO ao excluir aulas da turma do banco de dados");
						}
						limpaCampos();
						preencheComboBoxTurma();
						JOptionPane.showMessageDialog(null, "Dia de aula excluída com sucesso");
						return;
					}
					JOptionPane.showMessageDialog(null, "ERRO, a turma não foi selecionada");
					return;
				}
				
				if (data == null) {
					JOptionPane.showMessageDialog(null, "ERRO, a data não foi selecionada");
					return;
				}
				
				int confirmarExclusao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o dia de aula?");
				
				if (confirmarExclusao == 1) {
					return;
				}
				
				else if (confirmarExclusao == 2) {
					limpaCampos();
					return;
				}
				else if(confirmarExclusao != 0) {
					return;
				}
				
				DiaAulaDao diaAulaDao = new DiaAulaDao();
				for (DiaAula diaAula : diasHorasSelecionados) {
					boolean exclusaoDiaAula = diaAulaDao.excluirPorDiaAula(diaAula);
					
					if (!exclusaoDiaAula) {
						JOptionPane.showMessageDialog(null, "ERRO ao excluir aulas da turma do banco de dados");
						limpaCampos();
						return;
					}
				}
				
								
				limpaCampos();
				preencheComboBoxTurma();
				instanciaJanelaPrincipal.preencheAlunosAtrasados();
				instanciaJanelaPrincipal.criaSchedulerPorAula();
				JOptionPane.showMessageDialog(null, "Dia de aula excluída com sucesso");
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
	}
	
	private void criaComboBoxDiasAula() {
		Turma turmaSelecionada;
		try {
			turmaSelecionada = (Turma) comboBoxTurma.getSelectedItem();
		} catch (Exception e1) {
			return;
		} finally {
			try {
				panel.remove(lblAulaASeremExcluidas);
				panel.remove(panelCheckBoxAulas);
				panel.validate();
				panel.repaint();

			} catch (Exception e) {
			}
		}
		lblAulaASeremExcluidas = new JLabel("Aula a serem excluídas:");
		lblAulaASeremExcluidas.setFont(new Font("Dialog", Font.BOLD, 14));
		lblAulaASeremExcluidas.setBounds(58, 144, 216, 20);
		panel.add(lblAulaASeremExcluidas);
		
		panelCheckBoxAulas = new JPanel();
		panelCheckBoxAulas.setBounds(244, 144, 362, 177);
		panelCheckBoxAulas.setLayout(new GridLayout(0, 1, 0, 0));
		preencheComboBoxAulas(panelCheckBoxAulas, turmaSelecionada);
		
	}
	
	private void preencheComboBoxAulas(JPanel panelCheckBoxAulas, Turma turmaSelecionada) {
		Date data = inputData.getDate();
		
		if (data == null) {
			return;
		}
		DiaAulaDao diaAulaDao = new DiaAulaDao();
		List<DiaAula> diasAula = new ArrayList<>();
		diasAula = diaAulaDao.pesquisarDiaAula(turmaSelecionada.getIdTurma(), data);
		for (DiaAula diaAula : diasAula) {
			JCheckBox checkBoxDiaAula = new JCheckBox(diaAula.toString());
			checkBoxDiaAula.addItemListener(new ItemListener() {
			    @Override
			    public void itemStateChanged(ItemEvent e) {
			        if(e.getStateChange() == ItemEvent.SELECTED) {
			        	diasHorasSelecionados.add(diaAula);
			        } else {
			        	diasHorasSelecionados.remove(diaAula);
			        };
			    }
			});
			panelCheckBoxAulas.add(checkBoxDiaAula);
		}
		panelCheckBoxAulas.revalidate();
		panelCheckBoxAulas.repaint();
		panel.add(panelCheckBoxAulas);
		panel.validate();
		panel.repaint();
	}
	
	private void preencheComboBoxTurma() {
		comboBoxTurma.removeAllItems();
		List<Turma> turmas = new ArrayList<>();
		TurmaDao turmaDao = new TurmaDao();
		turmas = turmaDao.pesquisarPorNome("");
		comboBoxTurma.addItem("Selecione a turma");
		comboBoxTurma.addItem("Todas as turmas");
		for (Turma turma: turmas) {
			comboBoxTurma.addItem(turma);
		}
	}
	
	private void limpaCampos() {
		comboBoxTurma.setSelectedIndex(0);
		inputData.setDate(null);
	}
}
