package br.com.tarefas;

import javax.persistence.Persistence;

// Testar as configurações ou a criação do banco só precisa ser executado uma vez.
public class AppTest 
{
	public static void main(String[] args) {
		Persistence.createEntityManagerFactory("tarefas");
	}
}
