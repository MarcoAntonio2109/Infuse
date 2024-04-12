package br.com.infuse.crudsb.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Pedido")
@Getter
@Setter
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "num_controle")
    private Long numControle;

    @Column(name = "data_cadastro")
    private Date dataCadastro;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "quantidade")
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id", insertable = true, updatable = true)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_produto", referencedColumnName = "id_produto", insertable = true, updatable = true)
    private Produto produto;

}

	
    
    

