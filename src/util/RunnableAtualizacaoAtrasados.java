package util;

import view.JanelaPrincipal;

public class RunnableAtualizacaoAtrasados implements Runnable {
	
	private JanelaPrincipal instanciaJanelaPrincipal;
	
	public RunnableAtualizacaoAtrasados(JanelaPrincipal _instanciaJanelaPrincipal) {
	    this.instanciaJanelaPrincipal = _instanciaJanelaPrincipal;
	  }
	
	@Override
	public void run() {
		instanciaJanelaPrincipal.preencheAlunosAtrasados();

	}

}