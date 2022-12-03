package br.com.tarefas;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.dao.DaoGeneric;
import br.com.entidades.Tarefa;

@ViewScoped
@ManagedBean(name = "tController")
public class TarefaController {

	private Tarefa tarefa = new Tarefa();
	private Tarefa filterTar = new Tarefa();
	private DaoGeneric<Tarefa> daoGeneric = new DaoGeneric<Tarefa>();
	private List<Tarefa> tarefas = new ArrayList<Tarefa>();
	private List<Tarefa> filter = new ArrayList<Tarefa>();
	
	public String salvar() {
		if(tarefa.getSituacao() == null) {
			tarefa.setSituacao("Em andamento"); // aqui estou usando o set para transformar a situação.
		}
		daoGeneric.salvar(tarefa);
		tarefa = new Tarefa();
		return "/index.xhtml";
	}
	
	@PostConstruct
	public void carregarTarefas() {
		tarefas = daoGeneric.getList(Tarefa.class);
		filter = tarefas;
		filterTar.setSituacao("Em andamento");
		filterList();
		filterTar = new Tarefa();
	}
	
	public void filterList() {
		List<Tarefa> listatemp = new ArrayList<Tarefa>();
		if(filterTar.getSituacao() != null || filterTar.getSituacao().isBlank()) {
			listatemp = tarefas.stream().filter(
					tarefa -> tarefa.getSituacao().equals(filterTar.getSituacao())
				).collect(Collectors.toList());
		}
		
		if(filterTar.getResponsavel() != null && filterTar.getResponsavel() != "") {
			listatemp = listatemp.stream().filter(
						tarefa -> tarefa.getResponsavel().equals(filterTar.getResponsavel())
					).collect(Collectors.toList());
		}
		
		if(filterTar.getId() != null) {
			listatemp = listatemp.stream().filter(
						tarefa -> tarefa.getId().equals(filterTar.getId())
					).collect(Collectors.toList());
		}
		
		if(filterTar.getTitulo() != null && filterTar.getTitulo() != "") {
			listatemp = listatemp.stream().filter(
						tarefa -> tarefa.getTitulo().equals(filterTar.getTitulo())
					).collect(Collectors.toList());
		}
		
		filter = new ArrayList<Tarefa>();
		listatemp.forEach(p -> filter.add(p));
		
	}
	
	public Tarefa listBySituacao(String situacao) {
		return	tarefas.stream().filter(
						tarefa -> tarefa.getSituacao().equals(situacao)
					).findFirst().orElse(null);
	}
	
	public String delete() {
		
		daoGeneric.deleteById(tarefa);
		
		tarefa = new Tarefa();
		carregarTarefas();
		
		return "/";
	}
	
	public String atualizar() {
		tarefa = daoGeneric.merge(tarefa);
		carregarTarefas();
		
		return "";
	}
	
	public String atualizarAcao() {
		tarefa.setSituacao("Concluida");
		
		tarefa = daoGeneric.merge(tarefa);
		carregarTarefas();
		
		return "";
	}
	
	public String redirectLink() {
		return "/cadastroTarefa.xhtml";
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(Tarefa tarefas) {
		this.tarefa = tarefas;
	}

	public Tarefa getFilterTar() {
		return filterTar;
	}

	public void setFilterTar(Tarefa filterTar) {
		this.filterTar = filterTar;
	}

	public List<Tarefa> getFilter() {
		return filter;
	}

	public void setFilter(Tarefa filter) {
		this.filterTar = filter;
	}
	
	
}
