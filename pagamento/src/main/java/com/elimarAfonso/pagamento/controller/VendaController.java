package com.elimarAfonso.pagamento.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elimarAfonso.pagamento.service.VendaService;
import com.elimarAfonso.pagamento.vo.VendaVO;

@RestController
@RequestMapping("/venda")
public class VendaController {

	private final VendaService vendaService;
	private final PagedResourcesAssembler<VendaVO> assembler;

	@Autowired
	public VendaController(VendaService vendaService, PagedResourcesAssembler<VendaVO> assembler) {
		this.vendaService = vendaService;
		this.assembler = assembler;
	}

	// endpoint Busca por ID
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public VendaVO findById(@PathVariable("id") Long id) {
		VendaVO vendaVO = vendaService.findById(id);

		vendaVO.add(linkTo(methodOn(VendaController.class).findById(id)).withSelfRel());
		return vendaVO;
	}

	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "3") int limit,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "data"));

		Page<VendaVO> vendas = vendaService.findAll(pageable);
		vendas.stream().forEach(
				venda -> venda.add(linkTo(methodOn(VendaController.class).findById(venda.getId())).withSelfRel()));

		PagedModel<EntityModel<VendaVO>> pagedModel = assembler.toModel(vendas);

		return new ResponseEntity<>(pagedModel, HttpStatus.OK);
	}

	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public VendaVO create(@RequestBody VendaVO vendaVO) {

		VendaVO vendaVOretorno = vendaService.create(vendaVO);

		// adicionando o heteaos
		vendaVOretorno.add(linkTo(methodOn(VendaController.class).findById(vendaVOretorno.getId())).withSelfRel());
		return vendaVOretorno;

	}

}
