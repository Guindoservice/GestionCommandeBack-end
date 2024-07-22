package com.example.cmd.service;
import com.example.cmd.model.Produit;
import com.example.cmd.model.Stock;
import com.example.cmd.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService{
    private StockRepository stockRepository;

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void ajouterProduit(Produit p) {
        Optional<Stock> stockOptional = this.stockRepository.findByProduit(p);

        Stock stockInDB;
        if (stockOptional.isPresent()) {
            // Si le produit existe déjà, mettez à jour la quantité
            stockInDB = stockOptional.get();
            int nouvelleQuantite = stockInDB.getQuantite() + p.getQuantite();
            stockInDB.setQuantite(nouvelleQuantite);
        } else {
            // Sinon, créez un nouveau stock pour le produit
            stockInDB = new Stock();
            stockInDB.setProduit(p);
            stockInDB.setQuantite(p.getQuantite());
        }

        // Sauvegardez ou mettez à jour le stock dans la base de données
        this.stockRepository.save(stockInDB);
    }



    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void retirerProduit(Produit produit, int quantite) {
        Optional<Stock> stock = this.stockRepository.findByProduit(produit);
        if (stock.isPresent()) {
            Stock stockInDB = stock.get();
            int qte = stockInDB.getQuantite();
            if (qte > 0 && quantite<qte) {
                stockInDB.setQuantite(qte - quantite);
                this.stockRepository.save(stockInDB);
            }
        } else {
            System.out.println("Ce produit n'existe pas !!!");
        }
    }


}
