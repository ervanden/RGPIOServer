package rgpioserver;

import rgpio.*;

class TolanTigaDC implements MessageListener {

    VAnalogInput[] tmp;
    VAnalogInput[] hum;
    VAnalogInput[] pdu;

    public void onMessage(MessageEvent e) throws Exception {
        if (e.type != MessageType.SendMessage) {
            System.out.println(e.toString());
        }

    }

    public void start() {

        RGPIO.addMessageListener(this);

        RGPIO.initialize();

        tmp = new VAnalogInput[4];
        hum = new VAnalogInput[4];
        pdu = new VAnalogInput[8];

        for (int i = 0; i < 4; i++) {
            tmp[i] = RGPIO.VAnalogInput("T" + (i + 1));
            hum[i] = RGPIO.VAnalogInput("H" + (i + 1));
        }
        for (int i = 0; i < 8; i++) {
            pdu[i] = RGPIO.VAnalogInput("PDU" + (i + 1));
        }
        
        RGPIO.createRRD(5);

        int[] tmpCurr;
        int[] tmpPrev;
        tmpCurr = new int[4];
        tmpPrev = new int[4];

        for (int i = 0; i < 4; i++) {
            tmpPrev[i] = 0;
        }

        while (true) {
            try {
                Thread.sleep(2000);

                for (int i = 0; i < 4; i++) {
                    tmp[i].get();
                    hum[i].get();
                }
                for (int i = 0; i < 8; i++) {
                    pdu[i].get();
                }
                for (int i = 0; i < 4; i++) {
                    System.out.println("T"+(i+1)+"  curr="+tmpCurr[i]+" prev="+tmpPrev[i]);
                }
                for (int i = 0; i < 4; i++) {
                    tmpCurr[i] = tmp[i].avg();
                    if ((tmpCurr[i] > 2500) && (tmpPrev[i] < 2500)) {
                        String msg = "DC temperature warning : T" + (i + 1) + " exceeds 25 degrees";
                        RGPIO.sendMail("evandenmeersch@sipef.com", msg, "");
                    }
                }

            } catch (InterruptedException ie) {
            }
        }
    }

}

public class RGPIOServer {

    public static void main(String[] args) {
        new TolanTigaDC().start();
    }
}
