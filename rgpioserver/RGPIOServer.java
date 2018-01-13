package rgpioserver;

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
//        RGPIO.initialize("/home/pi/RGPIO/");
        RGPIO.initialize("C:\\Users\\erikv\\Documents\\RGPIO\\");
        
        allDevices = RGPIO.VDevice("allDevices");
        temperature = RGPIO.VAnalogInput("temperature");
        humidity = RGPIO.VAnalogInput("humidity");
        allDevices.minMembers = 2;

        RGPIO.createRRD("C:\\Users\\erikv\\Documents\\RRD\\",30);
        
        while (true) {
            try {
                Thread.sleep(2000);
                temperature.get();
                humidity.get();
                System.out.println("RGPIOServer : temp = "+temperature.avg()+ " humidity = "+humidity.avg());
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
