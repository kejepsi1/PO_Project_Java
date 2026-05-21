public class Guarana extends Roslina{
    private static final char ZNAK_GUARANY = 'G';

    public Guarana( int PolozenieX, int PolozenieY, Swiat swiat){
        super(0,0,PolozenieX,PolozenieY,swiat,ZNAK_GUARANY);
    }

    @Override
    public boolean CzyObronil(Organizm napastnik){
        napastnik.UstawSila(napastnik.WezSila() + 3);
        return false;
    }

    @Override
    protected Organizm Rozmnazaj(int x, int y){
        return new Guarana(x,y,swiat);
    }
}
