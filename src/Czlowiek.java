import java.awt.event.KeyEvent;

public class Czlowiek extends Zwierze {

    private static final char ZNAK_CZLOWIEKA = 'C';

    Czlowiek(int PolozenieX, int PolozenieY, Swiat swiat){
        super(5,4,PolozenieX,PolozenieY,swiat, ZNAK_CZLOWIEKA);
    }

    @Override
    public void Akcja(int klawisz) {
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

        if (noweX >= 0 && noweX < swiat.GetX() && noweY >= 0 && noweY < swiat.GetY()) {
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
}