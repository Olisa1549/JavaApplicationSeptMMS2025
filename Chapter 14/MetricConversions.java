package chapter14;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MetricConversions {
    static final Map<String, Double> LENGTH = new HashMap<>();
    static final Map<String, Double> MASS = new HashMap<>();
    static final Map<String, Double> VOLUME = new HashMap<>();

    static {
        LENGTH.put("meters",1.0); LENGTH.put("meter",1.0);
        LENGTH.put("centimeters",0.01); LENGTH.put("centimeter",0.01);
        LENGTH.put("inches",0.0254); LENGTH.put("inch",0.0254);
        LENGTH.put("feet",0.3048); LENGTH.put("foot",0.3048);

        MASS.put("kilograms",1.0); MASS.put("kilogram",1.0);
        MASS.put("grams",0.001); MASS.put("gram",0.001);
        MASS.put("pounds",0.45359237); MASS.put("pound",0.45359237);

        VOLUME.put("liters",1.0); VOLUME.put("liter",1.0);
        VOLUME.put("quarts",0.946352946); VOLUME.put("quart",0.946352946);
    }

    static Double convert(double value, String from, String to, Map<String,Double> map) {
        return value * map.get(from) / map.get(to);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter value: ");
        double value = input.nextDouble(); input.nextLine();
        System.out.print("From unit: ");
        String from = input.nextLine().toLowerCase();
        System.out.print("To unit: ");
        String to = input.nextLine().toLowerCase();

        Map<String,Double> map = fromMap(from);
        if (map == null || !map.containsKey(to)) {
            System.out.println("Invalid or incompatible units.");
            return;
        }
        System.out.printf("%.4f %s = %.4f %s%n", value, from, convert(value,from,to,map), to);
    }

    static Map<String,Double> fromMap(String unit) {
        if (LENGTH.containsKey(unit)) return LENGTH;
        if (MASS.containsKey(unit)) return MASS;
        if (VOLUME.containsKey(unit)) return VOLUME;
        return null;
    }
}
