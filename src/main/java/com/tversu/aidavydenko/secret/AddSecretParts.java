package com.tversu.aidavydenko.secret;

import com.tversu.aidavydenko.parts.RecoverSecret;
import com.tversu.aidavydenko.utils.Point;

import java.util.*;
import java.util.stream.Collectors;

import static com.tversu.aidavydenko.utils.Utils.gcdex;

public class AddSecretParts {
    private static final int MIN_NUMBER_OF_SECRET= 4;

    public static void addSecretParts(int numberNewParts) {
        //восстановили секрет, после чего создали части, не совпадающие с уже имеющимеся
        //необходимо восстановить весь полином????
        List<Point> parts = new ArrayList<>();
        int secret = RecoverSecret.recover();
        int P = 0;//считали из json
        int numberParts = 0;//считали из json
        List<Integer> polinom = interpolatingLagrangePolynomial(parts, P);
        Set<Integer> keys = new TreeSet<>(parts.stream().map(x -> x.getX()).collect(Collectors.toList()));
        //генерация частей секрета и добавление в json файлы
    }
    //готово
    private static List<Integer> interpolatingLagrangePolynomial(List<Point> parts, int P) {
        List<Integer> polinom = new ArrayList<>(MIN_NUMBER_OF_SECRET);
        for (int i = 0; i < MIN_NUMBER_OF_SECRET; i++) {
            polinom.add(0);
        }
        for (int i = 0; i < parts.size(); i++) {
            List<Integer> keys = parts.stream().map(Point::getX).collect(Collectors.toList());
            List<Integer> temp = findLi(i, keys, P);//*parts.get(i).getY();
            for (int j = 0; j < temp.size(); j++) {
                temp.set(j, (temp.get(j) * parts.get(i).getY()) % P);
                polinom.set(j, (polinom.get(j) + temp.get(j)) % P);
            }
        }
        return polinom;
    }
    //не готово
    private static List<Integer> findLi(int i, List<Integer> points, int P){
        int numerator = 1;
        int denominator = 1;
        for (int j = 0; j < i; j++) {
            numerator *= points.get(j);
            denominator *= points.get(i) - points.get(j);
        }
        for (int j = i+1; j < points.size(); j++) {
            numerator *= points.get(j);
            denominator *= points.get(i) - points.get(j);
        }
        numerator = numerator % P;
        denominator = (gcdex(denominator, P)[1] % 13 + 13) % 13;//поиск обратного
        numerator *= denominator;
        return new LinkedList<>();
    }
}
