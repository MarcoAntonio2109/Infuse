package br.com.infuse.crudsb.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.infuse.crudsb.dto.ConsultaPedidoDTO;
import br.com.infuse.crudsb.entitiy.Pedido;


public class PedidoSpecification implements Specification<Pedido> {

	private static final long serialVersionUID = -7956851914498818275L;
	private final ConsultaPedidoDTO dto;

    public PedidoSpecification(ConsultaPedidoDTO dto) {
        this.dto = dto;
    }

    @Override
    public Predicate toPredicate(Root<Pedido> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if(dto.getCliente() != null) {
        	predicates.add(criteriaBuilder.equal(root.get("cliente"), dto.getCliente()));
        }
        
        if(dto.getProduto() != null) {
        	predicates.add(criteriaBuilder.equal(root.get("produto"), dto.getProduto()));
        }
        
        if (dto.getNumControle() != null) {
            predicates.add(criteriaBuilder.equal(root.get("numControle"), dto.getNumControle()));
        }
        
        if (dto.getData() != null) {
            predicates.add(criteriaBuilder.equal(root.get("dataCadastro"), dto.getData()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }


}
