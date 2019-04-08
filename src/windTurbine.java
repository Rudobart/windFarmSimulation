import java.io.IOException;

public class windTurbine {
    public Double sweptArea=5281.0;
    public Double generatedPower=0.0;
    public Double efficency=0.35;
    public  static Double price=3100000.0;
    public  Double dailyMaintenance = 273.97;
    public double generatePower(Weather weather) {
        if(weather.windAtTurbineHeight>=5 && weather.windAtTurbineHeight<10){
            efficency=0.45;
        }
        else if(weather.windAtTurbineHeight<5){
            efficency=0.25;
        }
        else if(weather.windAtTurbineHeight>10){
            efficency=0.35;
        }
        generatedPower=(efficency*weather.denisty*Math.pow(weather.windAtTurbineHeight,3)*sweptArea)/1000;
        return generatedPower;
    }

}
