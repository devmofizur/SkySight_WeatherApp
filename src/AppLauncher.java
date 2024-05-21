import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SkySightGUI().setVisible(true);

               // System.out.println(SkySight.getWeatherData("Meherpur"));

            }
        });
    }
}
