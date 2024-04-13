package br.com.infuse.crudsb.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.infuse.crudsb.entitiy.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {

	@Query(value = "SELECT count(*) FROM PEDIDO P WHERE P.num_controle = :numControle", nativeQuery = true)
	int validaNumControleExistente(@Param("numControle")Long numControle);

	List<Pedido> findAll(Specification<Pedido> specification);
	
}
