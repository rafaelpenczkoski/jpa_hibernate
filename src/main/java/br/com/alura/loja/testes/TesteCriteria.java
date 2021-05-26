package br.com.alura.loja.testes;

import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class TesteCriteria {

    public static void main(String[] args) {
        CadastroDeProduto.cadastrarProdutos();
        CadastroDePedido.cadastrarPedidos();

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);
        List<Produto> produtosList = produtoDao.buscarPorCriteria("PS5", null, LocalDate.now());
        //produtosList.forEach(System.out::println);
        em.close();
    }
}
