
package ohtu.verkkokauppa;

import ohtu.verkkokauppa.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class VerkkokauppaTest {
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);
    
        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
    
        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);              
    
        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");
        
        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        // nimi, viite, tiliNumero, kaupanTili, summa
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 5);   
    }

    @Test
    public void kaksiEriTuotettaOstoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);
    
        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // määritellään että tuote numero 2 on mehu jonka hinta on 4 ja saldo 9
        when(varasto.saldo(2)).thenReturn(9); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "mehu", 4));
    
        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);              
    
        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);     // ostetaan tuotetta numero 2 eli mehua
        k.tilimaksu("pekka", "12345");
        
        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        // nimi, viite, tiliNumero, kaupanTili, summa
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 9);   
    }

    @Test
    public void kaksiSamaaTuotettaOstoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);
    
        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
    
        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);              
    
        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");
        
        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        // nimi, viite, tiliNumero, kaupanTili, summa
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 10);   
    }

    @Test
    public void kaksiTuotettaToinenLoppuOstoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);
    
        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // määritellään että tuote numero 2 on mehu jonka hinta on 4 ja saldo 0
        when(varasto.saldo(2)).thenReturn(0); 
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "mehu", 4));
    
        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);              
    
        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);     // ostetaan tuotetta numero 2 eli mehua
        k.tilimaksu("pekka", "12345");
        
        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        // nimi, viite, tiliNumero, kaupanTili, summa
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 5);   
    }

    @Test
    public void aloitaAsiointiNollaaOstoskorinHinnan() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);
    
        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
    
        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);              
    
        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        verify(pankki, times(0)).tilisiirto(anyString(), anyInt(), anyString(), anyString(), anyInt());

        // tehdään ostokset uudestaan
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");
        
        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        // nimi, viite, tiliNumero, kaupanTili, summa
        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455", 5);   
    }

    @Test
    public void kauppaPyytaaUudenViitenumeronJokaiselleMaksulle() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);
    
        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
    
        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);              
    
        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // tarkistetaan että tässä vaiheessa viitegeneraattorin metodia uusi()
        // on kutsuttu kerran
        verify(viite, times(1)).uusi();

        // tehdään ostokset uudestaan
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // tarkistetaan että tässä vaiheessa viitegeneraattorin metodia uusi()
        // on kutsuttu kahdesti
        verify(viite, times(2)).uusi();
    }

    @Test
    public void poistaKoristaPalauttaaVarastoon() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);
    
        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10); 
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
    
        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);              
    
        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa

        // poistetaan korista tuote numero 1 eli maito
        k.poistaKorista(1);
        // tarkistetaan että poisto palauttaa varastoon
        verify(varasto, times(1)).palautaVarastoon(new Tuote(1, "maito", 5));
    }
}
