package rgpioserver;

import rgpio.*;

class SterreborneRun implements MessageListener {

    VDevice allDevices;
    VAnalogInput tmp1, tmp2, tmp3, tmp4;
    VAnalogInput hum1, hum2, hum3, hum4;

    public void onMessage(MessageEvent e) throws Exception {
        System.out.println(e.toString());

    }

    public void start() {

        RGPIO.addMessageListener(this);

        RGPIO.initialize();

        tmp1 = RGPIO.VAnalogInput("T1");
        hum1 = RGPIO.VAnalogInput("H1");
        tmp2 = RGPIO.VAnalogInput("T2");
        hum2 = RGPIO.VAnalogInput("H2");
        tmp3 = RGPIO.VAnalogInput("T3");
        hum3 = RGPIO.VAnalogInput("H3");
        tmp4 = RGPIO.VAnalogInput("T4");
        hum4 = RGPIO.VAnalogInput("H4");

        RGPIO.createRRD(5);

        /* simulate webclient request
         ClientHandler clientHandler = new ClientHandler();
         ArrayList<String> reply;
         reply=clientHandler.onClientRequest("", "{\"Command\":\"graph\",\"Arg1\":\"range=1d temperature humidity=pink\"}");
         for (String s : reply){ System.out.println(s);              
         }
         */
        /* test sending of email
        
         RGPIO.sendMail("evandenmeersch@sipef.com", "from mailfile RGPIOServer", "it is getting hot in here");
         */
        while (true) {
            try {
                Thread.sleep(2000);
                tmp1.get();
                tmp2.get();
                tmp3.get();
                tmp4.get();
                hum1.get();
                hum2.get();
                hum3.get();
                hum4.get();
                //            System.out.println("RGPIOServer : temp = " + temperature.avg() + " humidity = " + humidity.avg());
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
