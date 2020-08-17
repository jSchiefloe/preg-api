package no.nav.service.pensjon.regler.util.test;

import static org.junit.Assert.*;
import org.junit.*;
import java.text.SimpleDateFormat;
import no.nav.domain.pensjon.regler.beregning2011.SisteAldersberegning2011;
import no.nav.domain.pensjon.regler.grunnlag.Persongrunnlag;
import no.nav.domain.pensjon.regler.grunnlag.Uforehistorikk;
import no.nav.domain.pensjon.regler.grunnlag.Uforeperiode;
import no.nav.domain.pensjon.regler.kode.UforeTypeCti;
import no.nav.domain.pensjon.regler.vedtak.VilkarsVedtak;
import no.nav.service.pensjon.regler.util.ForstevirkFraUforeperiodeUtil;


public class ForstevirkFraUforeperiodeUtilTest {
	Persongrunnlag avdod = null;
	Persongrunnlag eps = null;
	SisteAldersberegning2011 sisteAP2011 = null;
	
	SimpleDateFormat df = new SimpleDateFormat("dd.mm.yyyy");

	@Before
	public final void setUp() throws Exception {
		Uforeperiode viu = new Uforeperiode();
		viu.setUft( df.parse("01.01.1991") );
		viu.setVirk( df.parse("01.01.1900") );
		viu.setUforeType( new UforeTypeCti("VIRK_IKKE_UFOR") );

		eps = new Persongrunnlag();
		eps.setUforeHistorikk( new Uforehistorikk() );
		eps.getUforeHistorikk().getUforeperiodeListe().add( viu );
		
		avdod = new Persongrunnlag();
		avdod.setUforeHistorikk( new Uforehistorikk() );
		avdod.getUforeHistorikk().getUforeperiodeListe().add( viu );
		
		sisteAP2011 = new SisteAldersberegning2011();
		sisteAP2011.setAvdodesPersongrunnlag( avdod );
		sisteAP2011.setEps( eps );
		
		VilkarsVedtak gjrVV = new VilkarsVedtak();
		gjrVV.setForsteVirk( df.parse("02.01.1991")); 
		sisteAP2011.setAnvendtGjenlevenderettVedtak( gjrVV );
	}

	/**
	 * Tester at persongrunnlag for EPS i SisteAldersberegning2011's f�r satt f�rstevirk (PREG intern variabel). 
	 */
	@Test
	public void testH�ndteringAvSisteAldersberegning_eps() throws Exception {
		ForstevirkFraUforeperiodeUtil.forsteVirkHack( sisteAP2011 );
		
		assertNotNull( eps.getPREG_forsteVirk() );
		assertEquals( df.parse("01.01.1991"), eps.getPREG_forsteVirk() );
//		assertEquals( date("1-jan-1990"), eps.getPREG_forsteVirk() );
	}

	/**
	 * Tester at persongrunnlag for Avd�d i SisteAldersberegning2011's f�r satt f�rstevirk (PREG intern variabel).
	 *  
	 */
	@Test
	public void testH�ndteringAvSisteAldersberegning_AvdodViu() throws Exception {
		ForstevirkFraUforeperiodeUtil.forsteVirkHack( sisteAP2011 );

		assertNotNull( avdod.getPREG_forsteVirk() );
		assertEquals( df.parse("01.01.1991"), avdod.getPREG_forsteVirk() );
		assertEquals( df.parse("01.01.1991"), sisteAP2011.getAnvendtGjenlevenderettVedtak().getForsteVirk() );
	}
	
	@Test
	public void testH�ndteringAvSisteAldersberegning_AvdodGjr() throws Exception {
		sisteAP2011.getAnvendtGjenlevenderettVedtak().setForsteVirk( df.parse("31.12.1990") );
		ForstevirkFraUforeperiodeUtil.forsteVirkHack( sisteAP2011 );

		assertNotNull( avdod.getPREG_forsteVirk() );
		assertEquals( df.parse("31.12.1990"), avdod.getPREG_forsteVirk() );
		assertEquals( df.parse("31.12.1990"), sisteAP2011.getAnvendtGjenlevenderettVedtak().getForsteVirk() );
	}
	
	@Test
	public void testH�ndteringAvSisteAldersberegning_AvdodGjr_nullVv() throws Exception {
		sisteAP2011.setAnvendtGjenlevenderettVedtak( null );
		ForstevirkFraUforeperiodeUtil.forsteVirkHack( sisteAP2011 );

		assertNotNull( avdod.getPREG_forsteVirk() );
		assertEquals( df.parse("01.01.1991"), avdod.getPREG_forsteVirk() );
	}
	
	@Test
	public void testH�ndteringAvSisteAldersberegning_AvdodGjr_nullFv() throws Exception {
		sisteAP2011.getAnvendtGjenlevenderettVedtak().setForsteVirk( null );
		ForstevirkFraUforeperiodeUtil.forsteVirkHack( sisteAP2011 );

		assertNotNull( avdod.getPREG_forsteVirk() );
		assertEquals( df.parse("01.01.1991"), avdod.getPREG_forsteVirk() );
	}

}
