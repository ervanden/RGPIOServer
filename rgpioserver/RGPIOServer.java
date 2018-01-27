package rgpioserver;

import java.util.ArrayList;
import rgpio.*;

class SterreborneRun implements MessageListener {

    VDevice allDevices;
    VAnalogInput temperature;
    VAnalogInput humidity;

    public void onMessage(MessageEvent e) throws Exception {
        System.out.println(e.toString());

    }

    public void start() {

        RGPIO.addMessageListener(this);
        
        String configurationDir=null;
        String dataStoreDir=null;
        
        if (System.getProperty("file.separator").equals("/")) {
            configurationDir="/home/pi/RGPIO/";
            dataStoreDir="/home/pi/RGPIO/dataStore/";            
        } else {
            configurationDir="C:\\Users\\erikv\\Documents\\RGPIO\\";
            dataStoreDir="C:\\Users\\erikv\\Documents\\RRD\\";
        }

        RGPIO.initialize(configurationDir);
        allDevices = RGPIO.VDevice("allDevices");
        temperature = RGPIO.VAnalogInput("temperature");
        humidity = RGPIO.VAnalogInput("humidity");
        allDevices.minMembers = 2;

        RGPIO.createRRD(dataStoreDir, 5);
        
/* simulate webclient request
                ClientHandler clientHandler = new ClientHandler();
                ArrayList<String> reply;
                reply=clientHandler.onClientRequest("", "{\"Command\":\"graph\",\"Arg1\":\"range=1d temperature humidity=pink\"}");
                for (String s : reply){ System.out.println(s);              
                }
*/
        while (true) {
            try {
                Thread.sleep(2000);
                temperature.get();
                humidity.get();
                System.out.println("RGPIOServer : temp = " + temperature.avg() + " humidity = " + humidity.avg());
            } catch (InterruptedException ie) {
            }
        }
    }

}

public class RGPIOServer {

    public static void main(String[] args) {
        new SterreborneRun().start();
    }
}
