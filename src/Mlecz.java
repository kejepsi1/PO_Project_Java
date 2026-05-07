import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mlecz extends Roslina {
    private static final char ZNAK_MLECZA = 'M';

    public Mlecz(int polozenieX, int polozenieY, Swiat swiat) {
        super(0, 0, polozenieX, polozenieY, swiat, ZNAK_MLECZA);
    }

    @Override
    public void Akcja(int klawisz) {
        for (int k = 0;k < 3;k++) {
            Random random = new Random();
            int losuj = random.nextInt(20);

            if (losuj == 0) {
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
                    int wybrany = random.nextInt(bezpieczne.size());

                    int noweX = GetPolozenieX() + mozliweX[bezpieczne.get(wybrany)];
                    int noweY = GetPolozenieY() + mozliweY[bezpieczne.get(wybrany)];

                    swiat.DodajKomunikat("Powstaje nowy Mlecz");
                    swiat.DodajOrganizm(new Mlecz(noweX, noweY, swiat));
                }
            }
        }
    }

    public boolean CzyMoznaZdeptac(Organizm napastnik) {
        if (napastnik.CzyDrapieznik()) {
            return true;
        }
        return false;
    }
}
