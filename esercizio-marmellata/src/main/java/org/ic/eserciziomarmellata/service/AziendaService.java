package org.ic.eserciziomarmellata.service;


import lombok.extern.slf4j.Slf4j;
import org.ic.eserciziomarmellata.dto.MaggioreIncassoResponse;
import org.ic.eserciziomarmellata.dto.VenditaRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AziendaService {

    Map<Long,Map<String,Double>> aziendeMarmellate=new HashMap<>();


    public void registraIncasso(long idAzienda,VenditaRequest request) {
        double importo = request.getImporto();
        String tipoMarmellata = request.getTipoMarmellata();
        Map<String,Double> vecchiaMappa=aziendeMarmellate.get(idAzienda);
        if(vecchiaMappa==null){//caso in cui l'azienda non abbia mai venduto
            Map<String,Double> nuovaMarmellata=new HashMap<>();
            nuovaMarmellata.put(tipoMarmellata, importo);
            aziendeMarmellate.put(idAzienda,nuovaMarmellata);
        }else{//caso in cui l'azienda ha già qualche vendita registrata ma si deve cotnrollare se aveva già venduto quella marmellata

            if(!vecchiaMappa.containsKey(tipoMarmellata)){
                vecchiaMappa.put(tipoMarmellata,Double.valueOf(0));
            }
            double nuovoIncasso=vecchiaMappa.get(tipoMarmellata);
            nuovoIncasso+=importo;
            vecchiaMappa.put(tipoMarmellata,nuovoIncasso);

        }
        log.info("Vendita registrata");
    }

    public MaggioreIncassoResponse maggioreIncasso(long idAzienda) {

        Map<String,Double>mappaMarmellate=aziendeMarmellate.get(idAzienda);
        if(mappaMarmellate==null){throw new IllegalArgumentException("Quest'azienda non ha marmellate registrate.");}

        String nomeMarmellataRet="";
        double prezzoMax=-1;

        for(String tipoMarmellata:mappaMarmellate.keySet()){

            double incassoMarmellataCorrente= mappaMarmellate.get(tipoMarmellata);

            if(incassoMarmellataCorrente>prezzoMax){
                nomeMarmellataRet=tipoMarmellata;
                prezzoMax=mappaMarmellate.get(tipoMarmellata);
            }
        }

        return new MaggioreIncassoResponse(nomeMarmellataRet,prezzoMax);

    }
}
