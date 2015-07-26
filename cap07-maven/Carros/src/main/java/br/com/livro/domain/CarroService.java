package br.com.livro.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarroService {
	private CarroDAO db = new CarroDAO();

	// Lista todos os carros do banco de dados
	public List<Carro> getCarros() {
		try {
			List<Carro> carros = db.getCarros();
			return carros;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Carro>();
		}
	}

	// Busca um carro pelo id
	public Carro getCarro(Long id) {
		try {
			return db.getCarroById(id);
		} catch (SQLException e) {
			return null;
		}
	}

	// Deleta o carro pelo id
	public boolean delete(Long id) {
		try {
			return db.delete(id);
		} catch (SQLException e) {
			return false;
		}
	}

	// Salva ou atualiza o carro
	public boolean save(Carro carro) {
		try {
			db.save(carro);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	// Busca o carro pelo nome
	public List<Carro> findByName(String name) {
		try {
			return db.findByName(name);
		} catch (SQLException e) {
			return null;
		}
	}

	public List<Carro> findByTipo(String tipo) {
		try {
			return db.findByTipo(tipo);
		} catch (SQLException e) {
			return null;
		}
	}
}
