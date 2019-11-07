package util;

import view.JanelaPrincipal;

public class RunnableCriacaoSchedulesDia implements Runnable {
	
	private JanelaPrincipal instanciaJanelaPrincipal;
	
	public RunnableCriacaoSchedulesDia(JanelaPrincipal _instanciaJanelaPrincipal) {
	    this.instanciaJanelaPrincipal = _instanciaJanelaPrincipal;
	  }
	
	@Override
	public void run() {
		instanciaJanelaPrincipal.criaSchedulerPorAula();

	}

}
