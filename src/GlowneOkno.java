import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GlowneOkno extends JFrame implements KeyListener {

    private Swiat swiat;
    private Plansza plansza;
    private JTextArea dziennikZdarzen;
    private int wcisnietyKlawisz = 0;

    public GlowneOkno() {
        setTitle("Wirtualny Świat - Mikołaj Tchorek, s208435");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        swiat = new Swiat(20, 20);

        plansza = new Plansza(swiat, 20, 20);

        add(plansza, BorderLayout.CENTER);

        JPanel panelSterowania = new JPanel();
        panelSterowania.setLayout(new BorderLayout());

        JButton btnNastepnaTura = new JButton("Następna tura");

        btnNastepnaTura.setFocusable(false);

        btnNastepnaTura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swiat.WykonajTure(wcisnietyKlawisz);

                wcisnietyKlawisz = 0;

                plansza.odswiezPlansze();

                odswiezDziennik();
            }
        });
        panelSterowania.add(btnNastepnaTura, BorderLayout.NORTH);

        // Pole tekstowe na logi
        dziennikZdarzen = new JTextArea(10, 20);
        dziennikZdarzen.setEditable(false); // Blokujemy możliwość pisania przez gracza
        dziennikZdarzen.setFocusable(false);
        JScrollPane scrollPane = new JScrollPane(dziennikZdarzen); // Dodajemy suwak
        panelSterowania.add(scrollPane, BorderLayout.CENTER);

        // Dodajemy panel sterowania na dół głównego okna (lub na wschód - BorderLayout.EAST)
        add(panelSterowania, BorderLayout.SOUTH);

        // Pierwsze narysowanie planszy
        plansza.odswiezPlansze();
        // Nasłuchiwanie klawiszy dla CAŁEGO okna
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow(); // Zmuszamy okno do słuchania

        // Wyświetlenie okna
        setVisible(true);
    }

    // --- Metody z interfejsu KeyListener ---
    @Override
    public void keyPressed(KeyEvent e) {
        // Kiedy gracz wciska przycisk, po prostu go zapamiętujemy
        wcisnietyKlawisz = e.getKeyCode();

        // Możesz tu dać System.out.println("Wciśnięto: " + zapamietanyKlawisz); żeby testować
    }

    @Override
    public void keyReleased(KeyEvent e) {} // Niepotrzebne
    @Override
    public void keyTyped(KeyEvent e) {}    // Niepotrzebne

    private void odswiezDziennik() {
        dziennikZdarzen.setText(""); // Czyścimy stare logi
        dziennikZdarzen.append("--- RAPORT Z TURY ---\n");
        // Zakładam, że przeniesiesz listę komunikatów z C++
        for (String komunikat : swiat.GetKomunikaty()) {
            dziennikZdarzen.append(komunikat + "\n");
       }
    }
}