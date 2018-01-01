package rgpioserver;

import rgpio.*;

class SterreborneRun implements MessageListener {

    VDevice allDevices;
    VAnalogInput temp;

    public void onMessage(MessageEvent e) throws Exception {
        System.out.println(e.toString());

    }

    public void start() {

        RGPIO.addMessageListener(this);
        RGPIO.initialize("/home/pi/RGPIO/");

        allDevices = RGPIO.VDevice("allDevices");
        temp = RGPIO.VAnalogInput("temp");
        allDevices.minMembers = 2;


        while (true) {
            try {
                Thread.sleep(2000);
                temp.get();
                System.out.println("MAIN : temp = "+temp.avg());
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
