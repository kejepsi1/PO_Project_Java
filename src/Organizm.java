import java.util.Random;


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
    public int GetWiek(){
        return this.wiek;
    }
    public int GetSila(){
        return this.sila;
    }
    public int GetInicjatywa(){
        return this.inicjatywa;
    }
    public int GetPolozenieX(){
        return this.PolozenieX;
    }
    public int GetPolozenieY(){
        return this.PolozenieY;
    }
    public char GetZnak(){
        return this.znak;
    }
    public void SetPolozenieX(int x){
        this.PolozenieX = x;
    }
    public void SetPolozenieY(int y){
        this.PolozenieY = y;
    }
    public void SetWiek(int wiek){
        this.wiek = wiek;
    }
    public void SetSila(int sila){
        this.sila = sila;
    }
    public boolean CzyOdpycha(Organizm napastnik){
        return false;
    }
    public boolean SprawdzajSasiadow(int x,int y){
        for (int i = 0; i < swiat.GetOrganizmy().size();i++){
            if (x == swiat.GetOrganizmy().get(i).GetPolozenieX() && y == swiat.GetOrganizmy().get(i).GetPolozenieY()){
                return false;
            }
        }
        return true;
    }
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
                noweX+=1;
                break;
            case 1:
                noweX-=1;
                break;
            case 2:
                noweY+=1;
                break;
            case 3:
                noweY-=1;
                break;
            case 4:
                noweY-=1;
                noweX-=1;
                break;
            case 5:
                noweY-=1;
                noweX+=1;
                break;
            case 6:
                noweY+=1;
                noweX-=1;
                break;
            case 7:
                noweY+=1;
                noweX+=1;
                break;
        }
        if (noweX >= 0 && noweX < swiat.GetX() && noweY >=0 && noweY < swiat.GetY()) {
            PolozenieX=noweX;
            PolozenieY=noweY;
        }
    }

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
        if (napastnik.GetSila() >= this.GetSila()) {
            return false;
        }
        return true;
    }

    public String DoZapisu() {
        return String.valueOf(znak) + " " + PolozenieX + " " + PolozenieY + " " + sila + " " + wiek;
    }

}