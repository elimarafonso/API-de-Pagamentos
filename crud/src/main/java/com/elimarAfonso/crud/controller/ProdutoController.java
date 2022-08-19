package com.elimarAfonso.crud.controller;

//bibliotecas do hateoas, para as paginações
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elimarAfonso.crud.dataVO.ProdutoVO;
import com.elimarAfonso.crud.service.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	private final ProdutoService produtoService;
	private final PagedResourcesAssembler<ProdutoVO> assembler;

	@Autowired
	public ProdutoController(ProdutoService produtoService, PagedResourcesAssembler<ProdutoVO> assembler) {
		this.produtoService = produtoService;
		this.assembler = assembler;
	}

	// endpoint Busca por ID
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public ProdutoVO findById(@PathVariable("id") Long id) {

		ProdutoVO produtoVO = produtoService.findById(id);

		/*
		 * ver na classe o extends RepresentationModel<ProdutoVO> adicionando hateoas
		 * esse methodOn chama este metodo findById, desta classe
		 */
		produtoVO.add(linkTo(methodOn(ProdutoController.class).findById(id)).withSelfRel());
		return produtoVO;
	}

	// Endpoint IMPRIME TODOS PRODUTOS
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "3") int limit,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		// caso direction passado não seja DESC ele seta como padrao ASC, crescente
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		/*
		 * atribuindo os valores da pagina apresentada, direção da ordenação e o
		 * atributo chave da ordenação
		 */
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "nome"));

		// chama a camada Service
		Page<ProdutoVO> produtos = produtoService.findAll(pageable);

		// e para cada produto adiciona um link adiciona o heteoas
		produtos.stream()
				.forEach(p -> p.add(linkTo(methodOn(ProdutoController.class).findById(p.getId())).withSelfRel()));

		// retorna todas as informaçoes da paginação, Ex: primeira pag, quantidade de
		// pag, ultima pag.. etc
		PagedModel<EntityModel<ProdutoVO>> pagedModel = assembler.toModel(produtos);

		return new ResponseEntity<>(pagedModel, HttpStatus.OK);
	}

	// endpoint que cria um novo produto
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public ProdutoVO create(@RequestBody ProdutoVO produtoVO) {

		ProdutoVO proVO = produtoService.create(produtoVO);

		// adicionando o heteaos
		proVO.add(linkTo(methodOn(ProdutoController.class).findById(proVO.getId())).withSelfRel());
		return proVO;
	}

	// endpoint que atualiza um produto
	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public ProdutoVO update(@RequestBody ProdutoVO produtoVO) {
		ProdutoVO proVO = produtoService.update(produtoVO);
		proVO.add(linkTo(methodOn(ProdutoController.class).findById(proVO.getId())).withSelfRel());
		return proVO;
	}

	// endpoint que Deleta um produto
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		produtoService.delete(id);
		// retorna codigo 201 NOCONTENT caso o processo tenha realizado sem erros
		return ResponseEntity.noContent().build();
	}

}
