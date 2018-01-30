/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scale.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TooManyListenersException;
import javax.comm.CommPort;
import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;
import javax.swing.JTextField;
import sa.lib.SLibUtils;

/**
 *
 * @author Sergio Flores
 */
public class SGuiSerialPortListener implements SerialPortEventListener {

    protected String msPort;
    protected JTextField mjTextField;
    protected boolean mbListening;
    protected SerialPort moSerialPort;
    protected CommPort moCommPort;
    protected CommPortIdentifier moCommPortIdentifier;
    protected BufferedReader moBufferedReader;

    public SGuiSerialPortListener(String port, JTextField textField) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, TooManyListenersException, IOException {
        msPort = port;
        mjTextField = textField;
        mbListening = false;

        System.out.println("Getting port identifier for '" + msPort + "'...");
        moCommPortIdentifier = CommPortIdentifier.getPortIdentifier(msPort);
        System.out.println("Opening port '" + msPort + "'...");
        moCommPort = moCommPortIdentifier.open(SGuiClientApp.APP_NAME, 30 * 1000);  // timeout of 30 sec

        System.out.println("Casting serial port '" + msPort + "'...");
        moSerialPort = (SerialPort) moCommPort;
        System.out.println("Setting parameters for serial port '" + msPort + "'...");
        moSerialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

        System.out.println("Creating buffered reader from port '" + msPort + "'...");
        moBufferedReader = new BufferedReader(new InputStreamReader(moCommPort.getInputStream()));

        System.out.println("Setting notify on data available property on serial port '" + msPort + "'...");
        moSerialPort.notifyOnDataAvailable(true);
        System.out.println("Adding event listener into serial port '" + msPort + "'...");
        moSerialPort.addEventListener((SerialPortEventListener) this);

        mbListening = true;
    }

    public boolean isListening() {
        return mbListening;
    }

    public void closeListener() throws IOException {
        if (moBufferedReader != null) {
            moBufferedReader.close();
        }

        if (moCommPort != null) {
            moCommPort.close();
        }

        mbListening = false;
    }

    @Override
    public void serialEvent(SerialPortEvent ev) {
        String line = null;

        //System.out.println("Listening to serial event...");

        try {
            line = moBufferedReader.readLine();

            if (line == null) {
                throw new Exception("EOF en puerto serial '" + msPort + "'.");
            }

            mjTextField.setText(line);
            mjTextField.setCaretPosition(0);
        }
        catch (IOException e) {
            SLibUtils.showException(this, e);
        }
        catch (Exception e) {
            SLibUtils.showException(this, e);
        }
    }

    @Override
    public void finalize() {
        try {
            closeListener();
        }
        catch (Exception e) {
            SLibUtils.printException(this, e);
        }
    }
}
