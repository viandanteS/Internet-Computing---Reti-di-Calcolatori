package wsdlexercise.marmellata;

public interface AziendaService {
    public void Vendita(int idAzienda,String tipoMarmellata,double importo);
    public IncassoProdotto MaggioreIncasso(int idAzienda);
}
