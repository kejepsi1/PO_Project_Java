import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Swiat {
    private int x, y;

    private List<String> komunikaty;
    private List<Organizm> organizmy;

    private boolean czyWczytac = false;

    public Swiat(int x, int y) {
        this.x = x;
        this.y = y;
        this.komunikaty = new ArrayList<>();
        this.organizmy = new ArrayList<>();

        this.DodajBezpiecznieOrganizm(new Czlowiek(0,0,this));
        for (int i = 0;i < 5;i++){
            this.DodajBezpiecznieOrganizm(new Wilk(0,0,this));
            this.DodajBezpiecznieOrganizm(new Trawa(0,0,this));
        }
    }

    private void DodajWiek() {
        for (int i = 0; i < organizmy.size(); i++) {
            organizmy.get(i).SetWiek(organizmy.get(i).GetWiek() + 1);
        }
    }

    public int GetX() { return this.x; }
    public int GetY() { return this.y; }
    public List<String> GetKomunikaty(){
        return this.komunikaty;
    }

    public List<Organizm> GetOrganizmy() {
        return this.organizmy;
    }

    public void DodajKomunikat(String komunikat) {
        komunikaty.add(komunikat); // Odpowiednik push_back()
    }

    public void SprawdzajKolizje(Organizm napastnik) {
        for (int i = 0; i < organizmy.size(); i++) {
            Organizm obronca = organizmy.get(i);
            if (obronca == napastnik || !obronca.CzyZyje() || !napastnik.CzyZyje()) {
                continue;
            }

            if (obronca.GetPolozenieX() == napastnik.GetPolozenieX() && obronca.GetPolozenieY() == napastnik.GetPolozenieY()) {
                if (obronca.CzyMoznaZdeptac(napastnik)) {
                    continue;
                }
                if (obronca.GetZnak() == napastnik.GetZnak()) {
                    napastnik.Cofnij();
                    obronca.RozmnozSie();
                    return;
                }

                if (!organizmy.get(i).CzyOdpycha(napastnik)) {
                    if (!organizmy.get(i).CzyObronil(napastnik)) {
                        if (obronca.UniknijSmierci(napastnik)) {
                            return;
                        }

                        String tekst = napastnik.GetZnak() + " zjada " + obronca.GetZnak();
                        DodajKomunikat(tekst);

                        obronca.Zabij();
                        return;
                    } else {
                        if (napastnik.UniknijSmierci(obronca)) {
                            return;
                        }

                        String tekst = obronca.GetZnak() + " zjada " + napastnik.GetZnak();
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
                if (org.GetPolozenieX() == randX && org.GetPolozenieY() == randY) {
                    zajete = true;
                    break;
                }
            }
        } while (zajete);

        organizm.SetPolozenieX(randX);
        organizm.SetPolozenieY(randY);

        this.DodajOrganizm(organizm);
    }

    public void DodajOrganizm(Organizm organizm) {
        this.organizmy.add(organizm);
    }

    public void WykonajTure(int wcisnietyKlawisz){
        organizmy.sort((a, b) -> {
            if (a.GetInicjatywa() == b.GetInicjatywa()) {
                // Zwracamy porównanie wieku.
                // Zauważ kolejność (b, a) - dzięki temu sortuje MALEJĄCO (starsze na początku)
                return Integer.compare(b.GetWiek(), a.GetWiek());
            }
            // Zauważ kolejność (b, a) - sortuje MALEJĄCO po inicjatywie
            return Integer.compare(b.GetInicjatywa(), a.GetInicjatywa());
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
}