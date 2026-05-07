public class Zolw extends Zwierze {

    private static final char ZNAK_ZOLWIA = 'Z';

    public Zolw(int PolozenieX, int PolozenieY, Swiat swiat) {
        super(2, 1, PolozenieX, PolozenieY, swiat, ZNAK_ZOLWIA);
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
        int czy_rusza = (int)(Math.random() * 4);
        if (czy_rusza == 0) {
            int ruch = (int) (Math.random() * 8);
            switch (ruch) {
                case 0:
                    noweX += 1;
                    break;
                case 1:
                    noweX -= 1;
                    break;
                case 2:
                    noweY += 1;
                    break;
                case 3:
                    noweY -= 1;
                    break;
                case 4:
                    noweY -= 1;
                    noweX -= 1;
                    break;
                case 5:
                    noweY -= 1;
                    noweX += 1;
                    break;
                case 6:
                    noweY += 1;
                    noweX -= 1;
                    break;
                case 7:
                    noweY += 1;
                    noweX += 1;
                    break;
            }
            if (noweX >= 0 && noweX < swiat.GetX() && noweY >= 0 && noweY < swiat.GetY()) {
                PolozenieX = noweX;
                PolozenieY = noweY;
            }
        }
    }

    @Override
    public boolean CzyOdpycha(Organizm napastnik){
        if (napastnik.GetSila() < 5){
            napastnik.Cofnij();
            return true;
        }
        return false;
    }

    @Override
    protected Organizm Rozmnazaj(int x, int y){
        return new Zolw(x,y,swiat);
    }
}
