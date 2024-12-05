package com.example.website.citu.utils;

import com.example.website.citu.model.Block;
import com.example.website.citu.model.DtoTransaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
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
    public static double growth(double Ap, double At, double Np, double Nt, double Up, double Ut) {
        // Веса для каждого показателя
        double wA = 1.1; // Вес для средней суммы транзакций
        double wN = 0.99; // Вес для количества транзакций
        double wU = 1.21; // Вес для количества уникальных адресов
        // Формула, которая учитывает ваши критерии и логику
        double G = (wA * (Ap / At)) * (wN * (Nt / Np)) * (wU * (Ut / Up)) - 1;
        // Возвращаем результат


        //Награда дополнительная не может быть ниже нуля и выше 10
        G = G > 10 ? 10 : G;
        G = G < 0 ? 0 : G;
        G = Math.round(G);
        return G;
    }

    public static double sumDollarFromTransactions(Block block) {
        double sum = 0;
        for (DtoTransaction transaction : block.getDtoTransactions()) {
            sum += transaction.getDigitalDollar();
        }
        return sum;
    }

    //получение уникальных адрессов
    public static int uniqAddress(Block block) {
        List<String> address = new ArrayList<>();
        for (DtoTransaction transaction : block.getDtoTransactions()) {
            address.add(transaction.getSender());
        }
        return address.stream().distinct().collect(Collectors.toList()).size();
    }

    //ПОДСЧЕТ НАГРАДЫ
    public static double blocksReward(Block acutal, Block prev) {
        double Ap = sumDollarFromTransactions(prev) / prev.getDtoTransactions().size();
        double At = sumDollarFromTransactions(acutal) / acutal.getDtoTransactions().size();
        double Nt = acutal.getDtoTransactions().size();
        double Np = prev.getDtoTransactions().size();
        double Ut = uniqAddress(acutal);
        double Up = uniqAddress(prev);
        return growth(Ap, At, Np, Nt, Up, Ut);
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static long calculateScore(BigDecimal x, BigDecimal x0) {
        if (x.compareTo(BigDecimal.ZERO) <= 0) {
            return 0;
        }
        BigDecimal log2 = new BigDecimal(Math.log(2));
        BigDecimal score = BigDecimal.valueOf(Math.ceil(Math.log(x.divide(x0, RoundingMode.HALF_UP).doubleValue()) / log2.doubleValue()));
        return Math.min(400, score.longValue());
    }


    static int V34_NEW_ALGO = 187200;
    static int V28_CHANGE_ALGORITH_DIFF_INDEX = 133750;
    static int YEAR = 360;
    static int ALGORITM_MINING = 295316;
    static int DIFFICULT_MONEY = 22;
    static double V28_REWARD = 5;
    static double V34_MINING_REWARD = 0.2;
    static int ALGORITM_MINING_2 = 296583;
    static int MULT = 6;
    static int START_BLOCK_DECIMAL_PLACES = 268765;
    static int DECIMAL_PLACES = 10;
    static int SENDING_DECIMAL_PLACES = 8;
    static int CHANGE_DECIMAL_2_INDEX = 321739;
    static int SENDING_DECIMAL_PLACES_2 = 2;
    static int MONEY_MILTON_FRIDMAN_INDEX = 326840;
    static int MILTON_MONEY_DAY = 432;
    static double MONEY_MILTON_FRIDMAN = 24;
    static double PERCENT_MONEY_MILTON_FRIMDAN = 1.02;
    public static double rewardCalculatorreward(double G, int difficulty, int MULTIPLIER, int index) {

        double minerRewards = 0;


        if (index >= V34_NEW_ALGO) {
            long money = (index - V28_CHANGE_ALGORITH_DIFF_INDEX)
                    / (576 * YEAR);
            money = (long) (MULTIPLIER );
            money = money < 1 ? 1 : money;
            double moneyFromDif = 0;

            if (index > ALGORITM_MINING) {
                moneyFromDif = (difficulty - DIFFICULT_MONEY) / 2;
                moneyFromDif = moneyFromDif > 0 ? moneyFromDif : 0;
            }

            minerRewards = (V28_REWARD + G + (difficulty * V34_MINING_REWARD) + moneyFromDif) * money;
            double digitalReputationForMiner = (V28_REWARD + G + (difficulty * V34_MINING_REWARD) + moneyFromDif) * money;

            if (index > ALGORITM_MINING_2) {
                minerRewards += moneyFromDif * (MULT + G);
                digitalReputationForMiner += moneyFromDif * (MULT + G);
            }

            //фридман модель рост в 4%
            minerRewards = UtilsUse.calculateMinedMoneyFridman(index, minerRewards, difficulty, G);


            if (index > START_BLOCK_DECIMAL_PLACES && index <= ALGORITM_MINING) {
                minerRewards = UtilsUse.round(minerRewards, DECIMAL_PLACES);
                digitalReputationForMiner = UtilsUse.round(digitalReputationForMiner, DECIMAL_PLACES);

            }
            if (index > ALGORITM_MINING) {
                int decimal = SENDING_DECIMAL_PLACES;

                if (index >= CHANGE_DECIMAL_2_INDEX) {
                    decimal = SENDING_DECIMAL_PLACES_2;
                }

                minerRewards = UtilsUse.round(minerRewards, decimal);


            }
        }
        return minerRewards;
    }

    public static double calculateMinedMoneyFridman(long index, double currentReward, double diffMoney, double G) {
        // Проверяем, активирован ли механизм увеличения награды
        if (index < MONEY_MILTON_FRIDMAN_INDEX) {
            return currentReward;
        }
        diffMoney = (diffMoney - 22) / 4;
        if (diffMoney < 0) diffMoney = 0;
        double result = (G / 4) + diffMoney;

        // Рассчитываем количество блоков в году
        long blocksPerYear = (long) MILTON_MONEY_DAY * YEAR;

        // Вычисляем количество блоков, прошедших с начала отсчета
        long blocksSinceStart = index - MONEY_MILTON_FRIDMAN_INDEX;

        // Определяем количество полных лет, прошедших с начала отсчета
        int yearsPassed = (int) (blocksSinceStart / blocksPerYear);

        // Рассчитываем награду за блок с учетом ежегодного увеличения
        double newBlockReward = (MONEY_MILTON_FRIDMAN + result) * Math.pow(PERCENT_MONEY_MILTON_FRIMDAN, yearsPassed);

        // Округляем новую награду до двух знаков после запятой
        newBlockReward = round(newBlockReward, 2);

        // Суммируем текущую награду с новой наградой
        double updatedReward = currentReward + newBlockReward;

        // Округляем итоговую награду до двух знаков после запятой
        updatedReward = round(updatedReward, 2);

        return updatedReward;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value *= factor;
        return (double) Math.round(value) / factor;
    }
}
