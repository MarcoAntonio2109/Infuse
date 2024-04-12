package br.com.infuse.crudsb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.infuse.crudsb.entities.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
