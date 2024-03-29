package br.com.marcospcruz.controleestoque.dao;

import java.util.List;

public interface Crud<T> {
	/**
	 * Método responsável em procurar entidade no banco de dados.
	 * 
	 * @param id
	 * @return
	 */
	public T select(int id);

	/**
	 * Método responsável em atualizar a entidade no banco.
	 * 
	 * @param tipoPeca
	 * @return 
	 */
	public T update(T entity);

	public List<T> busca(String namedQuery);

	public T busca(String namedQuery, String parametro, String valor);

	public void delete(T entity);

	public T busca(Class clazz, int id);

	public List<T> buscaList(String query, String parametro, String valor);

}
