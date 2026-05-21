import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Swiat {
    private int x, y;

    private List<String> komunikaty;
    private List<Organizm> organizmy;

    public Swiat(int x, int y) {
        this.x = x;
        this.y = y;
        this.komunikaty = new ArrayList<>();
        this.organizmy = new ArrayList<>();
        this.DodajBezpiecznieOrganizm(new Czlowiek(0,0,this));
        for (int i = 0;i < 5;i++){
            this.DodajBezpiecznieOrganizm(new Wilk(0,0,this));
            this.DodajBezpiecznieOrganizm(new Trawa(0,0,this));
            this.DodajBezpiecznieOrganizm(new Owca(0,0,this));
            this.DodajBezpiecznieOrganizm(new Mlecz(0,0,this));
            this.DodajBezpiecznieOrganizm(new Lis(0,0,this));
            this.DodajBezpiecznieOrganizm(new Zolw(0,0,this));
            this.DodajBezpiecznieOrganizm(new Antylopa(0,0,this));
            this.DodajBezpiecznieOrganizm(new Guarana(0,0,this));
            this.DodajBezpiecznieOrganizm(new WilczeJagody(0,0,this));
            this.DodajBezpiecznieOrganizm(new BarszczSosnowskiego(0,0,this));
        }
    }

    private void DodajWiek() {
        for (int i = 0; i < organizmy.size(); i++) {
            organizmy.get(i).UstawWiek(organizmy.get(i).WezWiek() + 1);
        }
    }

    public int WezX() { return this.x; }
    public int WezY() { return this.y; }
    public List<String> WezKomunikaty(){
        return this.komunikaty;
    }

    public List<Organizm> WezOrganizmy() {
        return this.organizmy;
    }

    public void DodajKomunikat(String komunikat) {
        komunikaty.add(komunikat);
    }

    public void SprawdzajKolizje(Organizm napastnik) {
        for (int i = 0; i < organizmy.size(); i++) {
            Organizm obronca = organizmy.get(i);
            if (obronca == napastnik || !obronca.CzyZyje() || !napastnik.CzyZyje()) {
                continue;
            }

            if (obronca.WezPolozenieX() == napastnik.WezPolozenieX() && obronca.WezPolozenieY() == napastnik.WezPolozenieY()) {
                if (obronca.CzyMoznaZdeptac(napastnik)) {
                    continue;
                }
                if (obronca.WezZnak() == napastnik.WezZnak()) {
                    napastnik.Cofnij();
                    obronca.RozmnozSie();
                    return;
                }

                if (!organizmy.get(i).CzyOdpycha(napastnik)) {
                    if (!organizmy.get(i).CzyObronil(napastnik)) {
                        if (obronca.UniknijSmierci(napastnik)) {
                            return;
                        }

                        String tekst = napastnik.WezZnak() + " zjada " + obronca.WezZnak();
                        DodajKomunikat(tekst);

                        obronca.Zabij();
                        return;
                    } else {
                        if (napastnik.UniknijSmierci(obronca)) {
                            return;
                        }

                        String tekst = obronca.WezZnak() + " zjada " + napastnik.WezZnak();
                        DodajKomunikat(tekst);
                        napastnik.Zabij();
                        return;
                    }
                } else {
                    return;
                }
            }
        }
    }

    public void DodajBezpiecznieOrganizm(Organizm organizm) {
        if (organizmy.size() >= x * y) {

            return;
        }

        boolean zajete;
        int randX, randY;
        Random random = new Random();

        do {
            zajete = false;
            randX = random.nextInt(x);
            randY = random.nextInt(y);

            for (int i = 0; i < organizmy.size(); i++) {
                Organizm org = organizmy.get(i);
                if (org.WezPolozenieX() == randX && org.WezPolozenieY() == randY) {
                    zajete = true;
                    break;
                }
            }
        } while (zajete);

        organizm.UstawPolozenieX(randX);
        organizm.UstawPolozenieY(randY);

        this.DodajOrganizm(organizm);
    }

    public void DodajOrganizm(Organizm organizm) {
        if (organizm != null)
            this.organizmy.add(organizm);
    }

    public void WykonajTure(int wcisnietyKlawisz){
        organizmy.sort((a, b) -> {
            if (a.WezInicjatywa() == b.WezInicjatywa()) {
                return Integer.compare(b.WezWiek(), a.WezWiek());
            }
            return Integer.compare(b.WezInicjatywa(), a.WezInicjatywa());
        });

        komunikaty.clear();
        for (int i = 0; i < organizmy.size(); i++) {
            Organizm org = organizmy.get(i);
            if (org.CzyZyje()) {
                org.Akcja(wcisnietyKlawisz);
                org.Kolizja();
            }
        }

        organizmy.removeIf(org -> !org.CzyZyje());
        DodajWiek();
    }

    public void ZapiszDoPliku() {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"wymiarX\": ").append(this.x).append(",\n");
        json.append("  \"wymiarY\": ").append(this.y).append(",\n");
        json.append("  \"listaOrganizmow\": [\n");

        for (int i = 0; i < organizmy.size(); i++) {
            Organizm org = organizmy.get(i);
            json.append("    {\n");
            json.append("      \"znak\": \"").append(org.WezZnak()).append("\",\n");
            json.append("      \"x\": ").append(org.WezPolozenieX()).append(",\n");
            json.append("      \"y\": ").append(org.WezPolozenieY()).append(",\n");
            json.append("      \"sila\": ").append(org.WezSila()).append(",\n");
            json.append("      \"wiek\": ").append(org.WezWiek());

            if (org instanceof Czlowiek) {
                Czlowiek c = (Czlowiek) org;
                json.append(",\n      \"trwanieUmiejetnosci\": ").append(c.WezCzasTrwania()).append(",\n");
                json.append("      \"odnowienieUmiejetnosci\": ").append(c.WezCzasOdnowienia()).append("\n");
            } else {
                json.append("\n");
            }

            json.append("    }");
            if (i < organizmy.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }

        json.append("  ]\n}");

        try (FileWriter writer = new FileWriter("zapis_gry.json")) {
            writer.write(json.toString());
            DodajKomunikat("Gra zostala zapisana do pliku.");
        } catch (IOException e) {
            System.out.println("Błąd zapisu: " + e.getMessage());
        }
    }

    public void WczytajZPliku() {
        try {
            String tresc = new String(Files.readAllBytes(Paths.get("zapis_gry.json")));

            this.x = Integer.parseInt(wyciagnijWartosc(tresc, "\"wymiarX\":"));
            this.y = Integer.parseInt(wyciagnijWartosc(tresc, "\"wymiarY\":"));

            this.organizmy.clear();
            this.komunikaty.clear();

            int startTablicy = tresc.indexOf("[");
            int koniecTablicy = tresc.lastIndexOf("]");

            if (startTablicy != -1 && koniecTablicy != -1) {
                String zawartoscTablicy = tresc.substring(startTablicy + 1, koniecTablicy).trim();

                if (zawartoscTablicy.startsWith("{")) zawartoscTablicy = zawartoscTablicy.substring(1);
                if (zawartoscTablicy.endsWith("}")) zawartoscTablicy = zawartoscTablicy.substring(0, zawartoscTablicy.length() - 1);

                String[] blokiOrganizmow = zawartoscTablicy.split("\\}\\s*,\\s*\\{");

                for (String blok : blokiOrganizmow) {
                    if (blok.trim().isEmpty()) continue;

                    char znak = wyciagnijWartoscString(blok, "\"znak\":").charAt(0);
                    int ox = Integer.parseInt(wyciagnijWartosc(blok, "\"x\":"));
                    int oy = Integer.parseInt(wyciagnijWartosc(blok, "\"y\":"));
                    int sila = Integer.parseInt(wyciagnijWartosc(blok, "\"sila\":"));
                    int wiek = Integer.parseInt(wyciagnijWartosc(blok, "\"wiek\":"));

                    Organizm nowy = null;
                    switch (znak) {
                        case 'W': nowy = new Wilk(ox, oy, this); break;
                        case 'O': nowy = new Owca(ox, oy, this); break;
                        case 'L': nowy = new Lis(ox, oy, this); break;
                        case 'Z': nowy = new Zolw(ox, oy, this); break;
                        case 'A': nowy = new Antylopa(ox, oy, this); break;
                        case 'C':
                            nowy = new Czlowiek(ox, oy, this);
                            int trwanie = Integer.parseInt(wyciagnijWartosc(blok, "\"trwanieUmiejetnosci\":"));
                            int odnowienie = Integer.parseInt(wyciagnijWartosc(blok, "\"odnowienieUmiejetnosci\":"));
                            ((Czlowiek) nowy).UstawCzasTrwania(trwanie);
                            ((Czlowiek) nowy).UstawCzasOdnowienia(odnowienie);
                            break;
                        case 'T': nowy = new Trawa(ox, oy, this); break;
                        case 'M': nowy = new Mlecz(ox, oy, this); break;
                        case 'G': nowy = new Guarana(ox, oy, this); break;
                        case 'J': nowy = new WilczeJagody(ox, oy, this); break;
                        case 'B': nowy = new BarszczSosnowskiego(ox, oy, this); break;
                    }

                    if (nowy != null) {
                        nowy.UstawSila(sila);
                        nowy.UstawWiek(wiek);
                        this.organizmy.add(nowy);
                    }
                }
            }
            DodajKomunikat("Wczytano zapisana gre!");

        } catch (Exception e) {
            System.out.println("Blad podczas wczytywania: " + e.getMessage());
        }
    }

    private String wyciagnijWartosc(String zrodlo, String klucz) {
        int startKlucza = zrodlo.indexOf(klucz);
        if (startKlucza == -1) return "0";

        int startWartosci = startKlucza + klucz.length();
        int koniecWartosciPrzecinek = zrodlo.indexOf(",", startWartosci);
        int koniecWartosciKlamra = zrodlo.indexOf("}", startWartosci);
        int koniecWartosciEnter = zrodlo.indexOf("\n", startWartosci);

        int koniec = koniecWartosciPrzecinek;
        if (koniec == -1 || (koniecWartosciKlamra != -1 && koniecWartosciKlamra < koniec)) koniec = koniecWartosciKlamra;
        if (koniec == -1 || (koniecWartosciEnter != -1 && koniecWartosciEnter < koniec)) koniec = koniecWartosciEnter;

        if (koniec == -1) koniec = zrodlo.length();

        return zrodlo.substring(startWartosci, koniec).trim();
    }

    private String wyciagnijWartoscString(String zrodlo, String klucz) {
        String surowaWartosc = wyciagnijWartosc(zrodlo, klucz);
        return surowaWartosc.replace("\"", "");
    }
}