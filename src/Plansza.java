import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Plansza extends JPanel {
    private int szerokosc;
    private int wysokosc;
    private JButton[][] pola;

    private Swiat swiat;

    public Plansza(Swiat swiat, int wysokosc, int szerokosc) {
        this.swiat = swiat;
        this.wysokosc = wysokosc;
        this.szerokosc = szerokosc;

        this.pola = new JButton[wysokosc][szerokosc];

        this.setLayout(new GridLayout(wysokosc, szerokosc, 0, 0));

        zainicjalizujPola();
    }

    private void zainicjalizujPola() {
        for (int y = 0; y < wysokosc; y++) {
            for (int x = 0; x < szerokosc; x++) {
                JButton przycisk = new JButton();

                przycisk.setBackground(Color.WHITE);
                przycisk.setOpaque(true);
                przycisk.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                final int finalX = x;
                final int finalY = y;

                przycisk.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //Tutaj dodac wybor organizmu
                        System.out.println("Kliknięto pole: " + finalX + ", " + finalY);
                    }
                });

                pola[y][x] = przycisk;
                this.add(przycisk);
            }
        }
    }

    public void odswiezPlansze() {
        // 1. "Czyszczenie" ekranu - odpowiednik Twojego clear() i rysowania '#'
        for (int y = 0; y < wysokosc; y++) {
            for (int x = 0; x < szerokosc; x++) {
                pola[y][x].setBackground(Color.WHITE); // Przywracamy białe tło
                pola[y][x].setText("");                // Usuwamy znak
            }
        }

        List<Organizm> organizmy = swiat.GetOrganizmy();

        for (int i = organizmy.size() - 1; i >= 0; i--) {
            Organizm org = organizmy.get(i);
            if (org.CzyZyje()) {
                int orgX = org.GetPolozenieX();
                int orgY = org.GetPolozenieY();

                if (org instanceof Wilk) {
                    pola[orgY][orgX].setBackground(Color.RED);
                    pola[orgY][orgX].setText("W");
                }

                if (org instanceof Trawa){
                    pola[orgY][orgX].setBackground(Color.GREEN);
                    pola[orgY][orgX].setText("T");
                }

                if (org instanceof Czlowiek){
                    pola[orgY][orgX].setBackground(Color.BLUE);
                    pola[orgY][orgX].setText("C");
                }
            }
        }
    }
}
