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
        //необходимо восстановить весь полином
        //int secret = RecoverSecret.recover();//находится при поиске полинома
        // TODO: считывание частей секрета из json
        List<Point> parts = new ArrayList<>();
        // TODO: считывание P из json
        int P = 0;
        //Set<Integer> keys = parts.stream().map(Point::getX).collect(Collectors.toCollection(TreeSet::new));
        // TODO: считываение кол-ва новых частей секрета из json
        int numberNewSecretParts = 0;
        List<Integer> polinom = interpolatingLagrangePolynomial(parts, P);
        // TODO: генерация частей секрета и добавление в json файлы
        Map<Integer, Integer> shares = addShares(parts, polinom, numberNewSecretParts, P);

    }

    /**
      сгенерированная часть может совпасть с той, что была создана ранее, но не исопльзовалась для восстановления секрета
     */
    private static Map<Integer, Integer> addShares(List<Point> parts, List<Integer> polinom, int numberNewSecretParts, int P) {
        Map<Integer, Integer> newParts = parts.stream().collect(Collectors.toMap(Point::getX, Point::getY));
        for (int i = 1; i <= numberNewSecretParts; i++) {
            int temp = 0;
            int k = (int) (Math.random() * polinom.size());
            if (newParts.containsKey(k)) {
                i--;
                continue;
            }
            for (int j = 0; j < polinom.size(); j++) {
                temp += polinom.get(j) * Math.pow(k, j);
            }
            if (temp % P == 0) {
                i--;
                continue;
            }
            newParts.put(k, temp % P);
        }
        return newParts;
    }

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


    //готово
    private static List<Integer> findLi(int i, List<Integer> points, int P){
        int[] numerator = {1,0,0,1};
        int denominator = 1;
        switch (i){
            case 0:
                numerator[1] = points.get(1) * points.get(2) +
                        (points.get(1) + points.get(2)) * points.get(3);
                break;
            case 1:
                numerator[1] = points.get(0) * points.get(2) +
                        (points.get(0) + points.get(2)) * points.get(3);
                break;
            case 2:
                numerator[1] = points.get(0) * points.get(1) +
                        (points.get(0) + points.get(1)) * points.get(3);
                break;
            case 3:
                numerator[1] = points.get(0) * points.get(1) +
                        (points.get(0) + points.get(1)) * points.get(2);
                break;
        }
        for (int j = 0; j < i; j++) {
            numerator[0] *= points.get(j);
            numerator[2] += points.get(j);
            denominator *= points.get(i) - points.get(j);
        }
        for (int j = i+1; j < points.size(); j++) {
            numerator[0] *= points.get(j);
            numerator[2] += points.get(j);
            denominator *= points.get(i) - points.get(j);
        }
        denominator = (gcdex(denominator, P)[1] % P + P) % P;//поиск обратного
        for (int j = 0; j < 4; j++) {
            numerator[i] = ((numerator[i]%P)*denominator)% P;//может можно оптимизировать
        }
        return Arrays.stream(numerator).boxed().collect(Collectors.toList());
    }
}
