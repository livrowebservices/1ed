package br.com.livro.domain;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;

@Component
public class CarroService {
	@Autowired
	private CarroDAO db;

	// Lista todos os carros do banco de dados
	public List<Carro> getCarros() {
		List<Carro> carros = db.getCarros();
		return carros;
	}

	// Busca um carro pelo id
	public Carro getCarro(Long id) {
		return db.getCarroById(id);
	}

	// Deleta o carro pelo id
	@Transactional(rollbackFor = Exception.class)
	public boolean delete(Long id) {
		return db.delete(id);
	}

	// Salva ou atualiza o carro
	@Transactional(rollbackFor = Exception.class)
	public boolean save(Carro carro) {
		db.saveOrUpdate(carro);
		return true;
	}

	// Busca o carro pelo nome
	public List<Carro> findByName(String name) {
		return db.findByName(name);
	}

	public List<Carro> findByTipo(String tipo) {
		return db.findByTipo(tipo);
	}
}
