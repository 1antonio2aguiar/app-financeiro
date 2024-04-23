package com.financeiro.financeiroapi.repository.pessoas;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import org.apache.commons.lang3.time.DateUtils;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.util.StringUtils;

import com.financeiro.financeiroapi.filter.PessoasFilter;
import com.financeiro.financeiroapi.model.Pessoas;
import com.financeiro.financeiroapi.model.Pessoas_;

public class PessoasRepositoryImpl implements PessoasRepositoryQuery{
	
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<Pessoas> filtrar(PessoasFilter pessoasFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Pessoas> criteria = builder.createQuery(Pessoas.class);
	    Root<Pessoas> root = criteria.from(Pessoas.class);
	
	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);
	
	    Predicate[] predicates = criarRestricoes(pessoasFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);
	
	    TypedQuery<Pessoas> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);

	    return new PageImpl<>(query.getResultList(), pageable, total(pessoasFilter));
		
	}
	
	private Predicate[] criarRestricoes(
			PessoasFilter pessoasFilter, CriteriaBuilder builder, Root<Pessoas> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		// ID
		if(pessoasFilter.getId() != null) {
			predicates.add(builder.equal(root.get(Pessoas_.ID), pessoasFilter.getId()));
		}
		
		// CPF CNPJ
		if (StringUtils.hasLength(pessoasFilter.getCpfCnpj())) {
		      predicates.add(
		          builder.like(
		              builder.lower(root.get(Pessoas_.CPF_CNPJ)),
		              "%" + pessoasFilter.getCpfCnpj().toLowerCase() + "%"));
		}
		
		// NOME
	    if (StringUtils.hasLength(pessoasFilter.getNome())) {
	      predicates.add(
	          builder.like(
	              builder.lower(root.get(Pessoas_.NOME)),
	              "%" + pessoasFilter.getNome().toLowerCase() + "%"));
	    }
	    
	    //System.err.println("NASCIMENTO 	ENTROU AQUI : " + pessoasFilter.getDataNascimento());
	    // DATA_REGISTRO - DATA NASCIMENTO
	    /*if (pessoasFilter.getDataRegistro() != null) {
    		Date data = pessoasFilter.getDataRegistro();
			//remove time portion from specified date: now dd/mm/yy 00:00
		    Date startDate = DateUtils.truncate(data, Calendar.DATE);
		    //new date with time initialized to 23:59:59
		    Date endDate = DateUtils.addSeconds(DateUtils.addDays(startDate, 1), - 1);
		    System.err.println("DATA  : " + data + " start " + startDate + " end " + endDate);
			predicates.add(
		    		builder.between(root.get(Pessoas_.DATA_REGISTRO), startDate, endDate));
				
			// Isso aqui e para exemplos futuros	    	
			//	    	predicates.add(
			//					builder.greaterThanOrEqualTo(root.get(PessoasGeral_.DATA_REGISTRO), dataInicial));
			//			
			//			predicates.add(
			//					builder.lessThanOrEqualTo(root.get(PessoasGeral_.DATA_REGISTRO), dataFinal));
	    }*/
	    
	    if (pessoasFilter.getDataRegistro() != null) {
    		Date data = pessoasFilter.getDataRegistro();
	    	predicates.add(builder.equal(root.get(Pessoas_.DATA_REGISTRO), data));
	    }
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
	    int paginaAtual = pageable.getPageNumber();
	    int totalRegistrosPorPagina = pageable.getPageSize();
	    int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
	
	    query.setFirstResult(primeiroRegistroDaPagina);
	    query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(PessoasFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<Pessoas> root = criteria.from(Pessoas.class);

	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);

	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	}	

}
