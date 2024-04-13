package br.com.infuse.crudsb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.infuse.crudsb.entitiy.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
