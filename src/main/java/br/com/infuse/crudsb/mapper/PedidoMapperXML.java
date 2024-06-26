package br.com.infuse.crudsb.mapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@XmlRootElement(name = "pedido")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class PedidoMapperXML {

    @XmlElement(name = "numControle")
    private Long numControle;

    @XmlElement(name = "produto")
    private Long produto;

    @XmlElement(name = "quantidade")
    private int quantidade;

    @XmlElement(name = "cliente")
    private Long cliente;

    @XmlElement(name = "data")
    private String data;

    
}
