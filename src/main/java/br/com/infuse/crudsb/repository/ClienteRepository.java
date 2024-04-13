package br.com.infuse.crudsb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.infuse.crudsb.entitiy.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	@Query(value = "SELECT count(*) FROM CLIENTE C WHERE C.CPF = :cpf", nativeQuery = true)
	int validaCpfExistente(@Param("cpf")String cpf);
}
