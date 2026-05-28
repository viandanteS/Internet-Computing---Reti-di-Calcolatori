package wsdlexercise.marmellata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AziendaServiceImpl implements AziendaService{

    Map<Integer, List<IncassoProdotto>> aziendeProdotti=new HashMap<>();

    @Override
    public void Vendita(int idAzienda, String tipoMarmellata, double importo) {

        List<IncassoProdotto> incassoProdotti=aziendeProdotti.get(idAzienda);

        if(incassoProdotti==null){throw new IllegalArgumentException("Azienda inesistente");}

        for(IncassoProdotto i:incassoProdotti){
            if(i.getTipoMarmellata().equals(tipoMarmellata)){
                double oldImporto = i.getImporto() ;
                double newImporto = oldImporto + importo;
                i.setImporto(newImporto);
            }
        }
    }

    @Override
    public IncassoProdotto MaggioreIncasso(int idAzienda) {

        List<IncassoProdotto> incassoProdotti=aziendeProdotti.get(idAzienda);

        if(incassoProdotti==null){throw new IllegalArgumentException("Azienda inesistente");}

        double maxImporto=-1;
        String tipoMarmellataMax="";

        for(IncassoProdotto i:incassoProdotti){
            if(i.getImporto()>maxImporto){
                maxImporto=i.getImporto();
                tipoMarmellataMax=i.getTipoMarmellata();
            }
        }
        IncassoProdotto ret = new IncassoProdotto(tipoMarmellataMax,maxImporto);
        return ret;
    }
}
