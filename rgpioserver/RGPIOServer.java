package rgpioserver;

import rgpio.*;

class TolanTigaDC implements MessageListener {

    VAnalogInput[] tmp;
    VAnalogInput[] hum;
    VAnalogInput[] pdu;

    final static int nrSensors=4;
    final static int nrPdus=8;
    
    public void onMessage(MessageEvent e) throws Exception {
        if (e.type != MessageType.SendMessage) {
            System.out.println(e.toString());
        }

    }

    public void start() {

        RGPIO.addMessageListener(this);

        RGPIO.initialize();

        tmp = new VAnalogInput[nrSensors];
        hum = new VAnalogInput[nrSensors];
        pdu = new VAnalogInput[nrPdus];

        for (int i = 0; i < nrSensors; i++) {
            tmp[i] = RGPIO.VAnalogInput("T" + (i + 1));
            hum[i] = RGPIO.VAnalogInput("H" + (i + 1));
        }
        for (int i = 0; i < nrPdus; i++) {
            pdu[i] = RGPIO.VAnalogInput("PDU" + (i + 1));
        }
        
        RGPIO.createRRD(5);

        int[] tmpCurr;
        int[] tmpPrev;
        tmpCurr = new int[nrSensors];
        tmpPrev = new int[nrSensors];

        for (int i = 0; i < nrSensors; i++) {
            tmpPrev[i] = 0;
        }

        while (true) {
            try {
                Thread.sleep(2000);

                for (int i = 0; i < nrSensors; i++) {
                    tmp[i].get();
                                        System.out.println("T"+(i+1)+"  "+tmp[i].avg());
                    hum[i].get();
                                                  System.out.println("H"+(i+1)+"  "+tmp[i].avg());
                }
                for (int i = 0; i < nrPdus; i++) {
                    pdu[i].get();
                                                  System.out.println("PDU"+(i+1)+"  "+tmp[i].avg());
                }

                for (int i = 0; i < nrSensors; i++) {
                    tmpCurr[i] = tmp[i].avg();
                    if ((tmpCurr[i] > 2500) && (tmpPrev[i] < 2500)) {
                        String msg = "DC temperature warning : T" + (i + 1) + " exceeds 25 degrees";
                        RGPIO.sendMail("evandenmeersch@sipef.com", msg, "");
                    };
                    tmpPrev[i]=tmpCurr[i];
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
