package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Produto;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ProdutoDao {

    private EntityManager em;

    public ProdutoDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Produto produto) {
        this.em.persist(produto);
    }

    public Produto buscarPorId(Long Id) {
        return this.em.find(Produto.class, Id);
    }

    public List<Produto> buscarTodos() {
        String jpql = "SELECT p FROM Produto p";
        return this.em.createQuery(jpql, Produto.class).getResultList();
    }

    public List<Produto> buscarPorNome(String nome) {
        String jpql = "SELECT p FROM Produto p WHERE p.nome = :nome";
        return this.em.createQuery(jpql, Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public BigDecimal buscarPrecoPorNome(String nome) {
        String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = :nome";
        return this.em.createQuery(jpql, BigDecimal.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }

    public List<Produto> buscarPorNomeDaCategoria(String nome) {
        String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = :nome";
        return this.em.createQuery(jpql, Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public List<Produto> buscarPorParametros(String nome, BigDecimal preco, LocalDate dataCadastro) {
        String jpql
                = "SELECT p\n"
                + "  FROM Produto p\n"
                + " WHERE 1 = 1\n";
        if (nome != null && !nome.trim().isEmpty()) {
            jpql += " AND p.nome = :nome\n";
        }
        if (preco != null) {
            jpql += " AND p.preco = :preco\n";
        }
        if (dataCadastro != null) {
            jpql += " AND p.dataCadastro = :dataCadastro\n";
        }
        TypedQuery<Produto> query = this.em.createQuery(jpql, Produto.class);
        if (nome != null && !nome.trim().isEmpty()) {
            query.setParameter("nome", nome);
        }
        if (preco != null) {
            query.setParameter("preco", preco);
        }
        if (dataCadastro != null) {
            query.setParameter("dataCadastro", dataCadastro);
        }
        return query.getResultList();
    }

    public List<Produto> buscarPorCriteria(String nome, BigDecimal preco, LocalDate dataCadastro) {
        CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
        CriteriaQuery<Produto> query = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> from = query.from(Produto.class);

        Predicate filtros = criteriaBuilder.and();
        if (nome != null && !nome.trim().isEmpty()) {
            filtros = criteriaBuilder.and(filtros, criteriaBuilder.equal(from.get("nome"), nome));
        }
        if (preco != null) {
            filtros = criteriaBuilder.and(filtros, criteriaBuilder.equal(from.get("preco"), preco));
        }
        if (dataCadastro != null) {
            filtros = criteriaBuilder.and(filtros, criteriaBuilder.equal(from.get("dataCadastro"), dataCadastro));
        }
        query.where(filtros);
        return this.em.createQuery(query).getResultList();
    }
}
