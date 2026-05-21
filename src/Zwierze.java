import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Zwierze extends Organizm {

    public Zwierze(int sila, int inicjatywa, int polozenieX, int polozenieY, Swiat swiat, char znak) {
        super(sila, inicjatywa, polozenieX, polozenieY, swiat, znak);
    }
    @Override
    public void Akcja(int klawisz) {
        if (this.wiek == 0) {
            return;
        }
        StarePolozenieX=PolozenieX;
        StarePolozenieY=PolozenieY;
        int noweX = PolozenieX;
        int noweY = PolozenieY;
        int ruch = (int)(Math.random() * 8);
        switch (ruch) {
            case 0:
                noweX+=1;
                break;
            case 1:
                noweX-=1;
                break;
            case 2:
                noweY+=1;
                break;
            case 3:
                noweY-=1;
                break;
            case 4:
                noweY-=1;
                noweX-=1;
                break;
            case 5:
                noweY-=1;
                noweX+=1;
                break;
            case 6:
                noweY+=1;
                noweX-=1;
                break;
            case 7:
                noweY+=1;
                noweX+=1;
                break;
        }
        if (noweX >= 0 && noweX < swiat.WezX() && noweY >=0 && noweY < swiat.WezY()) {
            PolozenieX=noweX;
            PolozenieY=noweY;
        }
    }

    public void RozmnozSie() {
        int[] mozliweX = {-1, 1, 0, 0, -1, -1, 1, 1};
        int[] mozliweY = {0, 0, -1, 1, -1, 1, -1, 1};

        List<Integer> bezpieczne = new ArrayList<>();

        for (int j = 0; j < 8; j++) {
            int potencjalneX = WezPolozenieX() + mozliweX[j];
            int potencjalneY = WezPolozenieY() + mozliweY[j];

            if (potencjalneX >= 0 && potencjalneX < swiat.WezX() && potencjalneY >= 0 && potencjalneY < swiat.WezY()) {
                boolean zajete = false;

                for (int i = 0; i < swiat.WezOrganizmy().size(); i++) {
                    Organizm org = swiat.WezOrganizmy().get(i);

                    if (org.CzyZyje() && org.WezPolozenieX() == potencjalneX && org.WezPolozenieY() == potencjalneY) {
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

            int noweX = WezPolozenieX() + mozliweX[bezpieczne.get(wybrany)];
            int noweY = WezPolozenieY() + mozliweY[bezpieczne.get(wybrany)];

            Organizm dziecko = this.Rozmnazaj(noweX, noweY);
            if (dziecko != null) {
                swiat.DodajOrganizm(dziecko);
                String tekst = "Narodzil sie nowy organizm: " + this.WezZnak();
                swiat.DodajKomunikat(tekst);
                dziecko.UstawWiek(0);
            }
        }
    }

    protected abstract Organizm Rozmnazaj(int x, int y);
}