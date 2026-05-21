public class Mlecz extends Roslina {
    private static final char ZNAK_MLECZA = 'M';

    public Mlecz(int polozenieX, int polozenieY, Swiat swiat) {
        super(0, 0, polozenieX, polozenieY, swiat, ZNAK_MLECZA);
    }

    @Override
    public void Akcja(int klawisz) {
        for (int i = 0; i < 3; i++) {
            super.Akcja(klawisz);
        }
    }

    public boolean CzyMoznaZdeptac(Organizm napastnik) {
        if (napastnik.CzyDrapieznik()) {
            return true;
        }
        return false;
    }

    @Override
    protected Organizm Rozmnazaj(int x, int y){
        return new Mlecz(x,y,swiat);
    }
}
