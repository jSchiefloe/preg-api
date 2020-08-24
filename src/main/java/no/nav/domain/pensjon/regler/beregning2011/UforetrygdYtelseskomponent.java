package no.nav.domain.pensjon.regler.beregning2011;

/**
 * PK-27754: Innf�rte nytt felt som skal v�re p� alle uf�retrygdytelser.
 */
public interface UforetrygdYtelseskomponent{
    public int getTidligereBelopAr();
    public void setTidligereBelopAr(int tidligereBelopAr);
}
