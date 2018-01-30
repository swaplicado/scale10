/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scale.gui;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JTextField;
import sa.lib.SLibUtils;

/**
 *
 * @author Sergio Flores
 */
public class SGuiDaemonWeight extends Thread {

    private volatile boolean mbActive;
    private JTextField mjTextField;

    public SGuiDaemonWeight(JTextField textField) {
        mbActive = false;
        mjTextField = textField;
    }

    public boolean isActive() {
        return mbActive;
    }

    public void startDaemon() {
        mbActive = true;
        start();
    }


    public void stopDaemon() {
        mbActive = false;
        mjTextField.setText("");
    }

    @Override
    @SuppressWarnings("WaitWhileNotSynced")
    public void run() {
        boolean moving = true;

        while (mbActive) {
            //System.out.println("Begining weight update...");

            DecimalFormat df = new DecimalFormat("00");
            GregorianCalendar gc = new GregorianCalendar();
            String scale = "" + gc.get(Calendar.MINUTE);

            scale = "" + gc.get(Calendar.MINUTE);
            scale = "☻" + SLibUtils.textRepeat(" ", 5 - scale.length()) + scale + "." + df.format(gc.get(Calendar.SECOND)) + "KG" + (moving ? "M" : " ");
            moving = !moving;

            mjTextField.setText(scale);
            mjTextField.setCaretPosition(0);

            try {
                Thread.sleep(1000 * 1);   // 1 sec
            }
            catch (InterruptedException e) {
                SLibUtils.printException(this, e);
            }

            //System.out.println("Weight updated!");
        }
    }

    /*
     * Public static methods
     */

    public static double parseWeight(final String text) throws Exception {
        double weight = 0;

        if (text == null) {
            throw new Exception("Se debe proporcionar el texto.");
        }
        else if (text.length() != 12) {
            throw new Exception("La longitud del texto debe ser de 12 caracteres.");
        }
        else if (text.endsWith("M")) {
            throw new Exception("El peso de la báscula no es estable.");
        }
        else {
            weight = SLibUtils.parseDouble(text.substring(1, 9));   // weight format: "☻    0.00KGM"
        }

        return weight;
    }
}
