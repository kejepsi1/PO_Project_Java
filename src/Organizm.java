public abstract class Organizm {
    Organizm(int sila, int inicjatywa, int PolozenieX, int PolozenieY,Swiat swiat, char znak){
        this.sila=sila;
        this.inicjatywa=inicjatywa;
        this.PolozenieX=PolozenieX;
        this.PolozenieY=PolozenieY;
        this.StarePolozenieX = PolozenieX;
        this.StarePolozenieY = PolozenieY;
        this.swiat=swiat;
        this.znak=znak;
        this.wiek=0;
    }
    protected int sila, inicjatywa, PolozenieX, PolozenieY, StarePolozenieX, StarePolozenieY, wiek;
    protected char znak;
    protected Swiat swiat;
    protected boolean czyZyje = true;
    public int WezWiek(){
        return this.wiek;
    }
    public int WezSila(){
        return this.sila;
    }
    public int WezInicjatywa(){
        return this.inicjatywa;
    }
    public int WezPolozenieX(){
        return this.PolozenieX;
    }
    public int WezPolozenieY(){
        return this.PolozenieY;
    }
    public char WezZnak(){
        return this.znak;
    }
    public void UstawPolozenieX(int x){
        this.PolozenieX = x;
    }
    public void UstawPolozenieY(int y){
        this.PolozenieY = y;
    }
    public void UstawWiek(int wiek){
        this.wiek = wiek;
    }
    public void UstawSila(int sila){
        this.sila = sila;
    }
    public boolean CzyOdpycha(Organizm napastnik){
        return false;
    }
    public boolean SprawdzajSasiadow(int x,int y){
        for (int i = 0; i < swiat.WezOrganizmy().size();i++){
            if (swiat.WezOrganizmy().get(i).CzyZyje() && x == swiat.WezOrganizmy().get(i).WezPolozenieX() && y == swiat.WezOrganizmy().get(i).WezPolozenieY()){
                return false;
            }
        }
        return true;
    }

    public abstract void Akcja(int klawisz);

    public void Kolizja() {
        swiat.SprawdzajKolizje(this);
    }

    public void Cofnij() {
        PolozenieX=StarePolozenieX;
        PolozenieY=StarePolozenieY;
    }

    protected Organizm Rozmnazaj(int x, int y) {
        return null;
    }

    public void RozmnozSie() {
    }

    public boolean CzyZyje() {
        return czyZyje;
    }

    public void Zabij() {
        czyZyje = false;
    }

    public boolean CzyMoznaZdeptac(Organizm napastnik) {
        return false;
    }

    public boolean CzyDrapieznik() {
        return false;
    }

    public boolean UniknijSmierci(Organizm napastnik) {
        return false;
    }

    public boolean CzyObronil(Organizm napastnik) {
        if (napastnik.WezSila() >= this.WezSila()) {
            return false;
        }
        return true;
    }

    public String DoZapisu() {
        return String.valueOf(znak) + " " + PolozenieX + " " + PolozenieY + " " + sila + " " + wiek;
    }

}