public class Trawa extends Roslina{
    private static final char ZNAK_TRAWY = 'T';

    public Trawa(int polozenieX, int polozenieY, Swiat swiat) {
        super(0, 0, polozenieX, polozenieY, swiat, ZNAK_TRAWY);
    }



    @Override
    public boolean CzyMoznaZdeptac(Organizm napastnik) {
        if (napastnik.CzyDrapieznik()) {
            return true;
        }
        return false;
    }

    @Override
    protected Organizm Rozmnazaj(int x, int y){
        return new Trawa(x,y,swiat);
    }
}


