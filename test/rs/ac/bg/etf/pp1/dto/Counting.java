package rs.ac.bg.etf.pp1.dto;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandar Grkajac aleksa888@gmail.com, ga040202d@student.etf.rs
 * Date: 7/18/14
 * Time: 5:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class Counting {

	private Integer glob_promenljivih;
	private Integer glob_nizova;

	private Integer funkcija;
	private Integer un_klasa;
	private Integer blokova_naredbi;
	private Integer poziva_funkcija;
	private Integer instanciranja;

	// Klase
	private Integer metoda;
	private Integer atributa;
	private Integer izvodjenja;

	public Counting() {

	}

	public Integer getGlob_promenljivih() {

		return glob_promenljivih;
	}

	public void setGlob_promenljivih(Integer glob_promenljivih) {

		this.glob_promenljivih = glob_promenljivih;
	}

	public Integer getGlob_nizova() {

		return glob_nizova;
	}

	public void setGlob_nizova(Integer glob_nizova) {

		this.glob_nizova = glob_nizova;
	}

	public Integer getFunkcija() {

		return funkcija;
	}

	public void setFunkcija(Integer funkcija) {

		this.funkcija = funkcija;
	}

	public Integer getUn_klasa() {

		return un_klasa;
	}

	public void setUn_klasa(Integer un_klasa) {

		this.un_klasa = un_klasa;
	}

	public Integer getBlokova_naredbi() {

		return blokova_naredbi;
	}

	public void setBlokova_naredbi(Integer blokova_naredbi) {

		this.blokova_naredbi = blokova_naredbi;
	}

	public Integer getPoziva_funkcija() {

		return poziva_funkcija;
	}

	public void setPoziva_funkcija(Integer poziva_funkcija) {

		this.poziva_funkcija = poziva_funkcija;
	}

	public Integer getInstanciranja() {

		return instanciranja;
	}

	public void setInstanciranja(Integer instanciranja) {

		this.instanciranja = instanciranja;
	}

	public Integer getMetoda() {

		return metoda;
	}

	public void setMetoda(Integer metoda) {

		this.metoda = metoda;
	}

	public Integer getAtributa() {

		return atributa;
	}

	public void setAtributa(Integer atributa) {

		this.atributa = atributa;
	}

	public Integer getIzvodjenja() {

		return izvodjenja;
	}

	public void setIzvodjenja(Integer izvodjenja) {

		this.izvodjenja = izvodjenja;
	}

	public boolean isEqual(Counting counting) {

		boolean result = false;

		if (glob_promenljivih.equals(counting.getGlob_promenljivih()) && glob_nizova.equals(counting.getGlob_nizova())
				&& funkcija.equals(counting.getFunkcija()) && un_klasa.equals(counting.getUn_klasa())
				&& blokova_naredbi.equals(counting.getBlokova_naredbi()) && poziva_funkcija.equals(counting.getPoziva_funkcija())
				&& instanciranja.equals(counting.getInstanciranja()) && metoda.equals(counting.getMetoda())
				&& atributa.equals(counting.getAtributa()) && izvodjenja.equals(counting.getIzvodjenja())) {
			result = true;
		}

		return result;
	}
}
