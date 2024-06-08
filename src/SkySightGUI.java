import org.json.simple.JSONObject;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SkySightGUI extends JFrame{
    private JSONObject weatherData;
    public SkySightGUI(){
        super("SkySight");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450,650);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        addGuiComponents();
    }
    private void addGuiComponents(){
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(15,15,351,45);
        searchTextField.setFont(new Font("Dialog", Font.PLAIN,24));
        add(searchTextField);

        JLabel Error = new JLabel("Location not found");
        Error.setVisible(false);
        Error.setBounds(0,50,400,54);
        Error.setFont(new Font("Dialog", Font.PLAIN,28));
        Error.setHorizontalAlignment(SwingConstants.CENTER);
        add(Error);

        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png"));
        weatherConditionImage.setBounds(0,125,450,217);
        add(weatherConditionImage);

        JLabel temperatureText = new JLabel("10°C");
        temperatureText.setBounds(0,350,450,54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD,48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        JLabel weatherConditionDescription = new JLabel("Cloudy");
        weatherConditionDescription.setBounds(0,405,450,36);
        weatherConditionDescription.setFont(new Font("Dialog", Font.PLAIN,32));
        weatherConditionDescription.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDescription);

        JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
        humidityImage.setBounds(15,500,74,66);
        add(humidityImage);

        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100% </html>");
        humidityText.setBounds(90,500,85,55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN,16));
        add(humidityText);

        JLabel windSpeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        windSpeedImage.setBounds(220,500,74,66);
        add(windSpeedImage);

        JLabel windSpeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windSpeedText.setBounds(310,500,85,55);
        windSpeedText.setFont(new Font("Dialog", Font.PLAIN,16));
        add(windSpeedText);

        weatherConditionImage.setVisible(false);
        temperatureText.setVisible(false);
        weatherConditionDescription.setVisible(false);
        humidityImage.setVisible(false);
        humidityText.setVisible(false);
        windSpeedImage.setVisible(false);
        windSpeedText.setVisible(false);

        JButton searchButton = new JButton(loadImage("src/assets/search.png"));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375,15,47,45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = searchTextField.getText();

                if(userInput.replaceAll("\\s","").length() <= 0)
                {
                    return;
                }
                weatherData = SkySight.getWeatherData(userInput);
                if(weatherData == null)
                {
                    Error.setVisible(true);
                    weatherConditionImage.setVisible(false);
                    temperatureText.setVisible(false);
                    weatherConditionDescription.setVisible(false);
                    humidityImage.setVisible(false);
                    humidityText.setVisible(false);
                    windSpeedImage.setVisible(false);
                    windSpeedText.setVisible(false);
                    return;
                }
                else
                {
                    Error.setVisible(false);
                    weatherConditionImage.setVisible(true);
                    temperatureText.setVisible(true);
                    weatherConditionDescription.setVisible(true);
                    humidityImage.setVisible(true);
                    humidityText.setVisible(true);
                    windSpeedImage.setVisible(true);
                    windSpeedText.setVisible(true);
                }
                String weatherCondition = (String) weatherData.get("weather_code");

                switch (weatherCondition)
                {
                    case "Clear" :
                        weatherConditionImage.setIcon(loadImage("src/assets/clear.png"));
                        break;
                    case "Cloudy" :
                        weatherConditionImage.setIcon(loadImage("src/assets/cloudy.png"));
                        break;
                    case "Rain" :
                        weatherConditionImage.setIcon(loadImage("src/assets/rain.png"));
                        break;
                    case "Snow" :
                        weatherConditionImage.setIcon(loadImage("src/assets/snow.png"));
                        break;
                }
                double temperature = (double) weatherData.get("temperature_2m");
                temperatureText.setText((int)temperature + "°C");
                weatherConditionDescription.setText(weatherCondition);

                long humidity = (long) weatherData.get("relative_humidity_2m");
                humidityText.setText("<html><b>Humidity</b> "+ humidity +"%</html>");

                double windSpeed = (double) weatherData.get("wind_speed_10m");
                windSpeedText.setText("<html><b>Windspeed</b> " + (int)windSpeed + "km/h</html>");

            }
        });
        add(searchButton);

    }
    @org.jetbrains.annotations.Nullable
    private ImageIcon loadImage(String imagePath)
    {
        try{
            BufferedImage image = ImageIO.read(new File(imagePath));
            return new ImageIcon(image);
        }
        catch (IOException e) {
                e.printStackTrace();
        }
        System.out.println("File Not Found");
        return null;
    }
}
