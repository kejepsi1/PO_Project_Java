import java.awt.event.KeyEvent;

public class Czlowiek extends Zwierze {

    private int czas_trwania_umiejetnosci = 0;
    private int czas_odnowienia_umiejetnosci = 0;
    private static final char ZNAK_CZLOWIEKA = 'C';

    Czlowiek(int PolozenieX, int PolozenieY, Swiat swiat){
        super(5,4,PolozenieX,PolozenieY,swiat, ZNAK_CZLOWIEKA);
    }

    @Override
    public void Akcja(int klawisz) {

        if (klawisz == KeyEvent.VK_SPACE && czas_odnowienia_umiejetnosci == 0){
            czas_odnowienia_umiejetnosci = 10;
            czas_trwania_umiejetnosci = 5;
            swiat.DodajKomunikat("Czlowiek uzywa umiejetnosci niesmiertelnosc");
        }
        else if (czas_trwania_umiejetnosci != 0){
            czas_trwania_umiejetnosci--;
            czas_odnowienia_umiejetnosci--;
        }
        else if (czas_odnowienia_umiejetnosci != 0){
            czas_odnowienia_umiejetnosci--;
        }
        StarePolozenieX = PolozenieX;
        StarePolozenieY = PolozenieY;

        int noweX = PolozenieX;
        int noweY = PolozenieY;

        if (klawisz == KeyEvent.VK_UP) {
            noweY -= 1;
        } else if (klawisz == KeyEvent.VK_DOWN) {
            noweY += 1;
        } else if (klawisz == KeyEvent.VK_LEFT) {
            noweX -= 1;
        } else if (klawisz == KeyEvent.VK_RIGHT) {
            noweX += 1;
        }
        else if (klawisz == KeyEvent.VK_W) {
            noweY -= 1;
            noweX -= 1;
        }
        else if (klawisz == KeyEvent.VK_R) {
            noweY -= 1;
            noweX += 1;

        }
        else if (klawisz == KeyEvent.VK_X) {
            noweY += 1;
            noweX -= 1;
        }
        else if (klawisz == KeyEvent.VK_V) {
            noweY += 1;
            noweX += 1;
        }

        if (noweX >= 0 && noweX < swiat.WezX() && noweY >= 0 && noweY < swiat.WezY()) {
            PolozenieX = noweX;
            PolozenieY = noweY;
        }
    }

    @Override
    protected Organizm Rozmnazaj(int x, int y) {
        return null;
    }

    @Override
    public boolean CzyDrapieznik() {
        return true;
    }

    @Override
    public boolean UniknijSmierci(Organizm napastnik){
        if (czas_trwania_umiejetnosci > 0) {

            int[] mozliweX = {-1, 1, 0, 0, -1, -1, 1, 1};
            int[] mozliweY = {0, 0, -1, 1, -1, 1, -1, 1};
            java.util.List<Integer> bezpieczne = new java.util.ArrayList<>();

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
                java.util.Random random = new java.util.Random();
                int wybrany = random.nextInt(bezpieczne.size());

                int noweX = PolozenieX + mozliweX[bezpieczne.get(wybrany)];
                int noweY = PolozenieY + mozliweY[bezpieczne.get(wybrany)];

                StarePolozenieX = PolozenieX;
                StarePolozenieY = PolozenieY;
                PolozenieX = noweX;
                PolozenieY = noweY;

                swiat.DodajKomunikat("Umiejetnosc czlowieka ratuje go przed " + napastnik.WezZnak());
                return true;
            }
        }
        return false;
    }

    public int WezCzasTrwania() { return czas_trwania_umiejetnosci; }
    public int WezCzasOdnowienia() { return czas_odnowienia_umiejetnosci; }
    public void UstawCzasTrwania(int czas) { czas_trwania_umiejetnosci = czas; }
    public void UstawCzasOdnowienia(int czas) { czas_odnowienia_umiejetnosci = czas; }
}