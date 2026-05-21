import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Roslina extends Organizm {
    public Roslina(int sila, int inicjatywa, int polozenieX, int polozenieY, Swiat swiat, char znak) {
        super(sila, inicjatywa, polozenieX, polozenieY, swiat, znak);
    }

    @Override
    public void Kolizja() {
        //Tutaj nie implementujemy kolizji, żeby rośliny nie atakowały zwierząt, bo to jest niemożliwe
    }

    @Override
    public void Akcja(int klawisz) {

        Random random = new Random();
        int losuj = random.nextInt(20);

        if (losuj == 0) {
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
                int wybrany = random.nextInt(bezpieczne.size());

                int noweX = WezPolozenieX() + mozliweX[bezpieczne.get(wybrany)];
                int noweY = WezPolozenieY() + mozliweY[bezpieczne.get(wybrany)];

                Organizm nowaRoslina = this.Rozmnazaj(noweX,noweY);
                if (nowaRoslina != null) {
                    swiat.DodajOrganizm(nowaRoslina);
                    swiat.DodajKomunikat("Powstaje nowa roslina " + this.WezZnak());

                }
            }
        }
    }

    @Override
    protected abstract Organizm Rozmnazaj(int x, int y);
}
