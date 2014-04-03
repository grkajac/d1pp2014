package rs.ac.bg.etf.pp1;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandar Grkajac ga040202d@student.etf.rs , aleksa888@gmail.com
 * Date: 1/29/14
 * Time: 9:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestInfo {

    private String name;

    private int deklaracija_globalnih_promenljivih_prostog_tipa;
    private int deklaracija_globalnih_nizova;
    private int definicija_funkcija_u_glavnom_programu;
    private int definicija_unutrašnjih_klasa;
    private int blokova_naredbi;
    private int poziva_funkcija_u_telu_metode_main;
    private int naredbi_instanciranja_objekata;
    private int definicija_metoda_unutrašnjih_klasa;
    private int deklaracija_polja_unutrašnjih_klasa;
    private int izvodjenja_klasa;

    public TestInfo(String name,
                    int deklaracija_globalnih_promenljivih_prostog_tipa,
                    int deklaracija_globalnih_nizova,
                    int definicija_funkcija_u_glavnom_programu,
                    int definicija_unutrašnjih_klasa,
                    int blokova_naredbi,
                    int poziva_funkcija_u_telu_metode_main,
                    int naredbi_instanciranja_objekata,
                    int definicija_metoda_unutrašnjih_klasa,
                    int deklaracija_polja_unutrašnjih_klasa,
                    int izvodjenja_klasa) {
        this.name = name;
        this.deklaracija_globalnih_promenljivih_prostog_tipa = deklaracija_globalnih_promenljivih_prostog_tipa;
        this.deklaracija_globalnih_nizova = deklaracija_globalnih_nizova;
        this.definicija_funkcija_u_glavnom_programu = definicija_funkcija_u_glavnom_programu;
        this.definicija_unutrašnjih_klasa = definicija_unutrašnjih_klasa;
        this.blokova_naredbi = blokova_naredbi;
        this.poziva_funkcija_u_telu_metode_main = poziva_funkcija_u_telu_metode_main;
        this.naredbi_instanciranja_objekata = naredbi_instanciranja_objekata;
        this.definicija_metoda_unutrašnjih_klasa = definicija_metoda_unutrašnjih_klasa;
        this.deklaracija_polja_unutrašnjih_klasa = deklaracija_polja_unutrašnjih_klasa;
        this.izvodjenja_klasa = izvodjenja_klasa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeklaracija_globalnih_promenljivih_prostog_tipa() {
        return deklaracija_globalnih_promenljivih_prostog_tipa;
    }

    public void setDeklaracija_globalnih_promenljivih_prostog_tipa(int deklaracija_globalnih_promenljivih_prostog_tipa) {
        this.deklaracija_globalnih_promenljivih_prostog_tipa = deklaracija_globalnih_promenljivih_prostog_tipa;
    }

    public int getDeklaracija_globalnih_nizova() {
        return deklaracija_globalnih_nizova;
    }

    public void setDeklaracija_globalnih_nizova(int deklaracija_globalnih_nizova) {
        this.deklaracija_globalnih_nizova = deklaracija_globalnih_nizova;
    }

    public int getDefinicija_funkcija_u_glavnom_programu() {
        return definicija_funkcija_u_glavnom_programu;
    }

    public void setDefinicija_funkcija_u_glavnom_programu(int definicija_funkcija_u_glavnom_programu) {
        this.definicija_funkcija_u_glavnom_programu = definicija_funkcija_u_glavnom_programu;
    }

    public int getDefinicija_unutrašnjih_klasa() {
        return definicija_unutrašnjih_klasa;
    }

    public void setDefinicija_unutrašnjih_klasa(int definicija_unutrašnjih_klasa) {
        this.definicija_unutrašnjih_klasa = definicija_unutrašnjih_klasa;
    }

    public int getBlokova_naredbi() {
        return blokova_naredbi;
    }

    public void setBlokova_naredbi(int blokova_naredbi) {
        this.blokova_naredbi = blokova_naredbi;
    }

    public int getPoziva_funkcija_u_telu_metode_main() {
        return poziva_funkcija_u_telu_metode_main;
    }

    public void setPoziva_funkcija_u_telu_metode_main(int poziva_funkcija_u_telu_metode_main) {
        this.poziva_funkcija_u_telu_metode_main = poziva_funkcija_u_telu_metode_main;
    }

    public int getNaredbi_instanciranja_objekata() {
        return naredbi_instanciranja_objekata;
    }

    public void setNaredbi_instanciranja_objekata(int naredbi_instanciranja_objekata) {
        this.naredbi_instanciranja_objekata = naredbi_instanciranja_objekata;
    }

    public int getDefinicija_metoda_unutrašnjih_klasa() {
        return definicija_metoda_unutrašnjih_klasa;
    }

    public void setDefinicija_metoda_unutrašnjih_klasa(int definicija_metoda_unutrašnjih_klasa) {
        this.definicija_metoda_unutrašnjih_klasa = definicija_metoda_unutrašnjih_klasa;
    }

    public int getDeklaracija_polja_unutrašnjih_klasa() {
        return deklaracija_polja_unutrašnjih_klasa;
    }

    public void setDeklaracija_polja_unutrašnjih_klasa(int deklaracija_polja_unutrašnjih_klasa) {
        this.deklaracija_polja_unutrašnjih_klasa = deklaracija_polja_unutrašnjih_klasa;
    }

    public int getIzvodjenja_klasa() {
        return izvodjenja_klasa;
    }

    public void setIzvodjenja_klasa(int izvodjenja_klasa) {
        this.izvodjenja_klasa = izvodjenja_klasa;
    }
}
