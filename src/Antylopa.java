import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Antylopa extends Zwierze {

    private static final char ZNAK_ANTYLOPY = 'A';

    public Antylopa(int PolozenieX, int PolozenieY, Swiat swiat) {
        super(4, 4, PolozenieX, PolozenieY, swiat, ZNAK_ANTYLOPY);
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
                noweX+=2;
                break;
            case 1:
                noweX-=2;
                break;
            case 2:
                noweY+=2;
                break;
            case 3:
                noweY-=2;
                break;
            case 4:
                noweY-=2;
                noweX-=2;
                break;
            case 5:
                noweY-=2;
                noweX+=2;
                break;
            case 6:
                noweY+=2;
                noweX-=2;
                break;
            case 7:
                noweY+=2;
                noweX+=2;
                break;
        }
        if (noweX >= 0 && noweX < swiat.WezX() && noweY >=0 && noweY < swiat.WezY()) {
            PolozenieX=noweX;
            PolozenieY=noweY;
        }
    }

    @Override
    public boolean UniknijSmierci(Organizm napastnik) {
        int czy_ucieknie = (int) (Math.random() * 2);
        if (czy_ucieknie == 0) {
            int mozliweX[] = {-1, 1, 0, 0, -1, -1, 1, 1};
            int mozliweY[] = {0, 0, -1, 1, -1, 1, -1, 1};
            List<Integer> bezpieczne = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                int potencjalneX = PolozenieX + mozliweX[i];
                int potencjalneY = PolozenieY + mozliweY[i];

                if (potencjalneX >= 0 && potencjalneX < swiat.WezX() && potencjalneY >= 0 && potencjalneY < swiat.WezY()) {
                    if (SprawdzajSasiadow(potencjalneX, potencjalneY)) {
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
                String tekst = "Antylopa ucieka przed ";
                tekst += napastnik.WezZnak();
                swiat.DodajKomunikat(tekst);
                return true;
            }
        }
        return false;
    }

    @Override
    protected Organizm Rozmnazaj(int x, int y){
        return new Antylopa(x,y,swiat);
    }
}
