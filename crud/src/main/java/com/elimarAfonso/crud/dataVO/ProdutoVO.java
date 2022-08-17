package com.elimarAfonso.crud.dataVO;

import java.io.Serializable;

import org.modelmapper.ModelMapper;

import com.elimarAfonso.crud.entity.Produto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//ajuda mostrar a ordem do retorno dos atributos
@JsonPropertyOrder({"id","nome","estoque","preco"})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProdutoVO implements Serializable{
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("nome")
	private String nome;
	
	@JsonProperty("estogue")
	private Integer estogue;
	
	@JsonProperty("preco")
	private Double preco;

	
	//recebe um Produto e retorna um ProdutoVO
	
	public static ProdutoVO create (Produto produto) {
		return new ModelMapper().map(produto,ProdutoVO.class);
	}
}
