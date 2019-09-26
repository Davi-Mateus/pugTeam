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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JComboBox;

import dao.CursoDao;
import dao.PossuiDao;
import dao.HorarioDao;
import dao.TurmaDao;
import model.Curso;
import model.Horario;
import model.Turma;
import javax.swing.JCheckBox;
import com.toedter.calendar.JDateChooser;

public class JanelaAlteracaoTurma {

	private JPanel panel;
	private JPanel panelCheckbox;
	public JFrame frame;
	private JLabel lblNomeTurma;
	private JLabel lblDataFinalAulas;
	private JLabel lblAno;
	private JLabel lblNomeCurso;
	private JLabel lblDataIncioAulas;
	private JLabel lblTurma;
	private JTextField inputNomeTurma;
	private JComboBox<Object> comboBoxCurso;
	private JComboBox<Object> comboBoxAno;
	private JComboBox<Object> comboBoxTurma;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private ArrayList<Horario> horariosSelecionados = new ArrayList<>();
	private ArrayList<JCheckBox> checkboxs;
	private JDateChooser inputDataInicio;
	private JDateChooser inputDataFinal;
	
	private SimpleDateFormat sdfAno = new SimpleDateFormat("yyyy");
	
	private int anoAtual = Integer.parseInt(sdfAno.format(new Date()));
	private JLabel lblHorario;
	private Turma turmaSelecionada = new Turma();
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaAlteracaoTurma window = new JanelaAlteracaoTurma(null);
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
	public JanelaAlteracaoTurma(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Alterar turma");
		frame.setBounds(0, 0, 853, 699);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		panelCheckbox = new JPanel();
		panelCheckbox.setBounds(244, 368, 544, 228);
		panel.add(panelCheckbox);
		panelCheckbox.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblNomeTurma = new JLabel("Novo nome da turma:");
		lblNomeTurma.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNomeTurma.setBounds(58, 88, 181, 15);
		panel.add(lblNomeTurma);
		
		inputNomeTurma = new JTextField();
		inputNomeTurma.setBounds(244, 88, 362, 25);
		panel.add(inputNomeTurma);
		inputNomeTurma.setColumns(10);
		
		lblNomeCurso = new JLabel("Novo curso");
		lblNomeCurso.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNomeCurso.setBounds(58, 144, 181, 15);
		panel.add(lblNomeCurso);
		
		comboBoxCurso = new JComboBox<Object>();
		preencheComboBoxCurso();
		comboBoxCurso.setBounds(244, 144, 362, 25);
		panel.add(comboBoxCurso);
		
		lblAno = new JLabel("Nova ano:");
		lblAno.setFont(new Font("Dialog", Font.BOLD, 14));
		lblAno.setBounds(58, 200, 130, 20);
		panel.add(lblAno);
		
		comboBoxAno = new JComboBox<Object>();
		comboBoxAno.setBounds(244, 200, 362, 25);
		panel.add(comboBoxAno);
		comboBoxAno.addItem("Selecione o ano");
		for (int i = 2018; i <= anoAtual + 1; i++) {
			comboBoxAno.addItem(i);
		}
		
		lblDataIncioAulas = new JLabel("Nova data início aulas:");
		lblDataIncioAulas.setFont(new Font("Dialog", Font.BOLD, 14));
		lblDataIncioAulas.setBounds(58, 256, 181, 20);
		panel.add(lblDataIncioAulas);

		inputDataInicio = new JDateChooser();
		inputDataInicio.setDateFormatString("dd/MM/yyyy");
		inputDataInicio.setBounds(244, 256, 362, 25);
		panel.add(inputDataInicio);
		
		lblDataFinalAulas = new JLabel("Nova data final aulas:");
		lblDataFinalAulas.setFont(new Font("Dialog", Font.BOLD, 14));
		lblDataFinalAulas.setBounds(58, 312, 181, 20);
		panel.add(lblDataFinalAulas);
		
		inputDataFinal = new JDateChooser();
		inputDataFinal.setDateFormatString("dd/MM/yyyy");
		inputDataFinal.setBounds(244, 312, 362, 25);
		panel.add(inputDataFinal);
		
		lblHorario = new JLabel("Novos horários de aula:");
		lblHorario.setFont(new Font("Dialog", Font.BOLD, 14));
		lblHorario.setBounds(58, 368, 195, 20);
		panel.add(lblHorario);
		
		lblTurma = new JLabel("Turma:");
		lblTurma.setFont(new Font("Dialog", Font.BOLD, 14));
		lblTurma.setBounds(58, 32, 130, 20);
		panel.add(lblTurma);
		
		comboBoxTurma = new JComboBox<Object>();
		comboBoxTurma.setBounds(244, 32, 362, 25);
		preencheComboBoxTurma();
		comboBoxTurma.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent arg0) {
	        	if (comboBoxTurma.getSelectedIndex() == 0) {
	        		limpaCampos();
	        		return;
				}
				turmaSelecionada = (Turma) comboBoxTurma.getSelectedItem();
				if (turmaSelecionada == null) {
					return;
				}
	        	inputNomeTurma.setText(turmaSelecionada.getNomeTurma());
	        	selecionaComboBoxCurso();
	        	preencheComboBoxAno();
	        	inputDataInicio.setDate(turmaSelecionada.getDataInicio());
	        	inputDataFinal.setDate(turmaSelecionada.getDataFinal());
	        	preencheCheckBoxHorarios();
	        }
	    });
		
		panel.add(comboBoxTurma);
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String nomeTurma = inputNomeTurma.getText();
				Object curso = comboBoxCurso.getSelectedItem();
				String ano = comboBoxAno.getSelectedItem().toString();
				Date dataInicial = inputDataInicio.getDate();
				Date dataFinal = inputDataFinal.getDate();
				
				if (nomeTurma.length() == 0) {
					JOptionPane.showMessageDialog(null, "ERRO, o nome da turma não foi preenchido");
					return;
				}
				if (curso == "Selecione o curso") {
					JOptionPane.showMessageDialog(null, "ERRO, o nome do curso não foi selecionado");
					return;
				}
				if (ano == "Selecione o ano") {
					JOptionPane.showMessageDialog(null, "ERRO, o ano não foi selecionado");
					return;
				}
				if (dataFinal == null) {
					JOptionPane.showMessageDialog(null, "ERRO, a data final das aulas não foi selecionada");
					return;
				}
				if (dataInicial == null) {
					JOptionPane.showMessageDialog(null, "ERRO, a data de início das aulas não foi selecionada");
					return;
				}
				if (dataInicial.after(dataFinal)) {
					JOptionPane.showMessageDialog(null, "ERRO, a data de inicio é posterior à data final das aulas");
					return;
				}
				if (!sdfAno.format(dataInicial).equals(ano)) {
					JOptionPane.showMessageDialog(null, "ERRO, o ano selecionado é diferente do ano de início das aulas");
					return;
				}
				
				TurmaDao turmaDao = new TurmaDao();
				
				Turma turma = new Turma();
				turma.setIdTurma(turmaSelecionada.getIdTurma());
				turma.setNomeTurma(nomeTurma);
				turma.setCurso((Curso) curso);
				turma.setAno(Integer.parseInt(ano));
				turma.setDataInicio(dataInicial);
				turma.setDataFinal(dataFinal);
				turma.setQuadrosHorarios(horariosSelecionados);
				
				boolean insercaoTurma = turmaDao.alterar(turma);
				
				if (!insercaoTurma) {
					JOptionPane.showMessageDialog(null, "ERRO ao alterar turma no banco de dados");
					limpaCampos();
					return;
				}
				
				PossuiDao possuiDao = new PossuiDao();
				possuiDao.excluir(turmaSelecionada.getIdTurma());
				for (Horario quadroHorarios : horariosSelecionados) {
					boolean insercaoPossui = possuiDao.inserir(turmaSelecionada.getIdTurma(), quadroHorarios.getIdHorario());
					if (!insercaoPossui) {
						JOptionPane.showMessageDialog(null, "ERRO ao alterar horários da turma no banco de dados");
						limpaCampos();
						return;
					}
				}
								
				limpaCampos();
				preencheComboBoxTurma();
				instanciaJanelaPrincipal.preencheAlunosCriticos();
				instanciaJanelaPrincipal.criaSchedulerPorAula();
				JOptionPane.showMessageDialog(null, "Turma alterada com sucesso");
			
			}
		});
		btnSalvar.setBounds(257, 606, 117, 25);
		panel.add(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaCampos();
				frame. dispose();
			}
		});
		btnCancelar.setBounds(451, 606, 117, 25);
		panel.add(btnCancelar);		
	}
	
	private void preencheCheckBoxHorarios() {
		horariosSelecionados.clear();
		panelCheckbox.removeAll();
		List<Horario> horariosTurma = new ArrayList<>();
		HorarioDao horarioDao = new HorarioDao();
		horariosTurma = horarioDao.pesquisarHorariosPorTurma(turmaSelecionada);
		List<Horario> horarios = new ArrayList<>();
		checkboxs = new ArrayList<>();
		horarios = horarioDao.pesquisarPorNome("");
		for (Horario horario: horarios) {
			JCheckBox checkBoxHorario = new JCheckBox(horario.toString());
			checkBoxHorario.setBounds(0, 0, 0, 0);
			boolean turmaPossuiHorario = verificaSeSelecionado(horariosTurma,horario);
			if (turmaPossuiHorario) {
				checkBoxHorario.setSelected(true);
				horariosSelecionados.add(horario);
			}
			checkBoxHorario.addItemListener(new ItemListener() {
			    @Override
			    public void itemStateChanged(ItemEvent e) {
			        if(e.getStateChange() == ItemEvent.SELECTED) {
			        	horariosSelecionados.add(horario);
			        } else {
			        	horariosSelecionados.remove(horario);
			        };
			    }
			});
			checkboxs.add(checkBoxHorario);
		}
		for (JCheckBox jCheckBox : checkboxs) {
			panelCheckbox.add(jCheckBox);
		}
		panelCheckbox.validate();
	}
	
	private boolean verificaSeSelecionado(List<Horario> quadrosHorariosTurma, Horario quadroHorarios) {
		boolean resposta = false;
		for (int i = 0; i < quadrosHorariosTurma.size(); i++) {
			if (quadrosHorariosTurma.get(i).getIdHorario() == quadroHorarios.getIdHorario()) {
				resposta = true;
			break;
			}
		}
		return resposta;
	}
	
	private void preencheComboBoxAno() {
		for (int i = 0; i < comboBoxAno.getItemCount(); i++) {

			try {
				if ((int)comboBoxAno.getItemAt(i) == turmaSelecionada.getAno()) {
					comboBoxAno.setSelectedIndex(i);
					break;
				}
			} catch (Exception e) {
			}
		}
		
	}
	
	private void selecionaComboBoxCurso() {
		for (int i = 0; i <= comboBoxAno.getItemCount(); i++) {
			try {
				if ((int)((Curso)comboBoxCurso.getItemAt(i)).getIdCurso() == turmaSelecionada.getCurso().getIdCurso()) {
					comboBoxCurso.setSelectedIndex(i);
					break;
				}
			} catch (Exception e) {
			}
		}
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
		try {
			for (JCheckBox jCheckBox : checkboxs) {
				jCheckBox.setSelected(false);
			}
		} catch (Exception e) {
		}
		inputNomeTurma.setText("");
		comboBoxCurso.setSelectedIndex(0);
		comboBoxAno.setSelectedIndex(0);
		inputDataInicio.setDate(null);;
		inputDataFinal.setDate(null);;
	}
}
