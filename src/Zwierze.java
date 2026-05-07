import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Zgodnie z instrukcją, Zwierze powinno być klasą abstrakcyjną
public abstract class Zwierze extends Organizm {

    public Zwierze(int sila, int inicjatywa, int polozenieX, int polozenieY, Swiat swiat, char znak) {
        // Wywołanie konstruktora klasy bazowej (Organizm)
        super(sila, inicjatywa, polozenieX, polozenieY, swiat, znak);
    }

    public void RozmnozSie() {
        int[] mozliweX = {-1, 1, 0, 0, -1, -1, 1, 1};
        int[] mozliweY = {0, 0, -1, 1, -1, 1, -1, 1};

        List<Integer> bezpieczne = new ArrayList<>();

        for (int j = 0; j < 8; j++) {
            int potencjalneX = GetPolozenieX() + mozliweX[j];
            int potencjalneY = GetPolozenieY() + mozliweY[j];

            if (potencjalneX >= 0 && potencjalneX < swiat.GetX() && potencjalneY >= 0 && potencjalneY < swiat.GetY()) {
                boolean zajete = false;

                for (int i = 0; i < swiat.GetOrganizmy().size(); i++) {
                    Organizm org = swiat.GetOrganizmy().get(i);

                    if (org.CzyZyje() && org.GetPolozenieX() == potencjalneX && org.GetPolozenieY() == potencjalneY) {
                        zajete = true;
                        break;
                    }
                }

                if (!zajete) {
                    bezpieczne.add(j);
                }
            }
        }

        if (!bezpieczne.isEmpty()) {
            Random random = new Random();
            int wybrany = random.nextInt(bezpieczne.size());

            int noweX = GetPolozenieX() + mozliweX[bezpieczne.get(wybrany)];
            int noweY = GetPolozenieY() + mozliweY[bezpieczne.get(wybrany)];

            Organizm dziecko = this.Rozmnazaj(noweX, noweY);
            swiat.DodajOrganizm(dziecko);

            String tekst = "Narodzil sie nowy organizm: " + this.GetZnak();
            swiat.DodajKomunikat(tekst);
            dziecko.SetWiek(0);
        }
    }

    // Pamiętaj, żeby zadeklarować metodę rozmnazaj jako abstrakcyjną w tej klasie!
    // Wymusza to na klasach pochodnych (Wilk, Owca) jej poprawną implementację.
    protected abstract Organizm Rozmnazaj(int x, int y);
}