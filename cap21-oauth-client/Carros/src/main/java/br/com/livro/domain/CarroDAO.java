package br.com.livro.domain;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unchecked")
public class CarroDAO extends HibernateDAO<Carro> {
	public CarroDAO() {
		// Informa o tipo da entidade para o Hibernate
		super(Carro.class);
	}

	// Consulta um carro pelo id
	public Carro getCarroById(Long id) {
		// O Hibernate consulta automaticamente pelo id
		return super.get(id);
	}

	// Busca um carro pelo nome
	public Carro findByName(String nome) {
		Query q = getSession().createQuery("from Carro where nome=?");
		q.setString(0, nome);
		List<Carro> list = q.list();
		Carro c = list.isEmpty() ? null : list.get(0);
		return c;
	}

	// Busca um carro pelo tipo
	public List<Carro> findByTipo(String tipo) {
		Query q = getSession().createQuery("from Carro where tipo=?");
		q.setString(0, tipo);
		List<Carro> carros = q.list();
		return carros;
	}

	// Consulta todos os carros
	public List<Carro> getCarros() {
		Query q = getSession().createQuery("from Carro");
		List<Carro> carros = q.list();
		return carros;
	}

	// Insere ou atualiza um carro
	public void salvar(Carro c) {
		super.save(c);
	}

	// Deleta o carro pelo id
	public boolean delete(Long id) {
		Carro c = get(id);
		delete(c);
		return true;
	}
}
