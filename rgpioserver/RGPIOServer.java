package rgpioserver;

import rgpio.*;

class SterreborneRun implements MessageListener {

    VDevice allDevices;
    VAnalogInput tmp1, tmp2, tmp3, tmp4;
    VAnalogInput hum1, hum2, hum3, hum4;
    VAnalogInput pdu1, pdu2, pdu3, pdu4, pdu5, pdu6, pdu7, pdu8;

    public void onMessage(MessageEvent e) throws Exception {
        if (e.type != MessageType.SendMessage) {
            System.out.println(e.toString());
        }

    }

    public void start() {

        RGPIO.addMessageListener(this);

        RGPIO.initialize();

        /* uncomment when these devices are defined in devices.json
         tmp1 = RGPIO.VAnalogInput("T1");
         hum1 = RGPIO.VAnalogInput("H1");
         tmp2 = RGPIO.VAnalogInput("T2");
         hum2 = RGPIO.VAnalogInput("H2");
         tmp3 = RGPIO.VAnalogInput("T3");
         hum3 = RGPIO.VAnalogInput("H3");
         tmp4 = RGPIO.VAnalogInput("T4");
         hum4 = RGPIO.VAnalogInput("H4");
         */
        pdu1 = RGPIO.VAnalogInput("PDU1");
        pdu2 = RGPIO.VAnalogInput("PDU2");
        pdu3 = RGPIO.VAnalogInput("PDU3");
        pdu4 = RGPIO.VAnalogInput("PDU4");
        pdu5 = RGPIO.VAnalogInput("PDU5");
        pdu6 = RGPIO.VAnalogInput("PDU6");
        pdu7 = RGPIO.VAnalogInput("PDU7");
        pdu8 = RGPIO.VAnalogInput("PDU8");

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
/*
                tmp1.get();
                tmp2.get();
                tmp3.get();
                tmp4.get();
                hum1.get();
                hum2.get();
                hum3.get();
                hum4.get();
*/
                
pdu1.get();
pdu2.get();
pdu3.get();
pdu4.get();
pdu5.get();
pdu6.get();
pdu7.get();
pdu8.get();

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
