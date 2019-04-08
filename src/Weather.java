import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Weather{
    public static final int windHeight = 10;
    public Integer wind;
    public Double temperature;
    private String filePath="res/weatherKielce.csv";
    private BufferedReader br = new BufferedReader(new FileReader(filePath));
    private String line;
    private String sub;
    public Integer turbineHeight = 100;
    public Double windAtTurbineHeight;
    public Double roughness=0.03;
    public Double pressure;
    public Double pressureAtHeight;
    public Double denisty;
    public String date;

    public Weather() throws FileNotFoundException, IOException {
        br.readLine();
    }

    public void readWeather(){
        try{
            if ((line = br.readLine()) != null) {
                String[] cols = line.split(";");
                sub = cols[7].substring(1, cols[7].length() - 1);
                sub=checkString(sub);
                wind = Integer.parseInt(sub.trim());
                sub = cols[1].substring(1, cols[1].length() - 1);
                sub=checkString(sub);
                temperature = Double.parseDouble(sub.trim());
                sub = cols[2].substring(1, cols[2].length() - 1);
                sub=checkString(sub);
                pressure = Double.parseDouble(sub.trim()) * 133.332;
                date = cols[0].substring(6, cols[0].length() - 1);
            }}catch(IOException|NumberFormatException e){
            System.out.println("brak danych");
        };

    }
    public void calculateWind(){
        windAtTurbineHeight=wind*(Math.log(turbineHeight/roughness)/Math.log(windHeight/roughness));
    }
    public void calcuatePressure(){
        pressureAtHeight=pressure*Math.exp((-0.0289644*9.80665*110)/(8.3144598*(temperature+273)));
    }
    public void calculateDensity(){
        denisty=pressureAtHeight/(287.058*(temperature+273));
    }
    public void calculateVariables(){
        calculateWind();
        calcuatePressure();
        calculateDensity();
        validateWind();
    }
    public void validateWind(){
        if(windAtTurbineHeight<2){
            windAtTurbineHeight=0.0;
        }
        if(windAtTurbineHeight>=31){
            windAtTurbineHeight=0.0;
        }

    }
    public String checkString(String sub){
        if(sub==""){
            sub="0";
        }
        return sub;
    }





}
