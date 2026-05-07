public class Zolw extends Zwierze {

    private static final char ZNAK_ZOLWIA = 'Z';

    public Zolw(int PolozenieX, int PolozenieY, Swiat swiat) {
        super(2, 1, PolozenieX, PolozenieY, swiat, ZNAK_ZOLWIA);
    }

    @Override
    public void Akcja(int klawisz) {
        int czy_rusza = (int)(Math.random() * 4);
        if (czy_rusza == 0) {
            super.Akcja(klawisz);
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
