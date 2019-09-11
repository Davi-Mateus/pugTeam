package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.Font;
import util.Relatorio;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

public class JanelaRelatorioAPOIA {

	private JPanel panel;
	public JFrame frame;
	private JLabel lblTurma;
	private JButton btnGerar;
	private JButton btnCancelar;
	private JMonthChooser inputMes;
	private JYearChooser inputAno;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaRelatorioAPOIA window = new JanelaRelatorioAPOIA(null);
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
	public JanelaRelatorioAPOIA(JanelaPrincipal instanciaJanelaPrincipal) {
		initialize(instanciaJanelaPrincipal);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(JanelaPrincipal instanciaJanelaPrincipal) {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Relatório de alunos programa APOIA");
		frame.setBounds(0, 0, 761, 180);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblTurma = new JLabel("Selecione o período:");
		lblTurma.setFont(new Font("Dialog", Font.BOLD, 14));
		lblTurma.setBounds(30, 20, 190, 20);
		panel.add(lblTurma);
		
		btnGerar = new JButton("Gerar");
		btnGerar.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				
				int mes = inputMes.getMonth() + 1;
				int ano = inputAno.getYear();
								
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("mes", mes);
				parametros.put("ano", ano);
								
				try {
					new Relatorio().gerar("/home/rafael/GitHub/pugTeam/src/relatorios/AlunosAPOIA.jasper", parametros, "Relatório de alunos programa APOIA");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		btnGerar.setBounds(550, 18, 125, 25);
		panel.add(btnGerar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame. dispose();
			}
		});
		btnCancelar.setBounds(310, 95, 117, 25);
		panel.add(btnCancelar);
		
		inputMes = new JMonthChooser();
		inputMes.setBounds(230, 20, 125, 25);
		panel.add(inputMes);
		
		inputAno = new JYearChooser();
		inputAno.setBounds(380, 20, 125, 25);
		panel.add(inputAno);
		
		
	}
}
