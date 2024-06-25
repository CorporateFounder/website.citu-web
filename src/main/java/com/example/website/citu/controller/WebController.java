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
                "Preamble:\n" +
                        "This cryptocurrency aims to create a single international currency that is resistant to high volatility.\n" +
                        "Our coin was initially designed in such a way that the value of the coin increased, but decreased slightly over time.\n" +
                        "I crisis situations.\n" +
                        "\n" +
                        "The organizational structure of this coin functions as an independent corporation, where solutions to improve the cryptocurrency\n" +
                        "divided between several positions.\n" +
                        "1. The charter is mainly approved by the founder of this cryptocurrency\n" +
                        "2. Code improvements, within the framework of the charter and financing of other related projects,\n" +
                        "is carried out by a board of directors of 7 elected by the network participants.\n" +
                        "3. Implements decisions made by the board of directors, the general director elected by the board of directors.\n" +
                        "4. Controversial decisions, or the ability to veto decisions of the board of directors, are dealt with\n" +
                        "Council of Corporate Judges of 7 members.\n" +
                        "5. The Council of Corporate Judges is also elected by the network.\n" +
                        "6. Every approximately 100 seconds, a block is approved in the network.\n" +
                        "7. The reward for each block is calculated using this formula: (5+ coefficient + (difficulty * 0.2)) * multiplier\n" +
                        "- multiplier: initially in the first year it has a value of 29, but each year it decreases by one until it reaches one.\n" +
                        "- coefficient: can be 3 or 0, if your block that you are trying to add has a sum of transactions and the number of transactions is greater than in the previous block, then 3, otherwise 0.\n" +
                        "- coefficient: But transactions rewarding the founder and rewarding the miner are not taken into account.\n" +
                        "- difficulty: difficulty can be from 17 to 100, each participant sets the difficulty himself before mining.\n" +
                        "- complexity: we take the hash of the block, and count the number of ones in the hash bits, the hash must be lower than or equal to the target\n" +
                        "- goal: the goal is 100 minus the difficulty set by the miner.\n" +
                        "- mining: uses sha256 for hashing\n" +
                        "\n" +
                        "Elections: Each network member can vote for a candidate. Each participant has three ways to vote FOR, AGAINST or WITHDRAW VOTE.\n" +
                        "The vote of a network participant is equal to the amount of staking on his account. That is, if you have 100 coins reserved, you can vote for\n" +
                        "several candidates, and if you vote AGAINST (NO), then the candidate receives minus 100, if FOR (YES) plus 100.\n" +
                        "As a result, for each candidate his YES-NO rating is calculated, Thus, seven candidates for the Board of Directors,\n" +
                        "and the 7 highest ranked candidates for the Council of Corporate Judges are in office.\n" +
                        "Each network participant can change their vote at any time.\n" +
                        "Only votes cast in the last two years are counted.\n" +
                        "\n" +
                        "Board of Directors:\n" +
                        "Can approve network decisions, for example, can create a project by approving a decision where participants will voluntarily send money to an approved address.\n" +
                        "Example: Let's imagine that the board of directors has created a package, where the first line is the address and the amount separated by a space. The second line is a description, and the third is contact information.\n" +
                        "Moreover, the amount and address do not necessarily have to be in this coin, the board of directors can approve the decision in other currencies, then after the amount, separated by a space\n" +
                        "you need to register the type of usdt coin or other currency.\n" +
                        "1. rDqx8hhZRzNm6xxvL1GL5aWyYoQRKVdjEHqDo5PY2nbM 10000\n" +
                        "2. For the development of the project and the acquisition of new servers, you need to collect ten thousand coins\n" +
                        "3. citucorp.com\n" +
                        "According to the decision of the Board of Directors:\n" +
                        "Each director has a vote equal to his rating share relative to the other directors. Let's imagine that we have 7 directors,\n" +
                        "1. First, we will sum up the ratings of all these 7 directors. For example, it turned out to be 200. But director A has a rating of 40, that is, his share is 20%\n" +
                        "Therefore, for a resolution to be adopted, the resolution must receive a vote of 57% or more from the Directors.\n" +
                        "If the director has a very high rating, then he can make decisions alone; if he has a low rating, then he will have to get the support of other directors.\n" +
                        "Similarly, the Board of Directors elects the General Director.\n" +
                        "\n" +
                        "Council of Corporate Judges:\n" +
                        "The Board of Corporate Judges is elected in the same way as the Board of Directors, but it performs several functions\n" +
                        "1. Resolves disputes that arise between network participants, creating a precedent.\n" +
                        "2. May veto a decision of the Board of Directors if this decision contradicts the Charter or other current decisions.\n" +
                        "Each Judge has one vote, each Judge can vote YES, NO or abstain, as well as remove their previous vote.\n" +
                        "For each decision, the votes of the judges are calculated using the following formula: YES - NO. If the number falls below 0, then the decision is VETOed.\n" +
                        "\n" +
                        "Powers of the founder; only the founder can approve the new charter. also the property of a corporation belongs to the corporation, but to manage this property\n" +
                        "Board of Directors (Member accounts are not the property of the corporation). The Board of Directors can finance new projects by collecting money from voluntary contributions,\n" +
                        "or from sales of goods and services of the corporation, as well as from voluntary membership fees. The merger of this corporation with other corporations can only be carried out\n" +
                        "after approval by the Founder. Board of Directors, the board of directors is appointed " +
                        "for the management of this corporation within the framework of the charter.\n" +
                        "\n" +
                        "\n" +
                        "General Director:\n" +
                        "General Director, implements decisions made by the Board of Directors.\n" +
                        "\n" +
                        "\n" +
                        "1. Board of Directors (7 participants):\n" +
                        "Can approve decisions for the network, such as creating projects and raising funds for their implementation.\n" +
                        "Approves decisions by a majority vote, where the weight of the director's vote depends on his rating relative to other directors.\n" +
                        "Elects the General Director.\n" +
                        "May not act contrary to the Charter or decisions imposed by the Council of Corporate Judges.\n" +
                        "\n" +
                        "2. Council of Corporate Judges (7 participants):\n" +
                        "Resolves disputes between network participants by creating precedents.\n" +
                        "Can veto decisions of the Board of Directors if they contradict the Charter or other current decisions.\n" +
                        "Each judge has one vote, decisions are made by majority vote (YES - NO > 0).\n" +
                        "Cannot make decisions that contradict the Charter approved by the Founder.\n" +
                        "\n" +
                        "3. General Director:\n" +
                        "Implements decisions made by the Board of Directors.\n" +
                        "Does not have the authority to make independent decisions, except for the execution of decisions of the Board of Directors.\n" +
                        "It should be noted that one person can hold several elected positions at the same time, for example, be a member of the Board of Directors and the Council of Corporate Judges. However," +
                        " in this case, when voting on decisions, his votes will be counted separately for each position" +
                        " in accordance with the established rules.");
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

    @GetMapping("how_to_install_server")
    public String installServer(){
        return "how_to_install_server";
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
        model.addAttribute("noteText", "Block mining NOTE!!! THE BEING BLOCK HAS INDEX = 1, THE SAME AS THE ONE FOLLOWING IT. SO IN THIS BLOCKCHAIN \u200B\u200BTHERE ARE TWO BLOCKS WITH IDENTICAL INDICES, BUT WITH DIFFERENT CONTENT. THIS IS ABSOLUTELY NORMAL AND THIS IS THE FEATURE OF THIS BLOCKCHAIN. A unique mining system is implemented here, which stimulates a smooth increase in the value of the coin, preventing it from falling much with the onset of winter and is resistant to strong volatility, but at the same time the value increases. For mine, you need to go to localhost:8082/, multithreading is automatically turned on there and the number of threads is automatically turned on. Then go back to the menu in the top right corner and click on “Get Locked”, click on the “Start” button and then click on “International Union Corporation”, in the information window you should see “Is Mining Proper”. the update is also correct, which means that the system is loading the blockchain. Nothing will be displayed in the console, because if we display information there, mining will slow down six times for single-threaded and multi-threaded mining. If you want to turn off mining, click the “Stop” button twice, then click on the Corporation Intertation Union inscription, if everything stopped correctly, the information window will disappear. Under no circumstances should you interrupt the mining process by disabling the command line during mining or updating, as at this point the Blockchain and balance will be overwritten. When the information window goes out, mining will stop. A block is valid if the hash matches the target value according to this formula");
        model.addAttribute("instructionsText", "In the get a block tab (http://localhost:8082/mining) you can make staking or withdraw money from staking. By filling in all fields.\n" +
                "NOTE: IMPORTANT!!!!!!\n" +
                "IF YOU CALL http://localhost:8082/testCalculate in the console you will see a list of accounts, and the balance there is different. Be sure to report this\n" +
                "to the server owner, since the balance either on your wallet or on the server is not displayed correctly and he will not be able to send money.\n" +
                "look at the picture. If the server owner has discovered this problem, then he should update the resources folder and you don’t have to worry,\n" +
                "if the problem is with the server, all the owners are exceptional professionals and know their stuff.\n" +
                "P.S. (At the moment, in addition to the founder, you can also contact @suprtrupr and @caspar2114 on discord, since they are one of the best in\n" +
                "these matters and can help you when I don’t have time.)");
        model.addAttribute("importantInfoText", "In this system, the block with the most big random wins. Big random is a number that is formed from three components.\n" +
                "1. Difficulty\n" +
                "2. Random number\n" +
                "3. staking\n" +
                "\n" +
                "up to index 247867, each difficulty level gave 9 points. difficult * 9.\n" +
                "after 247867 each difficulty level gives 55 points. Difficult * 55.\n" +
                "\n" +
                "up to index 247867 hash as a seed could give a number from 0 to 55.\n" +
                "after 247867 hash as a seed, it gives a number from 0 to 135.\n" +
                "\n" +
                "up to index 247867, initial staking cost 1 coin, first point.\n" +
                "After the index 247867, initial staking costs 10 coins, the first point.\n" +
                "\n" +
                "Now in more detail: this is the formula by which a single score is calculated.\n" +
                "int result = deterministicRandom.nextInt(limit);\n" +
                "result = (int) ((int) (result + (actual.getHashCompexity() * waight)) + calculateScore(miner.getDigitalStakingBalance(), number)\n" +
                "\n" +
                "1. Difficulty.\n" +
                "Each participant can choose the difficulty from 17 to 100.\n" +
                "Each block has a hash, where the sum of the ones in bits must be equal to or lower than the target.\n" +
                "The goal is calculated as 100 - difficulty (100 minus difficulty.).\n" +
                "Each difficulty level gives 55 points. That is, if you set the difficulty to 20, then the difficulty points will be equal to 20 * 55 = 1100.\n" +
                "\n" +
                "2. Random number.\n" +
                "The hash of the block becomes the seed to generate a random number from 0 to 135.\n" +
                "\n" +
                "3. Staking.\n" +
                "Each subsequent point costs twice as much and is calculated using this formula:\n" +
                " public static long calculateScore(double x, double x0) {\n" +
                " if (x <= 0) {\n" +
                " return 0;\n" +
                " }\n" +
                " double score = Math.ceil(Math.log(x / x0) / Math.log(2));\n" +
                " return Math.min(400, (long) score);\n" +
                " }\n" +
                "\n" +
                "but this number cannot be more than 400.\n All points from the three parameters are summed up.\n" +
                "Each node (server) selects one block with the highest number of points every 100 seconds.\n" +
                "Afterwards, each node contacts random 7 nodes and compares the total\n" +
                "big random (of all blocks of a given blockchain) of your blockchain and the blockchain of another node.\n" +
                "If on another node the total big random is greater than on yours + the complexity of the last block, then your server will delete blocks up to the intersection point,\n" +
                "and will add blocks of this node. The difference between blocks must be at least 100 seconds.\nThe mining reward is calculated using the formula (5+coefficient + (difficulty * 0.2)) * multiplier. Where the multiplier is 29, but decreases by one each year until it reaches 1. The multiplier can be 0 or 3; For it to become equal to 3, two conditions must be met. 1. The sum of all transactions in the current block must be greater than in the previous block, excluding the founder's reward and the miner's reward. 2. The number of different senders in this block must be greater than in the previous block, not counting the base address from which the reward is sent to the founder and miner. http://94.87.236.238:82/winners this URL shows the last winner on this node, but does not show the winner if it was taken from another server and to see the last final winner you need /prevBlock\n" +
                "P.S. (before this index 187200, the reward was also different and the details are Mining and UtilsUse)." +
                "be sure, if you use your own server, you can configure your wallet on your server by going to the settings and writing your server there, without the slash at the end.\n");
        model.addAttribute("randomAndStakingText", "Story:\n" +
                "a special block was created on index 24281, with the help of which we created 12,000,000 coins and these coins were given to the participants, since the blockchain was broken by an attack in 40 days\n" +
                "and with the help of this address jPjuyLStHTCzwYt9J7R5M7pGUKshfcmEbtE3zVvCBE52 this money was returned to all participants who lost their coins.\n" +
                "The bug in the code that allowed this attack was also finally fixed, and now the system is completely safe.\nStory:\n" +
                "Starting from the index int V34_NEW_ALGO = 187200, the difficulty is selected by participants from 17 to 100.\n" +
                "Before this index, the complexity algorithm changed and its details are in the UtilsBlock class method difficulty.\n" +
                "Before this index, the mining algorithm also changed. Before index 151940, the mining algorithm was also different and the details are in the Block class as well as in related classes,\n" +
                "UtilsUse.\n");

        List<Map<String, Object>> scoringTable = new ArrayList<>();
        scoringTable.add(Map.of("index", 1, "value", 11));
        scoringTable.add(Map.of("index", 2, "value", 21));
        scoringTable.add(Map.of("index", 3, "value", 41));
        scoringTable.add(Map.of("index", 4, "value", 81));
        scoringTable.add(Map.of("index", 5, "value", 161));
        scoringTable.add(Map.of("index", 6, "value", 321));
        scoringTable.add(Map.of("index", 7, "value", 641));
        scoringTable.add(Map.of("index", 8, "value", 1281));
        scoringTable.add(Map.of("index", 9, "value", 2561));
        scoringTable.add(Map.of("index", 10, "value", 5121));
        scoringTable.add(Map.of("index", 11, "value", 10241));
        scoringTable.add(Map.of("index", 12, "value", 20481));
        scoringTable.add(Map.of("index", 13, "value", 40961));
        scoringTable.add(Map.of("index", 14, "value", 81921));
        scoringTable.add(Map.of("index", 15, "value", 163841));
        scoringTable.add(Map.of("index", 16, "value", 327681));
        scoringTable.add(Map.of("index", 17, "value", 655361));
        scoringTable.add(Map.of("index", 18, "value", 1310721));
        scoringTable.add(Map.of("index", 19, "value", 2621441));
        scoringTable.add(Map.of("index", 20, "value", 5242881));
        scoringTable.add(Map.of("index", 21, "value", 10485761));
        scoringTable.add(Map.of("index", 22, "value", 20971521));
        scoringTable.add(Map.of("index", 23, "value", 41943041));
        scoringTable.add(Map.of("index", 24, "value", 83886081));
        scoringTable.add(Map.of("index", 25, "value", 167772161));
        scoringTable.add(Map.of("index", 26, "value", 335544321));
        scoringTable.add(Map.of("index", 27, "value", 671088641));
        scoringTable.add(Map.of("index", 28, "value", 1342177281));
        scoringTable.add(Map.of("index", 29, "value", 2684354561L));
        scoringTable.add(Map.of("index", 30, "value", 5368709121L));
        scoringTable.add(Map.of("index", 31, "value", 10737418241L));
        scoringTable.add(Map.of("index", 32, "value", 21474836481L));
        scoringTable.add(Map.of("index", 33, "value", 42949672961L));
        scoringTable.add(Map.of("index", 34, "value", 85899345921L));
        scoringTable.add(Map.of("index", 35, "value", 171798691841L));
        scoringTable.add(Map.of("index", 36, "value", 343597383681L));
        scoringTable.add(Map.of("index", 37, "value", 687194767361l));
        scoringTable.add(Map.of("index", 38, "value", 1374389534721l));
        scoringTable.add(Map.of("index", 39, "value", 2748779069441l));
        scoringTable.add(Map.of("index", 40, "value", 5497558138881l));
        scoringTable.add(Map.of("index", 41, "value", 10995116277761l));
        scoringTable.add(Map.of("index", 42, "value", 21990232555521l));
        scoringTable.add(Map.of("index", 43, "value", 43980465111041l));
        scoringTable.add(Map.of("index", 44, "value", 87960930222081l));
        scoringTable.add(Map.of("index", 45, "value", 175921860444161l));
        scoringTable.add(Map.of("index", 46, "value", 351843720888321l));
        scoringTable.add(Map.of("index", 47, "value", 703687441776641l));
        scoringTable.add(Map.of("index", 48, "value", 1407374883553281l));
        scoringTable.add(Map.of("index", 49, "value", 2814749767106561l));
        scoringTable.add(Map.of("index", 50, "value", 5629499534213121l));
        scoringTable.add(Map.of("index", 51, "value", 11258999068426241l));
        scoringTable.add(Map.of("index", 52, "value", 22517998136852481l));
        scoringTable.add(Map.of("index", 53, "value", 45035996273704961l));
        scoringTable.add(Map.of("index", 54, "value", 90071992547409921l));
        scoringTable.add(Map.of("index", 55, "value", 180143985094819841l));
        scoringTable.add(Map.of("index", 56, "value", 360287970189639681l));
        scoringTable.add(Map.of("index", 57, "value", 720575940379279361l));
        scoringTable.add(Map.of("index", 58, "value", 1441151880758558721l));
        scoringTable.add(Map.of("index", 59, "value", 2882303761517117441l));
        scoringTable.add(Map.of("index", 60, "value", 5764607523034234881l));

        // add more entries as needed

        model.addAttribute("scoringTable", scoringTable);
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
        model.addAttribute("text1", " If you want to become a member of the board of directors or a member of the Council of Corporate Judges or a General Director, then you need to go to apply for a position, select a position, fill out all fields, then click submit. When this transaction is accepted online, find in the all laws tab the identification number of your positions, there the name of the package will correspond to your position, go to each position, and see that the first line inside is your pubkey, as soon as you found, copy the identification number of your position, it always starts with LIBER, then send it to all network participants so that they go to the tab, vote and vote for you. You can also vote for other network members, both FOR and AGAINST. You can also remove your voice. Elections: Each network member can vote for a candidate. Each participant has three ways to vote FOR, AGAINST or WITHDRAW VOTE. The vote of a network participant is equal to the amount of staking on his account. That is, if you have 100 coins reserved, you can vote for several candidates, and if you vote AGAINST (NO), then the candidate receives minus 100, if FOR (YES) plus 100. As a result, for each candidate his YES-NO rating is calculated, Thus, seven candidates for the Board of Directors, and the 7 highest ranked candidates for the Council of Corporate Judges are in office. Each network participant can change their vote at any time. Only votes cast in the last two years are counted. Board of Directors: Can approve network decisions, for example, can create a project by approving a decision where participants will voluntarily send money to an approved address. Example: Let's imagine that the board of directors has created a package, where the first line is the address and the amount separated by a space. The second line is a description, and the third is contact information. Moreover, the amount and address do not necessarily have to be in this coin, the board of directors can approve the decision in other currencies, then after the amount, separated by a space you need to register the type of usdt coin or other currency. 1. rDqx8hhZRzNm6xxvL1GL5aWyYoQRKVdjEHqDo5PY2nbM 10000 2. For the development of the project and the acquisition of new servers, you need to collect ten thousand coins 3. citucorp.com According to the decision of the Board of Directors: Each director has a vote equal to his rating share relative to the other directors. Let's imagine that we have 7 directors, 1. First, we will sum up the ratings of all these 7 directors. For example, it turned out to be 200. But director A has a rating of 40, that is, his share is 20% Therefore, for a resolution to be adopted, the resolution must receive a vote of 57% or more from the Directors. If the director has a very high rating, then he can make decisions alone; if he has a low rating, then he will have to get the support of other directors. Similarly, the Board of Directors elects the General Director. Council of Corporate Judges: The Board of Corporate Judges is elected in the same way as the Board of Directors, but it performs several functions 1. Resolves disputes that arise between network participants, creating a precedent. 2. May veto a decision of the Board of Directors if this decision contradicts the Charter or other current decisions. Each Judge has one vote, each Judge can vote YES, NO or abstain, as well as remove their previous vote. For each decision, the votes of the judges are calculated using the following formula: YES - NO. If the number falls below 0, then the decision is VETOed. Powers of the founder; only the founder can approve the new charter. also the property of a corporation belongs to the corporation, but to manage this property Board of Directors (Member accounts are not the property of the corporation). The Board of Directors can finance new projects by collecting money from voluntary contributions, or from sales of goods and services of the corporation, as well as from voluntary membership fees. The merger of this corporation with other corporations can only be carried out after approval by the Founder. Board of Directors A board of directors is appointed to govern a given corporation within the scope of its bylaws. General Director: General Director, implements decisions made by the Board of Directors. 1. Board of Directors (7 participants): Can approve decisions for the network, such as creating projects and raising funds for their implementation. Approves decisions by a majority vote, where the weight of the director's vote depends on his rating relative to other directors. Elects the General Director. May not act contrary to the Charter or decisions imposed by the Council of Corporate Judges. 2. Council of Corporate Judges (7 participants): Resolves disputes between network participants by creating precedents. Can veto decisions of the Board of Directors if they contradict the Charter or other current decisions. Each judge has one vote, decisions are made by majority vote (YES - NO > 0). Cannot make decisions that contradict the Charter approved by the Founder. 3. General Director: Implements decisions made by the Board of Directors. Has no authority have independent decisions, except for the execution of decisions of the Board of Directors. It should be noted that one person can hold several elected positions at the same time, for example, be a member of the Board of Directors and the Council of Corporate Judges. However, in this case, when voting on decisions, his votes will be counted separately for each position in accordance with the established rules.");

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
        model.addAttribute("text1", "If you want to become a member of the board of directors or a member of the Council of Corporate Judges or a General Director, then you need to go to apply for a position,\n" +
                "select a position, fill out all fields, then click submit. When this transaction is accepted online, find in the all laws tab the identification number of your\n" +
                "positions, there the name of the package will correspond to your position, go to each position, and see that the first line inside is your pubkey, as soon as you\n" +
                "found, copy the identification number of your position, it always starts with LIBER, then send it to all network participants so that they go to the tab, vote and vote for\n" +
                "you. You can also vote for other network members, both FOR and AGAINST. You can also remove your voice.\n" +
                "\n" +
                "Elections: Each network member can vote for a candidate. Each participant has three ways to vote FOR, AGAINST or WITHDRAW VOTE.\n" +
                "The vote of a network participant is equal to the amount of staking on his account. That is, if you have 100 coins reserved, you can vote for\n" +
                "several candidates, and if you vote AGAINST (NO), then the candidate receives minus 100, if FOR (YES) plus 100.\n" +
                "As a result, for each candidate his YES-NO rating is calculated, Thus, seven candidates for the Board of Directors,\n" +
                "and the 7 highest ranked candidates for the Council of Corporate Judges are in office.\n" +
                "Each network participant can change their vote at any time.\n" +
                "Only votes cast in the last two years are counted.\n" +
                "\n" +
                "Board of Directors:\n" +
                "Can approve network decisions, for example, can create a project by approving a decision where participants will voluntarily send money to an approved address.\n" +
                "Example: Let's imagine that the board of directors has created a package, where the first line is the address and the amount separated by a space. The second line is a description, and the third is contact information.\n" +
                "Moreover, the amount and address do not necessarily have to be in this coin, the board of directors can approve the decision in other currencies, then after the amount, separated by a space\n" +
                "you need to register the type of usdt coin or other currency.\n" +
                "1. rDqx8hhZRzNm6xxvL1GL5aWyYoQRKVdjEHqDo5PY2nbM 10000\n" +
                "2. For the development of the project and the acquisition of new servers, you need to collect ten thousand coins\n" +
                "3. citucorp.com\n" +
                "According to the decision of the Board of Directors:\n" +
                "Each director has a vote equal to his rating share relative to the other directors. Let's imagine that we have 7 directors,\n" +
                "1. First, we will sum up the ratings of all these 7 directors. For example, it turned out to be 200. But director A has a rating of 40, that is, his share is 20%\n" +
                "Therefore, for a resolution to be adopted, the resolution must receive a vote of 57% or more from the Directors.\n" +
                "If the director has a very high rating, then he can make decisions alone; if he has a low rating, then he will have to get the support of other directors.\n" +
                "Similarly, the Board of Directors elects the General Director.\n" +
                "\n" +
                "Council of Corporate Judges:\n" +
                "The Board of Corporate Judges is elected in the same way as the Board of Directors, but it performs several functions\n" +
                "1. Resolves disputes that arise between network participants, creating a precedent.\n" +
                "2. May veto a decision of the Board of Directors if this decision contradicts the Charter or other current decisions.\n" +
                "Each Judge has one vote, each Judge can vote YES, NO or abstain, as well as remove their previous vote.\n" +
                "For each decision, the votes of the judges are calculated using the following formula: YES - NO. If the number falls below 0, then the decision is VETOed.\n" +
                "\n" +
                "Powers of the founder; only the founder can approve the new charter. also the property of a corporation belongs to the corporation, but to manage this property\n" +
                "Board of Directors (Member accounts are not the property of the corporation). The Board of Directors can finance new projects by collecting money from voluntary contributions,\n" +
                "or from sales of goods and services of the corporation, as well as from voluntary membership fees. The merger of this corporation with other corporations can only be carried out\n" +
                "after approval by the Founder. Board of Directors A board of directors is appointed to govern a given corporation within the scope of its bylaws.\n" +
                "\n" +
                "\n" +
                "General Director:\n" +
                "General Director, implements decisions made by the Board of Directors.\n" +
                "\n" +
                "\n" +
                "1. Board of Directors (7 participants):\n" +
                "Can approve decisions for the network, such as creating projects and raising funds for their implementation.\n" +
                "Approves decisions by a majority vote, where the weight of the director's vote depends on his rating relative to other directors.\n" +
                "Elects the General Director.\n" +
                "May not act contrary to the Charter or decisions imposed by the Council of Corporate Judges.\n" +
                "\n" +
                "2. Council of Corporate Judges (7 participants):\n" +
                "Resolves disputes between network participants by creating precedents.\n" +
                "Can veto decisions of the Board of Directors if they contradict the Charter or other current decisions.\n" +
                "Each judge has one vote, decisions are made by majority vote (YES - NO > 0).\n" +
                "Cannot make decisions that contradict the Charter approved by the Founder.\n" +
                "\n" +
                "3. General Director:\n" +
                "Implements decisions made by the Board of Directors.\n" +
                "Has no authority have independent decisions, except for the execution of decisions of the Board of Directors.\n" +
                "It should be noted that one person can hold several elected positions at the same time, for example, be a member of the Board of Directors and the Council of Corporate Judges. However, in this case, when voting on decisions, his votes will be counted separately for each position in accordance with the established rules.");
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

    @GetMapping("/wallet_and_node_url")
    public String wallet_and_node_url(Model model){
        model.addAttribute("title", "url for wallet and node");
        List<String> urls = new ArrayList<>();
        urls.add("use the host from which you want to receive data (official host http://194.87.236.238:82) All methods are contained in the BassisController class.\n" +
                "1. /winnerList \n" +
                "returns a list of Blocks that should be included in the tournament.\n" +
                "public String winnerList()\n");
        urls.add("2. /allwinners \n" +
                "returns a listLiteVersionWinerwho participated in the last tournament (Means that this tournament is completed).\n" +
                "public String allWinners()\n");
        urls.add("3. /bigRandomWiner\n" +
                "shows the winner of the last tournamentLiteVersionWiner\n" +
                "public String bigRandomWiner()\n");
        urls.add("4. /datashort\n" +
                "returns meta data, {\"size\":265671,\"hashCount\":3489676,\"staking\":816578.0250480324,\"transactions\":617581,\"bigRandomNumber\":56872082,\"validation\":true}\n" +
                "size: height of this node\n" +
                "hashCount: the total amount of difficulty from the entire blockchain.\n" +
                "staking: total staking amount for all blocks divided by 100000\n" +
                "bigRandomNumber: the total sum of all bigRandoms\n" +
                "validation: if true, then the blockchain is intact and has the correct order of hashes and blocks (but this does not mean that the blockchain is completely correct and for detailed verification, you need to follow the instructions http://citucorp.com/how_to_install_server\n" +
                "public DataShortBlockchainInformation dataShortBlockchainInformation()\n");
        urls.add("5. /status \n" +
                "returns the status of the blockchain, blockFromDb and blockFromFile must match and have the same index and hash. If one of them is null or they are different, then there has been corruption either in the h2 database or in the file, then delete the corrupted version and use the fallback.\n" +
                "public String status()\n");
        urls.add("6. /allAccounts\n" +
                "returns the total number of accounts that are recorded in a given node and a given blockchain.\n" +
                "public long accounts()\n");
        urls.add("7. /totalDollars \n" +
                "returns the total dollar amount on a given node, taking into account onlydigitalDollarBalanceThus, money transferred to staking will not be taken into account, and therefore the total amount of dollars may decrease as they are transferred to staking. And similarly increase.\n" +
                "public double getTotalDollars()\n");
        urls.add("8. /totalTransactionsDay \n" +
                "returns the total number of transactions completed per day since the server was launched. Every 576 blocks, the number resets to zero.\n" +
                "public int getTotalTransactionsDays()\n");
        urls.add("9. /totalTransactionsSum \n" +
                "returns the total amount of transactions per day, since the server was launched. Every 576 blocks, the number resets to zero.\n" +
                "public double getTotalTransactionsSumDllar()\n");
        urls.add("10. /multiplier\n" +
                "this is a multiplier, described in more detail http://citucorp.com/how_to_mining\n" +
                "public long multiplier()\n");
        urls.add("11. /dayReduce \n" +
                "number of blocks left before reduction multiplierper unit.\n" +
                "http://citucorp.com/how_to_mining\n" +
                "public long daysReduce()\n");
        urls.add("12. /size \n" +
                "returns the height of the block.\n" +
                "public Integer sizeBlockchain()\n");
        urls.add("13. /sub-blocks \n" +
                "returns a list of Blocks, fromstartup to finish, inclusive finish.\n" +
                "That is, if you call start=0, finish=500. Then it will return as a list which includes block 0 and 500 too.\n" +
                "public List<Block> subBlocks(@RequestBody SubBlockchainEntity entity)\n");
        urls.add("14. /version \n" +
                "returns the version, if the version of the wallet and the server is different, then the server does not\n" +
                "will accept its blocks (practically not used in version 201)\n" +
                "public double version()\n");
        urls.add("15. /block \n" +
                "returns the Block type by index.\n" +
                "@Async(\"threadPoolTaskExecutor\")public Block getBlock(@RequestBody Integer index)\n" +
                "\n");
        urls.add("16. /isSaveFile \n" +
                "used for the interior, blocks taking blocks,\n" +
                "during dubbing. It returns the status of whether blocking is currently occurring or not.\n" +
                "public boolean isSaveFile()\n");
        urls.add("17. /balance \n" +
                "returns the balances of a given account\n" +
                "public Account getBalance(@RequestParam String address)\n" +
                "accepts pubkey as input\n" +
                "public Account getBalance(@RequestParam String address)\n");
        urls.add("18. /prevBlock \n" +
                "returns the last block that is in the blockchain,\n" +
                "may also match the last winner of the tournament.\n" +
                "public Block getPrevBlock()\n");
        urls.add("19. /nodes/resolve_from_to_block \n" +
                "adds a block or blocks to the tournament\n" +
                "after verification, and also distributes this block to other nodes.\n" +
                "public synchronized ResponseEntity<String> resolve_conflict(@RequestBody SendBlocksEndInfo sendBlocksEndInfo)\n");
        urls.add("20. /difficultyBlockchain \n" +
                "returns difficulty information, returns as\n" +
                "total and final complexity.\n" +
                "public InfoDificultyBlockchain dificultyBlockchain()\n");
        urls.add("21. /senderTransactions \n" +
                "returns a list of transactions,\n" +
                "from and to the sender. That is, all transactions where this address is sent to senders\n" +
                "for a certain period.\n" +
                "public List<DtoTransaction> senderTransactions\n");
        urls.add("22. /customerTransactions \n" +
                "similar/senderTransactions, with that\n" +
                "The only difference is where the address is already the recipient.\n" +
                "public List<DtoTransaction> customerTransactions\n");
        urls.add("23. /senderCountDto \n" +
                "returns the number of transactions sent,\n" +
                "from this address.\n" +
                "public long countSenderTransaction( @RequestParam String address)\n" +
                "\n");
        urls.add("24. /customerCountDto \n" +
                "similar/senderCountDtobut returns quantity\n" +
                "transactions where this account was the recipient.\n" +
                "public long countCustomerTransaction( @RequestParam String address)\n" +
                "\n");
        urls.add("25. /addresses \n" +
                "returns the entire list of addresses\n" +
                "public Map<String, Account> addresses()\n");
        urls.add("26./getNodes \n" +
                "returns a list of nodes\n" +
                "public Set<String> getAllNodes()\n");
        urls.add("Controller from classConductorController\n" +
                "1./account\n" +
                "Returns the balances of a given account\n" +
                "public Account account(@RequestParam String address)\n");
        urls.add("2./dollar\n" +
                "Returns the dollar balance of a given account\n" +
                "public Double dollar(@RequestParam String address)\n");
        urls.add("3./stock\n" +
                "Returns the stock balance of a given account\n" +
                "public Double stock(@RequestParam String address)\n");
        urls.add("4./isTransactionAdd\n" +
                "Returns the result whether the given signature has been added to the blockchain or not\n" +
                "public Boolean isTransactionGet(@RequestParam String sign)\n");
        urls.add("5./blockHash\n" +
                "Returns Block based on the hash of the block, thereby making it clear whether this block exists in this\n" +
                "blockchain.\n" +
                "public Block blockFromHash(@RequestParam String hash)\n");
        urls.add("6./conductorBlock\n" +
                "Returns a block by index, which is often used to understand whether a given block has been added to the blockchain.\n" +
                "\n" +
                "public Block block(@RequestParam Integer index)\n");
        urls.add("7./conductorHashTran\n" +
                "Returns the transaction by hash.\n" +
                "public DtoTransaction transaction(@RequestParam String hash)\n");
        urls.add("Controller in classNodesController\n" +
                "1./putNode\n" +
                "adds a host and port to this server./putNode\n" +
                "public void addNode(@RequestBody MyHost host)\n");
        urls.add("Controller in classTransactionController\n" +
                "1./addTransaction\n" +
                "Adds a transaction to the list of transactions waiting to be added to the blockchain.\n" +
                "public void add(@RequestBody DtoTransaction data) throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeySpecException, NoSuchProviderException, InvalidKeyException\n");
        urls.add("2./getTransactions\n" +
                "Returns a list of transactions to be added to\n");
        urls.add("Wallet controllers that are accessed through localhost:8082" +
                "The methods below are in the BasisController class\n");
        urls.add("1. http://localhost:8082/getNodes\n" +
                "returns a list of hosts that are stored on the wallet.\n" +
                "public Set<String> getAllNodes()\n");
        urls.add("2. http://localhost:8082/chain \n" +
                "returns the size of the blockchain all blocks. PS IT IS NOT RECOMMENDED TO USE AS THE SIZE OF THE BLOCKS CAN BE BIGGER THAN THE RAM MEMORY AND THIS CAN FAIL THE CHAIN.\n" +
                "public EntityChain full_chain()\n");
        urls.add("3. http://localhost:8082/size\n" +
                "returns the height of the local blockchain.\n" +
                "public Integer sizeBlockchain()\n");
        urls.add("4. http://localhost:8082/sub-blocks" +
                "returns a list of blocks from start to finish, but no more than 500 blocks at a time.\n" +
                "Also finish is included in the list.\n" +
                "public List<Block> subBlocks(@RequestBody SubBlockchainEntity entity)\n");
        urls.add("5. http://localhost:8082/nodes/resolve" +
                "configured for a specific server and either downloads missing blocks from there, or\n" +
                "deletes only blocks to synchronize your blockchain with the server.\n" +
                "public synchronized int resolve_conflicts()\n");
        urls.add("6. http://localhost:8082/resolving" +
                "causespublic synchronized int resolve_conflicts()\n" +
                "and similar to it.\n" +
                "public String resolving()\n");
        urls.add("7. http://localhost:8082/addBlock" +
                "OUTDATED AND NOT USED, STRICTLY DO NOT CALL SO AS NOT TO BREAK THE BLOCKCHAIN.\n" +
                "Previously used to restore balance and other meta data from a blockchain file,\n" +
                "but after implementing the h2 database, it does not work correctly.\n" +
                "public ResponseEntity getBLock()\n");
        urls.add("8. http://localhost:8082/miningblock http://localhost:8082/process-mining\n" +
                "Mines one block, calls a method that mines the block only once.\n" +
                "public synchronized ResponseEntity minings()\n" +
                "public synchronized String processMining(Model model, Integer number)\n");
        urls.add("9. http://localhost:8082/nodes/register\n" +
                "registers the host in the wallet.\n" +
                "public static synchronized void register_node(@RequestBody AddressUrl urlAddrress)\n");
        urls.add("10. http://localhost:8082/moreMining" +
                "continues mining for 2000 attempts.\n" +
                "public void moreMining()\n" +
                "mainly used for testing\n");
        urls.add("11. http://localhost:8082/constantMining" +
                "when you mine through the get a block tab, this is what is called.\n" +
                "Mining continues until the stop button is pressed.\n" +
                "public String alwaysMining() throws JSONException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, NoSuchProviderException, InvalidKeyException, CloneNotSupportedException\n" +
                "PS All mining methods call this method inside their methods.\n" +
                "public synchronized String mining()\n");
        urls.add("12. http://localhost:8082/stopMining" +
                "stops mining in the method http://localhost:8082/constantMining\n" +
                "public String stopMining(RedirectAttributes model)\n");
        urls.add("13. http://localhost:8082/testCalculate\n" +
                "used to check the integrity of the balance on the wallet and server.\n" +
                "If everything is correct, then you should not see a list of Accounts in the console\n" +
                "whose balance is different from the server.\n" +
                "If this still appears, perhaps the server or wallet has a damaged file\n" +
                "details are described herehttp://citucorp.com/how_to_install_server\n" +
                "public String testResolving()\n");
        urls.add("14. http://localhost:8082/status" +
                "checks the status of whether the blocks are written correctly; if the block recorded in the file and in the database are different or one of them is null, then perhaps you should replace it with a backup version or download it from scratch.\n" +
                "\n" +
                "public String status()\n");
        urls.add("Class in the walletpublic class BlockchainCheckController\n" +
                "controllers.\n 1.http://localhost:8082/checkValidation\n" +
                "checks the integrity of the blockchain from files located in the blockchain folder\n" +
                "public boolean checkValidation()\n");
        urls.add("In a wallet in class public class ConductorControlle\n" +
                "1.http://localhost:8082HYPERLINK \"http://localhost:8082/status\"/keys\n" +
                "Creates a new pair, public key, private key.\n" +
                "public Map<String, String> keys()\n");
        urls.add("2.http://localhost:8082/updating\n" +
                "Updates the local blockchain on the wallet.\n" +
                "public Integer updating()\n");
        urls.add("3.http://localhost:8082/account\n" +
                "Gets the full balance from a given wallet, for a given address.\n" +
                "public Account account(@RequestParam String address)\n");
        urls.add("4.http://localhost:8082/dollar\n" +
                "Returns the dollar balance for a given address.\n" +
                "public Double dollar(@RequestParam String address)\n");

        urls.add("5.http://localhost:8082/stock\n" +
                "Returns the stock balance for a given address.\n" +
                "public Double stock(@RequestParam String address)\n");
        urls.add("6.http://localhost:8082/sendCoin\n" +
                "This method takes as input the address of the sender, the recipient, the amount that\n" +
                "we want to transfer in dollars and shares, also the miner's reward (at the moment,\n" +
                "the mechanism to reward the miner for the transaction and the password are disabled.\n" +
                "The method locally signs and creates a transaction using this data and already\n" +
                "then sends it to 7 random nodes, calling them/addTransaction\n" +
                "public String send(@RequestParam String sender, @RequestParam String recipient, @RequestParam Double dollar, @RequestParam Double stock, @RequestParam Double reward, @RequestParam String password)\n");
        urls.add("7.http://localhost:8082/isTransactionAdd\n" +
                "Determines by signature whether a given transaction has been added\n" +
                "to the local blockchain or not.\n" +
                "public Boolean isTransactionGet(@RequestParam String sign)\n");
        urls.add("8.http://localhost:8082/blockHash\n" +
                "By block hash, returns a block if this block is in the local blockchain.\n" +
                "public Block blockFromHash(@RequestParam String hash)\n");
        urls.add("9.http://localhost:8082/conductorBlock\n" +
                "Returns a block by its index if this index with the block is in the local\n" +
                "blockchain.\n" +
                "public Block block(@RequestParam Integer index)\n");
        urls.add("10.http://localhost:8082/conductorHashTran\n" +
                "Returns the transaction by hash (not the signature, but the hash)\n" +
                "public DtoTransaction transaction(@RequestParam String hash)\n");
        urls.add("Methods in classMineController\n" +
                "1.http://localhost:8082/staking\n" +
                "Adds dollars to staking.\n" +
                "at the entrance your address, the amount you want to transfer to staking\n" +
                "and your password. It locally creates a transaction object and signs\n" +
                "it, after which it already sends this object to 7 random nodes.\n" +
                "public String staking(@RequestParam String miner, Double dollar, String password, RedirectAttributes redirectAttrs)\n");
        urls.add("2.http://localhost:8082/unstaking\n" +
                "Similar to the /staking method, only it removes your money from staking and returns it\n" +
                "into the dollar balance.\n" +
                "public String unstaking(@RequestParam String miner, Double dollar, String password, RedirectAttributes redirectAttrs)\n" +
                "\n" +
                "\n");
        urls.add("More details about the transaction\n" +
                "1. A transaction object is created and initialized\n" +
                "International_Trade_Union.entity.DtoTransaction\n" +
                "2. Then toSign is called on this object\n" +
                "This is how we sign\n" +
                "PrivateKey privateKey = UtilsSecurity.privateBytToPrivateKey(base.decode(password));byte[] sign = UtilsSecurity.sign(privateKey, dtoTransaction.toSign());\n" +
                "3. Your transaction is already initialized with a signature\n" +
                "dtoTransaction.setSign(sign);\n" +
                "After which this transaction can be sent.\n" +
                "The choice of states for your transaction is like this\n" +
                "public enum VoteEnum { YES, NO, REMOVE_YOUR_VOICE, STAKING, UNSTAKING}\n" +
                "\n");
        urls.add("Classpublic class ServerController\n" +
                "1.http://localhost:8082/server\n" +
                "This is how you change on your wallet which server you want to tune into.\n" +
                "public String server(@RequestParam String host)\n");

        model.addAttribute("urls", urls);
        return "wallet_and_node_url";
    }

    @GetMapping("/charter")
    public String charter(Model model){
        model.addAttribute("title", "charter");
        return "charter";
    }
    @GetMapping("/vision")
    public String vision_of_the_future(Model model){

        return "vision";
    }
}
