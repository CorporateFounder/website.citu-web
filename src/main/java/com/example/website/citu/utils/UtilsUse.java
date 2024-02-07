package com.example.website.citu.utils;

import com.example.website.citu.model.Block;
import com.example.website.citu.model.DtoTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UtilsUse {
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xff & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    public static byte[] hexToBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            int index = i * 2;
            int value = Integer.parseInt(hex.substring(index, index + 2), 16);
            bytes[i] = (byte) value;
        }
        return bytes;
    }

    // Метод, который вычисляет экономический рост или спад блокчейна в процентах
    //Пусть
    // At - средняя сумма транзакций в текущем блоке,
    // Ap - средняя сумма транзакций в предыдущем блоке,
    // Nt - количество транзакций в текущем блоке,
    // Np- количество транзакций в предыдущем блоке,
    // Ut - количество уникальных адресов в текущем блоке,
    // Up - количество уникальных адресов в предыдущем блоке.
    // Тогда экономический рост или спад блокчейна в процентах можно выразить как:
    //G=(Ap/At)*(Nt/Np)*(Ut/Up)-1
    //Эта формула учитывает, что если средняя сумма транзакций уменьшается, то экономика растет, а если количество транзакций и уникальных адресов увеличивается, то экономика также растет. Если G>0, то экономика блокчейна растет, а если G<0, то экономика блокчейна снижается.
    //формула
    public static double growth (double Ap, double At, double Np, double Nt, double Up, double Ut) {
        // Веса для каждого показателя
        double wA = 1.1; // Вес для средней суммы транзакций
        double wN = 0.99; // Вес для количества транзакций
        double wU = 1.21; // Вес для количества уникальных адресов
        // Формула, которая учитывает ваши критерии и логику
        double G = (wA*(Ap / At)) * (wN*(Nt / Np)) * (wU*(Ut / Up)) - 1;
        // Возвращаем результат


        //Награда дополнительная не может быть ниже нуля и выше 10
        G = G > 10? 10: G;
        G = G < 0? 0: G;
        G = Math.round(G);
        return G;
    }

    public static double sumDollarFromTransactions(Block block){
        double sum = 0;
        for (DtoTransaction transaction : block.getDtoTransactions()) {
            sum += transaction.getDigitalDollar();
        }
        return sum;
    }
    //получение уникальных адрессов
    public static int uniqAddress(Block block){
        List<String> address = new ArrayList<>();
        for (DtoTransaction transaction : block.getDtoTransactions()) {
            address.add(transaction.getSender());
        }
        return address.stream().distinct().collect(Collectors.toList()).size();
    }

    //ПОДСЧЕТ НАГРАДЫ
    public static double blocksReward(Block acutal, Block prev){
        double Ap = sumDollarFromTransactions(prev)/ prev.getDtoTransactions().size();
        double At = sumDollarFromTransactions(acutal)/ acutal.getDtoTransactions().size();
        double Nt = acutal.getDtoTransactions().size();
        double Np = prev.getDtoTransactions().size();
        double Ut = uniqAddress(acutal);
        double Up = uniqAddress(prev);
        return growth(Ap, At, Np, Nt, Up, Ut);
    }
}
