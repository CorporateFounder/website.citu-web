package com.example.website.citu.controller;

import com.example.website.citu.entity.InfoDificultyBlockchain;
import com.example.website.citu.entity.SubBlockchainEntity;
import com.example.website.citu.model.Block;
import com.example.website.citu.model.DtoTransaction;
import com.example.website.citu.utils.UtilUrl;
import com.example.website.citu.utils.UtilsJson;
import com.example.website.citu.utils.UtilsUse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class WebController {
    String BASIS_ADDRESS = "faErFrDnBhfSfNnj1hYjxydKNH28cRw1PBwDQEXH3QsJ";

    public static String getResult(String url, String errorText) {
        String str = "-1";
        try {
            str = UtilUrl.readJsonFromUrl(address + url);
        } catch (NoRouteToHostException e) {
            System.out.println(errorText);
            str = "-1";
        } catch (SocketException e) {
            System.out.println(errorText);
            str = "-1";
        } catch (IOException e) {
            throw new RuntimeException(e);

        } finally {
            return str;
        }

    }

    private String uriBase = "http://localhost:8080"; // change this to your server address
    static String address = ExplorerController.getAddress();

    @GetMapping("/")
    public String mainPage(Model model) throws IOException {
        //first api size blockchain
        String sizeStr = "-1";
        sizeStr = getResult("/size", "home page you cannot connect to global server,\" +\n" +
                "                    \"you can't give size global server");

        model.addAttribute("size", sizeStr);

        //second api
        InfoDificultyBlockchain infoDificultyBlockchain = new InfoDificultyBlockchain(-1, -1);
        String difficultOneBlock = ":";
        String difficultAllBlockchain = ":";
        try {
            String json = UtilUrl.readJsonFromUrl(address + "/difficultyBlockchain");
            infoDificultyBlockchain = UtilsJson.jsonToInfoDifficulty(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String startReduce = getResult("/v28Index", "you can't give start index global server");
        String totalDollars = getResult("/totalDollars", "you can't give total dollars global server");
        String totalAccounts = getResult("/allAccounts", "you can't give total accounts global server");
        String multiplier = getResult("/multiplier", "you can't give multiplier global server");
        String daysReduce = getResult("/dayReduce", "you can't give day reduce global server");

        long size = Long.valueOf(sizeStr);

        String urlPrev = address + "/conductorBlock?index=" + (size - 1);
        String prevJson = UtilUrl.getObject(urlPrev);
        Block prev = (Block) UtilsJson.jsonToClass(prevJson, Block.class);

        int prevBlockTotalTransaction = prev.getDtoTransactions().size();
        double prevBlockTotalSum = prev.getDtoTransactions()
                .stream().mapToDouble(t->t.getDigitalDollar())
                .sum();


        String totalTransactionsJson = getResult("/totalTransactionsDay", "you can't give total transactions day global server");
        int totalTransactionsDay = Integer.valueOf(totalTransactionsJson);
        String totalTransactionsSumJson = getResult("/totalTransactionsSum", "you can't give total transactions sum global server");
        double totalTransactionsDaySum =  Double.valueOf(totalTransactionsSumJson);

        double targetTotalSum = prev.getDtoTransactions().stream()
                .filter(t->!t.getSender().equals(BASIS_ADDRESS))
                .mapToDouble(t->t.getDigitalDollar())
                .sum();
        long targetTotalUniqAddress = prev.getDtoTransactions().stream()
                .filter(t->!t.getSender().equals(BASIS_ADDRESS))
                .map(t->t.getSender())
                .distinct()
                .count();



        difficultOneBlock = Long.toString(infoDificultyBlockchain.getDiffultyOneBlock());
        difficultAllBlockchain = Long.toString(infoDificultyBlockchain.getDifficultyAllBlockchain());
        model.addAttribute("difficulty", difficultOneBlock);
        model.addAttribute("difficultyAll", difficultAllBlockchain);
        model.addAttribute("targetTotalUniqAddress", targetTotalUniqAddress);
        model.addAttribute("targetTotalSum", targetTotalSum);

        model.addAttribute("startReduce", startReduce);
        model.addAttribute("totalDollars", totalDollars);
        model.addAttribute("totalAccounts", totalAccounts);
        model.addAttribute("multiplier", multiplier);
        model.addAttribute("daysReduce", daysReduce);
        model.addAttribute("prevBlockTotalTransaction", prevBlockTotalTransaction);
        model.addAttribute("prevBlockTotalSum", prevBlockTotalSum);
        model.addAttribute("totalTransactionsDay", totalTransactionsDay);
        model.addAttribute("totalTransactionsDaySum", totalTransactionsDaySum);





        model.addAttribute("title", "Main page");
        model.addAttribute("Summary", "Summary and Benefits");
        model.addAttribute("discord", "https://discord.gg/MqkvC3SGHH");
        model.addAttribute("telegram", "https://t.me/citu_coin");
        model.addAttribute("github", "https://github.com/CorporateFounder/unitedStates_final");
        model.addAttribute("storage", "https://github.com/CorporateFounder/unitedStates_storage");
        model.addAttribute("twitter", "@citu4030");


        model.addAttribute("text",
                "Preamble\n" +
                        "Hi all.\n" +
                        "Before downloading, I would like to read the text below.\n" +
                        "My meeting was inspired by the US Constitution and the leadership of the parliamentary system.\n" +
                        "My system implements a system that allows all creators to create and approve laws through democratic procedures.\n" +
                        "   imagine a world in which the people of the United States, Mexico, Canada, or other regions elect their representatives and recommend laws.\n" +
                        "and also on the principle of democracy, but I need your participation, I am a developer and can change\n" +
                        "code to make the legal system better. We will all be like the founding fathers of digital democracy.\n" +
                        "What are its advantages?\n" +
                        "1. There are 576 blocks per day, for each block you get (5+ coefficient + (difficulty * 0.2))*multiplier. Where the initial multiplier is 29, but decreases by one every year, but cannot be lower than one. The factor can be zero or 3. If you are blocking this data above, select the unique sender in the previous block:\n" +
                        "sender's target transaction amount in the previous block: the name of the number of unique senders, not including the base address, greater than the previous block, and subsequent all transactions, greater than the previous block, not including the founder and miner reward.\n" +
                        "2. Two unique digital dollars and digital cryptocurrency coins.\n" +
                        "3. A unique electoral system inspired by the principles of libertarianism, the parliaments of England and New Zealand and the US Constitution.\n" +
                        "4. Has a mining system built on SHA-256, the block is valid if the conditions are effectively applied.\n" +
                        "The mining algorithm is that we convert a hash into bits.\n" +
                        "and count all units in bits.\n" +
                        "The sum of units must be less than or equal to the target.\n" +
                        "Goal 100 is difficulty. that is, the complexity can only double each time. Example:\n" +
                        "   if previously the difficulty was 1, but became 2, then the probability of finding the correct hash has become half as strong.\n" +
                        "This allows us to have better control over the difficulty of the difficulty levels, up to 100 in fact.\n" +
                        "7. An electoral system that allows you to separate your representatives from different branches of government and directly vote for them.\n" +
                        "8. A unique system of government, consisting of elements of parliament and statutes of parliament, based on the constitution.\n" +
                        "USA and libertarian principles, including NAP.\n" +
                        "9. The ability to mine blocks directly on a local server, which automatically connects to a global server that only hosts storage,\n" +
                        "updates and restores the current balance.\n" +
                        "10. All settings are made taking into account knowledge in the field of macroeconomics and were taken into account in such schools as Milton Friedman’s monetarism,\n" +
                        "Austrian School of Economics (Mises, Hayek), Silvio Gesel - idle money and other books.\n" +
                        "11. The algorithm is designed in such a way that over time the economy will grow at a more sustainable and stable pace,\n" +
                        "which will prevent future crises such as deflationary spiral and stagflation, and best ensure the development of all humanity, the total number of coins in 29 years will reach a maximum of less than a billion (844635200.000000.00000000) with the fundamental fact that participants will always receive an additional payment for the coefficient (3) . The minimum amount for 29 years, if the coefficient is not received, will be about half a billion (570272000.000000.00000000)\n" +
                        "   with such an increase in the total mass annually in 29 years there will be a minimum of min 1051200.000000.00000000 one million per year and a maximum of 1681920.000000.00000000\n" +
                        "               \n" +
                        "12. Ultimately this system will be able to function as a Confederation or Federation of Humanity.\n" +
                        "and solve problems such as financial crises (deflationary spiral, stagflation, runaway inflation, etc.)\n" +
                        "Pandemics, space threats, environmental problems and international mitigation measures." +
                        "                      \n" +
                        "Mission.\n" +
                        "use an economic model that is resistant to new deflationary and inflationary crises,\n" +
                        "this means in a single democratic trading network. Our digital democracy is resistant to stuffing\n" +
                        "Parliament represents all sectors of society. First of all, we are creating a democratic platform,\n" +
                        "where there should be unique opinions of all participants. The goal is to know people of different views, different religions,\n" +
                        "regional and social groups to solve social and economic problems, as well as take into account the differences between\n" +
                        "countries and the economic growth of humanity.");
        return "main";
    }

    @GetMapping("/summary_and_benefits")
    public String summaryAndBenefits(Model model) {
        List<String> strings = new ArrayList<>();
        strings.add("Brief description of cryptocurrency\n" +
                "A unique cryptocurrency with a number of features, such as:\n" +
                "Two unique coins, a digital dollar and digital shares.\n" +
                "On average, 576 blocks are mined per day.\n" +
                "A unique mining algorithm in which a block is considered valid if it meets these conditions\n" +

                "The mining algorithm is that we convert the hash into bits and count all the units in bits." +
                " The sum of units must be less than or equal to the target. The goal is 100 - difficulty. that is, the " +
                "difficulty can only double each time. Example: if previously the complexity was 1 and became 2," +
                " then the probability of finding the correct hash has become half as much. This allows us " +
                "to better control the difficulty of the difficulty levels, up to 100 in fact." +
                "public static int getBitSum2(String hash) {\n" +
                "    int bitSum = 0;\n" +
                "    String hashUpper = hash.toUpperCase();\n" +
                "    for (int i = 0; i < hashUpper.length(); i += 2) {\n" +
                "      String hex = hashUpper.substring(i, i + 2);\n" +
                "      int hexValue = Integer.parseInt(hex, 16);\n" +
                "      while (hexValue > 0) {\n" +
                "        bitSum += hexValue & 1;\n" +
                "        hexValue >>= 1;\n" +
                "      }\n" +
                "    }\n" +
                "    return bitSum;\n" +
                "  }\n" +
                "\n" +
                "\n" +
                "\n" +
                "  public static boolean isValidHashV29(String hash, int difficulty){\n" +
                "    // Вычислить сумму битов в хэше\n" +
                "    int bitSum = getBitSum2(hash);\n" +
                "    // Проверить, меньше ли или равна сумма битов заданному уровню сложности\n" +
                "    return bitSum <= difficulty;\n" +
                "  }" +
                "A unique electoral system that allows you to choose your representatives.\n" +
                "Three branches of government: legislative, judicial and executive.\n" +
                "Parliamentary form of government.\n" +
                "\n" +
                "A unique mining system, the number of mined coins per block is equal to the formula (5+coefficient) * multiplier.\n" +
                "where the coefficient can be either zero or three if these conditions are met \"target uniq sender in prev block\" where the number of unique senders must be greater than\n" +
                "the number of unique senders in the previous block, but not counting the base address, also the sum of all transactions, not counting the founder and miner reward\n" +
                "must be higher than in the previous block, then the coefficient is 3, otherwise 0. \"target sum sender transaction in prev block\"\n" +
                "\n" +
                "\n" +
                "\n" +
                "All laws and elected positions are valid for exactly 4 years and must be re-voted every four years.\n" +
                "A unique sanctions system where participants can donate their coins to another participant who has lost the same number of coins, but this mechanism only works for shares.\n" +
                "Detailed description of each part\n" +
                "1. Two unique coins\n" +
                "For each block two types of coins are given, one coin is a dollar, the second is a share. Shares are used to vote on the election of officials and to vote on legislation.\n" +
                "2. Approximately 576 are mined per day\n" +
                "Approximately 576 blocks are mined per day, allowing for a large number of participants to engage in mining. But the quantity is not strictly fixed, and each block takes about 150 seconds to mine, and the difficulty can rise or fall depending on the mining. increased by 2.3 times or fell by 1.6 times\n" +

                "10. Laws and regulations\n" +
                "All participants can vote for both multiple participants and individuals. Each vote is counted only for the last four years. For example, you have one hundred shares, which means you have one hundred votes FOR and one hundred votes against. Example: You have 100 shares. There are 6 positions of candidates on the board A) B) C) D) E) E) F) you want to support A) and B) then by voting for them, they receive 50 votes each, the votes are divided by the number of candidates FOR Similarly, you want to vote against the remaining 4 at the same time, and each of them will receive minus 25 votes, divide the votes by the number of candidates AGAINST, which means you voted for 6 candidates, 2 of them received +50, four of them -25 .Each law, like each position, has its own hash, for which participants can vote and thus take part in voting.\n" +
                "\n" +
                "11. Sanctions\n" +
                "This system implements a sanctions mechanism; imagine that there are participants who violate the rules of the network and they are trying to use laws of a fairly radical type. Let's imagine that there are six participants with such views,\n" +
                "one centrist.\n" +
                "two left\n" +
                "two on the right\n" +
                "  one radical.\n" +
                "  Each participant has one hundred shares, so a radical may try to make a decision that all other participants do not support. And then they decide that each of them is ready to lose twenty coins, but the radical loses this number of coins. .Now imagine that each of them imposes sanctions against the radical, and all participants lose their coins, but the radical loses all his hundred shares. But other participants also lose their shares, 20 each.");


        model.addAttribute("title", "Summary and benefits");
        model.addAttribute("texts", strings);
        return "summary_and_benefits";
    }


    @GetMapping("/how_to_install")
    public String installPage(Model model) {
        model.addAttribute("title", "INSTALLATION:  how to install");
        model.addAttribute("text", "If you have windows, then you need to download from the folder target unitedStates-0.0.1-SHAPSHOT.jar\n" +
                "in the search for windows, enter cmd open the command line and enter java -jar there (where the file is located) / unitedStates-0.0.1-SNAPSHOT.jar\n" +
                "example: java -jar C://unitedStates-0.0.1-SNAPSHOT.jar.\n" +
                "\n" +
                "To work properly you need to download and install jre https://www.java.com/en/download/manual.jsp\n" +
                "https://www.oracle.com/cis/java/technologies/downloads/,\n" +
                "and jdk 19 or higher\n" +
                "\n" +
                "after launch jar, the resources folder will be automatically created where windows, then \n" +
                "go to localhost:8082 go down push button update blockchain\n" +
                "\n" +
                "the resources folder is in src/main/java/resources\n" +
                "there are stored\n" +
                "1. blockchain files in the /blockchain folder\n" +
                "2. balance files in the folder /balance\n" +
                "3. rules files voted for with their votes /allLawsWithBalance\n" +
                "4.files all rules without votes in /federalLaws\n" +
                "5. account files that have been elected as guide /federalLaws\n" +
                "6. file storing miner account /minerAccount\n" +
                "7. host address files /poolAddress\n" +
                "8. files sent by transaction /sentTransaction\n" +
                "9. transaction list files to send /transactions");
        return "how_to_install";
    }

    @GetMapping("/how_to_open_an_account")
    public String howToOpenAnAccount(Model model) {
        model.addAttribute("title", "How to open an account");
        model.addAttribute("text1", "Once the server has been properly started, go to http://localhost:8082/create-account\n" +
                "There you need to copy NEW ADDRESS this is your LOGIN and PUBLIC KEY.\n" +
                "You also need to copy PASSWORD this is your PRIVATE KEY.\n" +
                "Copy your username and password and keep it in a safe place.");
        model.addAttribute("tex2", "\n" +
                "After you need to change the address of the miner, enter the settings http://localhost:8082/seting\n" +
                "\n" +
                "enter your pub-key login there, and click the button CHANGE MINER ADDRESS");

        return "how_to_open_an_account";
    }

    @GetMapping("/how_to_change_miner_account")
    public String howToChangeMinerAccount(Model model) {
        model.addAttribute("title", "How to change miner account");
        model.addAttribute("text1", "Start local server and login http://localhost:8082/seting\n" +
                "or click the settings button, enter your ADDRESS (PUBLIC KEY)\n" +
                "ENTER PUBKEY TO CHANGE MINER ADDRESS and press the button\n" +
                "CHANGE MINER ADDRESS. UtilsFileSaveRead.save() saves the new public account to a file\n" +
                "in folder: resources/minerAccount/minerAccount.txt");
        return "how_to_change_miner_account";
    }

    @GetMapping("/how_to_mining")
    public String howToMining(Model model) {
        model.addAttribute("title", "How to mining");
        model.addAttribute("text1", "Block mining\n" +
                "NOTE!!!\n" +
                "THE BEING BLOCK HAS INDEX = 1, THE SAME AS THE ONE FOLLOWING IT.\n" +
                "SO IN THIS BLOCKCHAIN THERE ARE TWO BLOCKS WITH IDENTICAL INDICES,\n" +
                "BUT WITH DIFFERENT CONTENT. THIS IS ABSOLUTELY NORMAL AND THIS IS THE FEATURE OF THIS\n" +
                "BLOCKCHAIN.\n" +
                "                \n" +
                "               \n" +
                "A unique mining system is implemented here, which stimulates a smooth increase in the value of the coin, preventing it from falling much with the onset of winter and is resistant to strong volatility, but at the same time the value increases. For mine, you need to go to localhost:8082/, multithreading is automatically turned on there and the number of threads is automatically turned on.\n" +
                "Then go back to the menu in the top right corner and click on “Get Locked”, click on the “Start” button and then click on “International Union Corporation”, in the information window you should see “Is Mining Proper”. the update is also correct, which means that the system is loading the blockchain. Nothing will be displayed in the console, because if we display information there, mining will slow down six times for single-threaded and multi-threaded mining. If you want to turn off mining, click the “Stop” button twice, then click on the Corporation Intertation Union inscription, if everything stopped correctly, the information window will disappear. Under no circumstances should you interrupt the mining process by disabling the command line during mining or updating, as at this point the Blockchain and balance will be overwritten. When the information window goes out, mining will stop. A block is valid if the hash matches the target value according to this formula:\n" +
                "//************************************************ *************************************************\n" +
                "//mining formula\n" +
                "public static BigInteger CalcultTargetV30(long complexity) {\n" +
                "                         BigInteger maxTarget = new BigInteger(Setting.MAX_TARGET_v30, 16);\n" +
                "                       return maxTarget.divide(BigInteger.valueOf(complexity));\n" +
                "\n" +
                "\n" +
                "//used to select a block\n" +
                "                  }\n" +
                "   /**You count a random number based on the hash definition and the current one and the higher the number, the higher\n" +
                "       * originality.*/\n" +
                "      /**You count a random number based on the hash definition and the current one and the higher the number, the higher\n" +
                "       * originality.*/\n" +
                "      public static int bigRandomWinner(Actual lock, account miner) {\n" +
                "          // Concatenation of two hashes\n" +
                "          String combinedHash = act.getHashBlock();\n" +
                "\n" +
                "          if (actual == null || fact.getHashBlock() == null || fact.getHashBlock().isBlank() || fact.getHashBlock().isEmpty())\n" +
                "              return 0;\n" +
                "          // Convert concatenated hashes to BigInteger\n" +
                "          BigInteger hashAsNumber = new BigInteger(combinedHash, 16);\n" +
                "          if (hashAsNumber == null) {\n" +
                "              return 0;\n" +
                "          }\n" +
                "\n" +
                "          // Using BigInteger as a seed for a deterministic random number generator\n" +
                "          Random deterministicRandom = new Random(hashAsNumber.longValue());\n" +
                "\n" +
                "          // Generate a random number depending on 0 to 130\n" +
                "          int int = 131; // Assuming the limit is the largest value + 1\n" +
                "          int result = deterministicRandom.nextInt(limit);\n" +
                "          result = (int) ((int) (result + (actual.getHashCompexity() * 3)) + CalculateScore(miner.getDigitalStakeBalance(), 0.1));\n" +
                "          return result;\n" +
                "\n" +
                "      }\n" +
                "      public static long CalculateScore(double x, double x0) {\n" +
                "          double score = Math.ceil(Math.log(x / x0) / Math.log(2));\n" +
                "          return Math.min(200, (long) count);\n" +
                "      }\n" +
                "The mining reward is calculated using the formula (5+coefficient + (difficulty * 0.2)) * multiplier. Where the multiplier is 29, but decreases by one each year until it reaches 1.\n" +
                "The coefficient can be 0 or 3; For it to become equal to 3, two conditions must be met. 1. The sum of all transactions in the current block must be greater than in the previous block,\n" +
                "excluding the founder's remuneration and the miner's remuneration. 2. The number of different senders in this block must be greater than in the previous block, not counting the base address,\n" +
                "which sends the reward to the founder and miner.\n" +
                "                \n" +
                "The reward is also affected by the difficulty that each of you can set for yourself, from 17 to 100, but not lower than 17. To increase your chances, you can add some of your money to staking in the mining menu, and you can also withdraw your money from staking. How the winner is determined:\n" +
                "Every 150 seconds, the server first selects the 100 blocks with the highest difficulty from all the blocks presented. After this, 80 blocks with the largest number of transactions per block are selected. It then selects the 60 accounts with the highest staking amount and chooses one winner at the end.\n" +
                "The winner is determined by a formula.\n" +
                "1. First, a hash block is used as a seed to generate a random number from 0 to 130 inclusive\n" +
                "2. to this number is added the number of complexity multiplied by 3.\n" +
                "3. The number from staking is added to this, and the calculated points from staking are returned.\n" +
                "that is the first point is enough for staking 0.1 coins, to get the second point you need 0.2, for the 3rd you need 0.4, etc.\n" +
                "that is, the final formula is random + (diff * 3) + public static long calculateScore(Staking, 0.1 )\n" +
                "http://94.87.236.238:82/winners\n" +
                "\n" +
                "blocks.." );


        return "how_to_mining";
    }

    @GetMapping("/how_to_transaction")
    public String howToTransaction(Model model) {
        model.addAttribute("title", "How to transaction");
        model.addAttribute("text1", "# Transaction\n" +
                "\n" +
                "## How to send a transaction\n" +
                "How to send money\n" +
                "\n" +
                "login to http://localhost:8082/\n" +
                "Enter the sender's address, recipient's address, how many digital\n" +
                "dollars you want to send, how many digital shares you want to send,\n" +
                "miner reward\n" +
                "\n" +
                "Before sending, update the local blockchain, but up-to-date.\n" +
                "Before voting, and other actions, you can update the blockchain,\n" +
                "but before the vote is not necessary, since no amount is sent.\n" +
                "Also, in order to see current positions, it is worth updating the blockchain.\n" +
                "Before mining happens automatically.\n" +
                "To do this, you need to press the button ***update blockchain*** on the main menu and at the very bottom\n");
        model.addAttribute("text2", "And enter the password, then click the send money button");
        model.addAttribute("text3", "at localhost:8082/\n" +
                "need to keep data in\n" +
                "- input address sender public key of the sender\n" +
                "- input address recipient public key of the recipient\n" +
                "- input digital dollar to send amount of digital dollars to send\n" +
                "- input digital stock to send amount of digital stock to send\n" +
                "- send reward for miner\n" +
                "\n" +
                "- input password keep private key\n" +
                "- and click send money\n" +
                "\n" +
                "## What the transaction class consists of\n" +
                "\n" +

                " src/main/java/entity/blockchain/DtoTransaction/DtoTransaction.java\n" +

                "\n" +
                "Transaction constructor.\n" +
                "- sender (sender)\n" +
                "- customer (recipient)\n" +
                "- digitalDollar (digital dollar)\n" +
                "- digitalStock (digital stocks)\n" +
                "- laws (Package of laws)\n" +
                "- bonusMiner (miner reward)\n" +
                "- VoteEnum (sender's vote, which can be YES or NO)\n" +
                "- sign (sender's signature)\n" +
                "\n" +
                "\n" +
                "checks the integrity of the transaction that the transaction was signed correctly\n" +
                "The method is in the DtoTransaction.java class");
        return "how_to_transaction";
    }

    @GetMapping("/how_to_apply_for_a_job")
    public String howToApplyForAJob(Model model) {
        model.addAttribute("title", "how to apply for a job");
        model.addAttribute("text1", "In this system there are three branches of government, " +
                "1. Legislative 2. Judicial 3. Executive. The legislative branch consists of 201 Board of Directors accounts." +
                " 2. The judiciary consists of 50 accounts of judges. " +
                "3. The executive branch consists of 1 account of the General Executive Director. " +
                "The Board of Directors and Judges are elected equally. " +
                "To do this, you must enter the “apply for a positon” tab, wait until the calculations " +
                "finish (calculate voting: index: ...) and the tab opens, then: " +
                "1. enter the first field, your public key. 2. reward for the miner to install 5 or more coins. " +
                "3. select the position you want from the tab. In the \"input address sender\" " +
                "field enter your pub key again. 4. enter your contact information in the last field. " +
                "(if you have already applied for a position once, you do not have to do it again with the same address). " +
                "5. After you must enter the “All created law packages” tab, find your position in the name of the package, " +
                "example: BOARD_OF_DIRECTORS, 6. Go to “details” and find your public key, in index 0. further, " +
                "if you find it. 7. copy \"address package law\", 8. give this address to other participants so that " +
                "they vote for you and you are elected. to do this, they must enter the \"Vote\" " +
                "tab, where in the first field they must enter their public key, and in the second " +
                "they must enter the address of the law that you copied in point 7 (in the same way, " +
                "you yourself can vote for other laws). 9. Select YES or NO from the tab. " +
                "10. Fill in the remaining data and click send. How Positions Are Elected The Board of Directors and " +
                "Judges are elected in the same manner. To elect the Board of Directors, " +
                "201 addresses from point 7 with the highest rating are selected. The rating is calculated this way. " +
                "The number of your shares is equal to the number of votes that you can give either YES or NO, " +
                "let’s say you have 100 shares, which means you can give 100 YES and 100 NO votes. For each candidate, " +
                "the counting takes place in this way, all votes are YES - NO, and thus the result is the RATING. " +
                "201 bills filed for the position of the Board of Directors and 50 bills filed for the position of a " +
                "judge are elected judges. The CEO is elected in a similar way, but his rating must also be received from " +
                "the Board of Directors, when the board of directors and judges vote, they also have their " +
                "own chambers both within their chambers and within the chambers, one score equals one vote, " +
                "so to be elected by the board of directors, your rating must be 10 or more from the chamber. " +
                "All current positions are in the Fea tured Guid. All your votes are taken into account " +
                "only for the last four years, and you can change your vote at any time. If, for example, " +
                "you vote for several participants, then your votes will be divided between them. Example: you gave YES " +
                "for 2 candidates and NO for 4 candidates. You have one hundred shares (100), so two participants will " +
                "receive 50 votes according to the formula 100 / YES, and four against whom you voted will receive minus " +
                "twenty-five (-25) according to the same formula 100 / NO. All created laws have an expiration date of " +
                "up to 4 years, and you must vote for them. You also need to take into account that all your " +
                "representatives are, in fact, delegates, since if your number of shares decreases or increases, " +
                "the number of votes also changes. More details are currently described in the GitHub readme. to " +
                "create laws Create a package law . All laws are created by the package, you also need to pay 5 or more " +
                "digital dollars to the miner and fill out the fields, then send. all current laws fall into the Current " +
                "tab and packages of laws. In order for your law to also be voted for, you need the same as for the " +
                "position in paragraph 7. You need to find the name of your package and copy the address of this law.");


        return "how_to_apply_for_a_job";

    }

    @GetMapping("/how_to_make_laws")
    public String howToMakeLaws(Model model) {
        model.addAttribute("text1", "1. Go to the CREATE LAWS PACKAGE tab.\n" +
                "2. fill in the fields, the name of the package must be filled in\n" +
                "in capital letters and if the package name consists of several words, " +
                "then they must be separated by an underscore: Example: LAW_ON_FREEDOM.\n" +
                "after you click the send button, this transaction will go to all participants " +
                "and when the miners include this transaction in the blockchain, it will go " +
                "to the ALL CREATED LAW PACKAGES tab.");
        return "how_to_make_laws";
    }

    @GetMapping("/how_to_vote_and_what_voting_types_are_there")
    public String howToVoteAndWhatVotingTypesAreThere(Model model) {
        model.addAttribute("text1", "There are three types of voting that are used here." +
                "1. ONE_VOTE (One Voice)\n" +
                "\n" +
                "When these positions are voted count as one score = one vote\n" +
                "(CORPORATE_COUNCIL_OF_REFEREES-Council of Corporate Judges,\n" +
                "GENERAL_EXECUTIVE_DIRECTOR-General Executive Director,\n" +
                "HIGH_JUDGE - Supreme Judge and Board of Shareholders).\n" +
                "Each score that starts with LIBER counts all votes FOR (VoteEnum.YES) and AGAINST (VoteEnum.NO) for it\n" +
                "further deducted from FOR - AGAINST = if the balances are above the threshold, then it becomes the current law. But if a position is elected,\n" +
                "then after that it is sorted from largest to smallest and the largest number that is described for this position is selected.\n" +
                "Recalculation of votes occurs every block.\n" +
                "\n" +
                "After voting, the vote can only be changed to the opposite one.\n" +
                "There is no limit on the number of times you can change your vote. Only those votes that are given by accounts are taken into account\n" +
                "in his position, for example, if the account ceased to be in CORPORATE_COUNCIL_OF_REFEREES, his vote as\n" +
                "CORPORATE_COUNCIL_OF_REFEREES does not count and will not count in voting. All votes are valid until the bills\n" +
                "voters are in their positions. Only those votes from which no more than\n" +
                "four years, but each participant may at any time renew their vote." +
                "" +
                "2. VOTE_STOCK (How shares are voted.)\n" +
                "How shares are voted.\n" +
                "1. The number of shares is equal to the number of votes.\n" +
                "2. Your votes are recounted every block and if you lose your shares,\n" +
                "   or increase your shares, your cast votes also change\n" +
                "   according to the number of shares.\n" +
                "3. For each law that you voted, for this law, all\n" +
                "   FOR and AGAINST and after that with FOR minus AGAINST and this is the rating of the law.\n" +
                "4. Your votes are divided separately for all the laws that you voted FOR and separately AGAINST\n" +
                "   Example: you have 100 shares and you voted FOR one candidate and for one law,\n" +
                "   you also voted AGAINST two candidates and two laws.\n" +
                "   Now each of your candidates and the law for which you voted FOR will receive 50 votes.\n" +
                "   and for which you voted AGAINST will receive 25 votes AGAINST.\n" +
                "   the formula is simple FOR/number of laws and AGAINST/number of laws you are against." +
                "" +
                "3. FAVORITE_FRACTION\n" +
                "The faction is extracted like the chief judges, 200 scores received by the maximum number of votes\n" +
                "from a unique electoral one, as previously and an observed share equal to one vote of the described\n" +
                "in VOTE_STOCK\n" +
                "\n" +
                "#VOTE_FRACTION\n" +
                "This voting system is used only for factions.\n" +
                "First, 200 factions are selected that have become legitimate.\n" +
                "Then all the votes given to 200 selected factions are summed up.\n" +
                "After that, the share of each fraction from the total amount is determined.\n" +
                "votes cast for this faction.\n" +
                "The number of votes of each faction is equal to its percentage shares.\n" +
                "Thus, if a faction has 23% of the votes of all votes, out of\n" +
                "200 factions, then her vote is equal to 23%.\n" +
                "On behalf of the factions, the leaders always act and because of this it is\n" +
                "First of all, the leader system. Identical factions with ideological\n" +
                "system here can be represented by different leaders, even\n" +
                "if they are from the same community.\n" +
                "\n" +
                "Then every time a faction votes for laws,\n" +
                "that start with LIBER (VoteEnum.YES) or (VoteEnum.NO).\n" +
                "This law counts all the votes given *** for ***\n" +
                "and *** against ***, after which it is subtracted from *** for *** - *** against ***.\n" +
                "This result is displayed as a percentage.");

        model.addAttribute("text2", "to vote you have to do a few things.\n" +
                "1. First you must go to the tab of all created law packages.\n" +
                "2. see the details of this law, if it is a network rule, there will be a list of laws inside the package.\n" +
                "3. if this is a candidate, then the first line inside the packet will be the address of the candidate.\n" +
                "4. copy the address of the law, it always starts with LIBER\n" +
                "5. enter the vote tab.\n" +
                "6. Enter your address in the first line.\n" +
                "7. to the second address of the law,\n" +
                "8. enter the amount of remuneration to the earner in digital dollars. Choose your vote YES or NO and click vote.");

        model.addAttribute("text3", "1. If you are an independent member and your account is not an elected position, then your vote will be counted according to the second type.\n" +
                "2. If you are a judge and you vote for chief judge, then your vote will be counted according to the first type only if you vote for chief judge, as a judge.\n" +
                "3. if you are a faction and you vote for laws or the CEO, your vote will be counted by\n" +
                "the third type, but if you do not vote for candidates of other positions.\n" +
                "If you are a member of the Board of Shareholders and vote for amendments, then you vote by type 1.\n" +
                "\n" +
                "1. how the faction is elected. The 200 candidates who received the highest number of share rankings become factions.\n" +
                "2. how judges are elected. The 55 candidates who receive the most votes in the ranking of the shares become judges.\n" +
                "3. How the Chief Justice is elected. 1 candidate who received the most votes of the share rating and the most (more than 2 votes) the number of ratings from the votes of the judges, becomes the supreme judge.\n" +
                "4. how the CEO is elected.\n" +
                "The 1 candidate who receives the most share ranking votes and the most faction ranking votes (more than 15% of the rating) becomes the Executive CEO.\n" +
                "5. How laws are elected, any package of laws must receive more than 1 rating from the number of votes of shares and a rating from the votes of factions above 15% percent, then it is valid.");

        return "how_to_vote_and_what_voting_types_are_there";
    }

    @GetMapping("/solving_common_problems")
    public String solvingCommonProblems(Model model) {
        model.addAttribute("titile", "solving common problems");
        List<String> list = new ArrayList<>();
        list.add("1. Problem with the port.\n" +
                "If you see this error on the command line, then take this port.\n" +
                "This problem occurs if you are on the same computer twice without closing the previous\n" +
                "command line trying to run the program. You need to restart your computer.\n" +
                "***************************\n" +
                "APPLICATION FAILED TO START\n" +
                "***************************\n" +
                "Description:\n" +
                "Web server failed to start. Port 8082 was already in use.\n" +
                "action:\n" +
                "Identify and stop the process that's listening on port 8082 or configure this application to listen on another port.\n");
        list.add("2. Sometimes the balance display disappears.\n" +
                "This occurs when, for some reason, the blockchain is incorrectly recorded.\n" +
                "The actual blockchain is always stored in the global server.\n" +
                "And to restore your balance, it is enough to update the blockchain on the main page.\n");
        list.add("3. Your local blockchain outperforms the global one and then gets deleted and part of the balance is lost.\n" +
                "The system after each finding of the block tries to connect to the global network for one minute,\n" +
                "transfer the actual blockchain there. If, for example, there are 20 blocks on the global server,\n" +
                "and you are trying to add N blocks, but their index continues the global blockchain. 21, 22, .... etc.\n" +
                "Then when your wallet can connect, your block will be added.\n" +
                "If your branch is different from the global one and perhaps someone has already added 21 blocks, then your\n" +
                "blocks are removed and your balance is lost because of this. How so your balance contained not up-to-date\n" +
                "blockchain.");

        model.addAttribute("list", list);

        return "solving_common_problems";
    }

//    @GetMapping("/conductor")
//    public String conductors(
//            Model model){
//
//
//        return "conductor";
//    }

}
