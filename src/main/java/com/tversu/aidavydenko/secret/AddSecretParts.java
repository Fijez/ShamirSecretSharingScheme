package com.tversu.aidavydenko.secret;

import com.tversu.aidavydenko.utils.FileManager;
import com.tversu.aidavydenko.utils.SecretPart;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.tversu.aidavydenko.utils.Utils.mod;

public class AddSecretParts {
    private static final int MIN_NUMBER_OF_SECRET = 4;

    public static void addSecretParts() {
        //восстановили секрет, после чего создали части, не совпадающие с уже имеющимеся
        //необходимо восстановить весь полином
        //int secret = RecoverSecret.recover();//находится при поиске полинома
        List<SecretPart> parts = FileManager.getSecretPartsPoints();
        int numK = parts.size();//кол-во частей, считанных из файла
        if (numK < MIN_NUMBER_OF_SECRET) {
            throw new RuntimeException("Недостаточное кол-во частей секрета");
        }
        int P = parts.get(0).getP();
        for (SecretPart part :
                parts) {
            if (!part.getP().equals(P)) {
                throw new RuntimeException("Присутствуют части разных полей");
            }
        }
        //Set<Integer> keys = parts.stream().map(Point::getX).collect(Collectors.toCollection(TreeSet::new));
        System.out.println("Введите кол-во частей для добавления: ");
        int numberNewSecretParts = new Scanner(System.in).nextInt();
        List<Integer> polinom = interpolatingLagrangePolynomial(parts, P);
        // TODO: генерация частей секрета и добавление в json файлы
        List<SecretPart> shares = addShares(parts, polinom, numberNewSecretParts, P);
        FileManager.clearPartsFolder();
        FileManager.writeSharingSecret(shares);
    }

    /**
     * сгенерированная часть может совпасть с той, что была создана ранее, но не использовалась для восстановления секрета
     */
    private static List<SecretPart> addShares(List<SecretPart> parts, List<Integer> polinom, int numberNewSecretParts, int P) {
        Map<Integer, Integer> newParts = parts.stream().
                collect(Collectors.
                        toMap(SecretPart::getPoint, SecretPart::getValue));
        for (int i = 1; i <= numberNewSecretParts; i++) {
            int temp = 0;
            int k = (int) (Math.random() * P);
            if (newParts.containsKey(k) || k == 0) {
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
            newParts.put(k, (temp % P + P) % P);
        }
        List<SecretPart> newSecretParts = newParts.
                entrySet().
                stream().
                map(x -> new SecretPart(x.getKey(), x.getValue(), P)).
                collect(Collectors.toList());
        return newSecretParts;
    }

    public static List<Integer> interpolatingLagrangePolynomial(List<SecretPart> parts, int P) {
        List<Integer> polinom = new ArrayList<>(MIN_NUMBER_OF_SECRET);
        for (int i = 0; i < MIN_NUMBER_OF_SECRET; i++) {
            polinom.add(0);
        }
        List<Integer> keys = parts.stream().
                map(SecretPart::getPoint).
                limit(MIN_NUMBER_OF_SECRET).
                collect(Collectors.toList());
        for (int i = 0; i < MIN_NUMBER_OF_SECRET; i++) {
            List<Integer> li = findLi(i, keys, P);//*parts.get(i).getY();
            for (int j = 0; j < MIN_NUMBER_OF_SECRET; j++) {
                li.set(j, ((li.get(j) * parts.get(i).getValue()) % P));
                polinom.set(j, (polinom.get(j) + li.get(j)) % P);
            }
        }
        return polinom;
    }


    //готово
    private static List<Integer> findLi(int i, List<Integer> points, int P) {
        int[] numerator = {1, 0, 0, 1};
        int denominator = 1;

        for (Integer point : points) {
            if (!points.get(i).equals(point)) {
                denominator *= (points.get(i) - point);
                numerator[0] *= (-point);
                numerator[2] -= point;
            }
        }

        switch (i) {
            case 0:
                numerator[1] =( ((((-points.get(1)) + (-points.get(2))) * (-points.get(3)))
                                + ((-points.get(1)) * (-points.get(2)))) % P + P) % P;
                break;
            case 1:
                numerator[1] =( ((((-points.get(0)) + (-points.get(2))) * (-points.get(3)))
                        + ((-points.get(0)) * (-points.get(2)))) % P + P) % P;
                break;
            case 2:
                numerator[1] =( ((((-points.get(0)) + (-points.get(1))) * (-points.get(3)))
                        + ((-points.get(0)) * (-points.get(1)))) % P + P) % P;
                break;
            case 3:
                numerator[1] =( ((((-points.get(0)) + (-points.get(1))) * (-points.get(2)))
                        + ((-points.get(0)) * (-points.get(1)))) % P + P) % P;
                break;
        }

        boolean denomIsUpZero = denominator >= 0;
        denominator = mod(denominator, P);
//        if (!denomIsUpZero) {
//            denominator = (denominator * 2) % P;
//        }

        int finalDenominator = denominator;
        numerator = Arrays.stream(numerator).map(x -> ((((x % P) + P) % P) * finalDenominator) % P).toArray();
        return Arrays.stream(numerator).boxed().collect(Collectors.toList());
//
//
//        int[] numerator = {1, 0, 0, 1};
//        int denominator = 1;
//        switch (i) {
//            case 0:
//                numerator[1] = points.get(1) * points.get(2) +
//                        ((-points.get(1)) + (-points.get(2))) * (-points.get(3));
//                break;
//            case 1:
//                numerator[1] = points.get(0) * points.get(2) +
//                        ((-points.get(0)) + (-points.get(2))) * (-points.get(3));
//                break;
//            case 2:
//                numerator[1] = points.get(0) * points.get(1) +
//                        ((-points.get(0)) + (-points.get(1))) * (-points.get(3));
//                break;
//            case 3:
//                numerator[1] = points.get(0) * points.get(1) +
//                        ((-points.get(0)) + (-points.get(1))) * (-points.get(2));
//                break;
//        }
//        for (int j = 0; j < i; j++) {
//            numerator[0] *= (-points.get(j));
//            numerator[2] += (-points.get(j));
//            denominator *= (-points.get(i)) - (-points.get(j));
//        }
//        for (int j = i + 1; j < points.size(); j++) {
//            numerator[0] *= (-points.get(j));
//            numerator[2] += (-points.get(j));
//            denominator *= (-points.get(i)) - (-points.get(j));
//        }
//        boolean denomIsUpZero = denominator > 0;
//        denominator = (denominator % P + P) % P;
//        denominator = mod(denominator, P);//поиск обратного
//        if (!denomIsUpZero) {
//            denominator = (denominator * 2) % P;
//        }
//        for (int j = 0; j < 4; j++) {
//            numerator[i] = (((numerator[i] % P + P) % P) * denominator) % P;
//        }
//        return Arrays.stream(numerator).boxed().collect(Collectors.toList());
    }
}
