import javax.swing.*;
import java.text.NumberFormat;

public class Gra {
    public static void main(String[] args) {

        String xstr = JOptionPane.showInputDialog(null, "Podaj wymiar x: ",JOptionPane.QUESTION_MESSAGE);
        String ystr = JOptionPane.showInputDialog(null, "Podaj wymiar y: ",JOptionPane.QUESTION_MESSAGE);

        try {
            int x = Integer.parseInt(xstr);
            int y = Integer.parseInt(ystr);

            if (x <= 0 || y <= 0) {
                throw new NumberFormatException();
            }

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new GlowneOkno(x, y);
                }
            });
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Błędne wymiary, uruchamiam z domyślnym 20x20", "Błąd", JOptionPane.ERROR_MESSAGE);
            SwingUtilities.invokeLater(() -> new GlowneOkno(20, 20));
        }

    }
}