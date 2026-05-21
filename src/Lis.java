import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lis extends Zwierze{

    private static final char ZNAK_LISA = 'L';

    public Lis(int polozenieX, int polozenieY, Swiat swiat) {
        super(3, 7, polozenieX, polozenieY, swiat, ZNAK_LISA);
    }

    @Override
    protected Organizm Rozmnazaj(int x, int y) {
        return new Lis(x, y, swiat);
    }

    @Override
    public boolean CzyDrapieznik() {
        return true;
    }
    @Override
    public void Akcja(int klawisz) {
        if (this.wiek == 0) {
            return;
        }
        StarePolozenieX = PolozenieX;
        StarePolozenieY = PolozenieY;
        int mozliweX[] = {-1, 1, 0, 0, -1, -1, 1, 1};
        int mozliweY[] = {0, 0, -1, 1, -1, 1, -1, 1};
        List<Integer> bezpieczne = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int potencjalneX = PolozenieX + mozliweX[i];
            int potencjalneY = PolozenieY + mozliweY[i];

            if (potencjalneX >= 0 && potencjalneX < swiat.WezX() && potencjalneY >= 0 && potencjalneY < swiat.WezY()) {
                if (DobryWech(potencjalneX, potencjalneY)) {
                    bezpieczne.add(i);
                }
            }
        }


        if (!bezpieczne.isEmpty()) {
            Random random = new Random();
            int wybrany = random.nextInt(bezpieczne.size());

            int noweX = PolozenieX + mozliweX[bezpieczne.get(wybrany)];
            int noweY = PolozenieY + mozliweY[bezpieczne.get(wybrany)];

            StarePolozenieX = PolozenieX;
            StarePolozenieY = PolozenieY;
            PolozenieX = noweX;
            PolozenieY = noweY;
        }
    }

    private boolean DobryWech ( int x, int y) {
        for (int i = 0; i < swiat.WezOrganizmy().size(); i++) {
            if (x == swiat.WezOrganizmy().get(i).WezPolozenieX() && y == swiat.WezOrganizmy().get(i).WezPolozenieY()) {
                if (swiat.WezOrganizmy().get(i).CzyZyje() && sila < swiat.WezOrganizmy().get(i).WezSila()) {
                    return false;
                }
            }
        }
        return true;
    }
}
