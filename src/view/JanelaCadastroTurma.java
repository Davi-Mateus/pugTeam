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
import javax.swing.JComboBox;

import dao.CursoDao;
import dao.PossuiDao;
import dao.HorarioDao;
import dao.TurmaDao;
import model.Curso;
import model.Horario;
import model.Turma;
import javax.swing.JCheckBox;
import java.awt.GridLayout;
import com.toedter.calendar.JDateChooser;

public class JanelaCadastroTurma {

	private JPanel panel;
	private JPanel panelCheckbox;
	public JFrame frame;
	private JLabel lblNomeTurma;
	private JLabel lblDataFinalAulas;
	private JLabel lblAno;
	private JLabel lblNomeCurso;
	private JLabel lblDataIncioAulas;
	private JTextField inputNomeTurma;
	private JComboBox<Object> comboBoxCurso;
	private JComboBox<Object> comboBoxAno;
	private JCheckBox checkBoxHorario;
	private JDateChooser inputDataInicio;
	private JDateChooser inputDataFinal;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private ArrayList<Horario> horariosSelecionados = new ArrayList<>();
	private ArrayList<JCheckBox> checkboxs;
	
	private SimpleDateFormat sdfAno = new SimpleDateFormat("yyyy");
	
	private int anoAtual = Integer.parseInt(sdfAno.format(new Date()));
	private JLabel lblHorario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaCadastroTurma window = new JanelaCadastroTurma(null);
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
	public JanelaCadastroTurma(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Cadastro de turma");
		frame.setBounds(0, 0, 853, 643);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		panelCheckbox = new JPanel();
		panelCheckbox.setBounds(244, 312, 544, 228);
		panel.add(panelCheckbox);
		panelCheckbox.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblNomeTurma = new JLabel("Nome da turma:");
		lblNomeTurma.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNomeTurma.setBounds(58, 32, 130, 20);
		panel.add(lblNomeTurma);
		
		inputNomeTurma = new JTextField();
		inputNomeTurma.setBounds(244, 32, 362, 25);
		panel.add(inputNomeTurma);
		inputNomeTurma.setColumns(10);
		
		lblNomeCurso = new JLabel("Curso");
		lblNomeCurso.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNomeCurso.setBounds(58, 88, 181, 15);
		panel.add(lblNomeCurso);
		
		comboBoxCurso = new JComboBox<Object>();
		List<Curso> cursos = new ArrayList<>();
		CursoDao cursoDao = new CursoDao();
		cursos = cursoDao.pesquisarPorNome("");
		comboBoxCurso.addItem("Selecione o curso");
		for (Curso curso: cursos) {
			comboBoxCurso.addItem(curso);
		}
		comboBoxCurso.setBounds(244, 88, 362, 25);
		panel.add(comboBoxCurso);
		
		lblAno = new JLabel("Ano:");
		lblAno.setFont(new Font("Dialog", Font.BOLD, 14));
		lblAno.setBounds(58, 144, 130, 20);
		panel.add(lblAno);
		
		comboBoxAno = new JComboBox<Object>();
		comboBoxAno.setBounds(244, 144, 362, 25);
		panel.add(comboBoxAno);
		comboBoxAno.addItem("Selecione o ano");
		for (int i = 2018; i <= anoAtual + 1; i++) {
			comboBoxAno.addItem(i);
		}
		
		lblDataIncioAulas = new JLabel("Data Início aulas:");
		lblDataIncioAulas.setFont(new Font("Dialog", Font.BOLD, 14));
		lblDataIncioAulas.setBounds(58, 200, 147, 20);
		panel.add(lblDataIncioAulas);
				
		lblDataFinalAulas = new JLabel("Data final aulas:");
		lblDataFinalAulas.setFont(new Font("Dialog", Font.BOLD, 14));
		lblDataFinalAulas.setBounds(58, 256, 147, 20);
		panel.add(lblDataFinalAulas);
		
		
		lblHorario = new JLabel("Horários de aula:");
		lblHorario.setFont(new Font("Dialog", Font.BOLD, 14));
		lblHorario.setBounds(58, 312, 147, 20);
		panel.add(lblHorario);
		
		preencheCheckBoxHorarios();
		
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
				if (dataInicial == null) {
					JOptionPane.showMessageDialog(null, "ERRO, a data de início das aulas não foi selecionada");
					return;
				}
				if (dataFinal == null) {
					JOptionPane.showMessageDialog(null, "ERRO, a data final das aulas não foi selecionada");
					return;
				}
				if (dataInicial.after(dataFinal)) {
					JOptionPane.showMessageDialog(null, "ERRO, a data de início é posterior à data final das aulas");
					return;
				}
				if (!sdfAno.format(dataInicial).equals(ano)) {
					JOptionPane.showMessageDialog(null, "ERRO, o ano selecionado é diferente do ano de início das aulas");
					return;
				}
				
				TurmaDao turmaDao = new TurmaDao();
				
				Turma turma = new Turma();
				turma.setNomeTurma(nomeTurma);
				turma.setCurso((Curso) curso);
				turma.setAno(Integer.parseInt(ano));
				turma.setDataInicio(dataInicial);
				turma.setDataFinal(dataFinal);
				turma.setQuadrosHorarios(horariosSelecionados);
				
				boolean insercaoTurma = turmaDao.inserir(turma);
				
				if (!insercaoTurma) {
					JOptionPane.showMessageDialog(null, "ERRO ao inserir turma no banco de dados");
					limpaCampos();
					return;
				}
				
				turma.setIdTurma(turmaDao.buscarUltimoId());
				
				for (Horario horario : horariosSelecionados) {
					PossuiDao possuiDao = new PossuiDao();		
					
					boolean insercaoPossui = possuiDao.inserir(turma.getIdTurma(), horario.getIdHorario());
					
					if (!insercaoPossui) {
						JOptionPane.showMessageDialog(null, "ERRO ao inserir horários da turma no banco de dados");
						limpaCampos();
						return;
					}
				}
				limpaCampos();
				instanciaJanelaPrincipal.criaSchedulerPorAula();
				JOptionPane.showMessageDialog(null, "Turma inserida com sucesso");
			}
		});
		btnSalvar.setBounds(257, 550, 117, 25);
		panel.add(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaCampos();
				frame. dispose();
			}
		});
		btnCancelar.setBounds(451, 550, 117, 25);
		panel.add(btnCancelar);		
		
		inputDataInicio = new JDateChooser();
		inputDataInicio.setDateFormatString("dd/MM/yyyy");
		inputDataInicio.setBounds(244, 200, 362, 25);
		panel.add(inputDataInicio);
		
		inputDataFinal = new JDateChooser();
		inputDataFinal.setDateFormatString("dd/MM/yyyy");
		inputDataFinal.setBounds(244, 256, 362, 25);
		panel.add(inputDataFinal);
	}
	
	private void preencheCheckBoxHorarios() {
		panelCheckbox.removeAll();
		List<Horario> horario = new ArrayList<>();
		checkboxs = new ArrayList<>();
		HorarioDao horarioDao = new HorarioDao();
		horario = horarioDao.pesquisarPorNome("");
		for (Horario quadroHorarios: horario) {
			checkBoxHorario = new JCheckBox(quadroHorarios.toString());
			checkBoxHorario.setBounds(0, 0, 0, 0);
			checkBoxHorario.addItemListener(new ItemListener() {
			    @Override
			    public void itemStateChanged(ItemEvent e) {
			        if(e.getStateChange() == ItemEvent.SELECTED) {
			        	horariosSelecionados.add(quadroHorarios);
			        } else {
			        	horariosSelecionados.remove(quadroHorarios);
			        };
			    }
			});
			checkboxs.add(checkBoxHorario);
		}
		for (JCheckBox jCheckBox : checkboxs) {
			panelCheckbox.add(jCheckBox);
		}
	}
	
	private void limpaCampos() {
		for (JCheckBox jCheckBox : checkboxs) {
			jCheckBox.setSelected(false);
		}
		preencheCheckBoxHorarios();
		inputNomeTurma.setText("");
		comboBoxCurso.setSelectedIndex(0);
		comboBoxAno.setSelectedIndex(0);
		inputDataInicio.setDate(null);
		inputDataFinal.setDate(null);
	}
}
