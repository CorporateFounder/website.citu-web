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
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebController {


    private String uriBase = "http://localhost:8080"; // change this to your server address
    String address = ExplorerController.getAddress();

    @GetMapping("/")
    public String mainPage(Model model){
        //first api size blockchain
        String sizeStr = "-1";
        try {
            sizeStr = UtilUrl.readJsonFromUrl(address + "/size");
        }catch (NoRouteToHostException e){
            System.out.println("home page you cannot connect to global server," +
                    "you can't give size global server");
            sizeStr = "-1";
        }catch (SocketException e){
            System.out.println("home page you cannot connect to global server," +
                    "you can't give size global server");
            sizeStr = "-1";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("size", sizeStr);

        //second api
        InfoDificultyBlockchain infoDificultyBlockchain = new InfoDificultyBlockchain(-1, -1);
        String difficultOneBlock =  ":";
        String difficultAllBlockchain = ":";
        try {
            String json = UtilUrl.readJsonFromUrl(address+ "/difficultyBlockchain");
            infoDificultyBlockchain = UtilsJson.jsonToInfoDifficulty(json);

        }catch (NoRouteToHostException e){
            System.out.println("home page you cannot connect to global server," +
                    "you can't give difficulty global server");

        }catch (SocketException e){
            System.out.println("," +
                    "you can't give difficulty global server");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        difficultOneBlock = Long.toString(infoDificultyBlockchain.getDiffultyOneBlock());
        difficultAllBlockchain = Long.toString(infoDificultyBlockchain.getDifficultyAllBlockchain());
        model.addAttribute("difficulty", difficultOneBlock);
        model.addAttribute("difficultyAll", difficultAllBlockchain);

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
        "Before downloading, I would like you to read the text below.\n" +
        "My cryptocurrency was inspired by the US Constitution and the parliamentary system of government.\n" +
        "My system implements a system that allows all participants to create laws and approve laws through democratic procedures.\n" +
        "Imagine a world where residents of the USA, Mexico, Canada or other regions elect their representatives and approve laws,\n" +
        "and also through direct democracy, but I need you to participate, I am a developer and can modify\n" +
        "code so that the legal system becomes better. We will all be like the founding fathers of digital democracy.\n" +
        "\n" +
        "What are its advantages.\n" +
        "1. There are 576 blocks in a day, for each block it is given if the block index is even (difficulty * 30) coins, if not even\n" +
        "   (difficulty * 30) + 1 coin. where difficulty is equal to the number of zeros in the hash.\n" +
        "2. Two unique digital dollar and digital stock coins.\n" +
        "3. There is no halving; instead, the limitation occurs by burning coins from all accounts in the amount\n" +
        "   0.2% every six months for digital dollars and 0.4% for digital stocks.\n" +
        "4. A unique electoral system inspired by the principles of libertarianism, the English and New Zealand parliaments and\n" +
        "   US Constitution.\n" +
        "5. It has a unique mining system built on the basis of SHA-256 (A valid block must have the number\n" +
        "   zeros in the hash string equal to the amount of complexity and the number of zeros in the hash bits must be 2).\n" +
        "\n" +
        "6. Difficulty adapts every 288 blocks.\n" +
        "7. An election system that allows you to elect your representatives to different branches of government and directly vote for them.\n" +
        "8. A unique system of government consisting of elements of the English parliament and a charter inspired by the constitution\n" +
        "   USA and libertarian principles, including NAP.\n" +
        "9. The ability to mine blocks directly on a local server, which automatically connects to the global server, which only stores,\n" +
        "   updates and returns the current blockchain.\n" +
        "10. All settings are made taking into account knowledge in the field of Macroeconomics and such schools as Milton Friedman’s Monetarism were taken into account,\n" +
        "    Austrian School of Economics (Mises, Hayek), Silvio Gesel - money with demurrage and other books.\n" +
        "11. The algorithm is designed in such a way that over time the economy will grow more steadily and have a more stable rate,\n" +
        "    which will prevent future crises such as Deflationary Spiral and Stagflation, and allow for the optimal development of all humanity.\n" +
        "    the total number of coins never exceeds 10 billion dollars and 5 billion shares unless the difficulty is higher than 10.\n" +
        "12. Ultimately this system will be able to function as a Confederation or Federation for humanity\n" +
        "    and eliminate problems such as financial crises (Deflationary Spiral, Stagflation, Galloping Inflation, etc.)\n" +
        "    Pandemics, Space Threats, Environmental problems and will reduce the occurrence of military conflicts.\n" +
        "\n" +
        "\n" +
        "Mission.\n" +
        "Using a new economic model that is resistant to deflationary and inflationary crises,\n" +
        "unite humanity into a single democratic trading network. Our digital democracy is resilient to stuffing\n" +
        "and parliament represents all sections of society. First of all, we are creating a democratic platform,\n" +
        "where the views of all participants must be heard. The goal is to unite people of different views, different religions,\n" +
        "regions and social groups to solve social and economic problems, as well as reduce conflicts between\n" +
        "countries and the economic growth of mankind.\n");
        return "main";
    }
    @GetMapping("/summary_and_benefits")
    public String summaryAndBenefits(Model model){
        List<String> strings = new ArrayList<>();
        strings.add("Brief description of cryptocurrency\n" +
                "A unique cryptocurrency that has a number of features, such as:\n" +
                "\n" +
                "Two unique coins, a digital dollar and digital shares.\n" +
                "A unique mechanism for limiting coins by burning co of all coin accounts in the amount of, 0.2% digital dollars and 0.4% digital shares every six months.\n" +
                "On average, 576 blocks are mined per day.\n" +
                "A unique mining algorithm, where the block is considered valid if the number of zeros in the hash string matches the current difficulty, and the number of zeros in the hash bits is equal to or greater than two.\n" +
                "Difficulty adapts every 288 blocks.\n" +
                "A unique electoral system that allows you to elect your representatives.\n" +
                "Three branches of government Legislative, Judicial and Executive.\n" +
                "Parliamentary Form of Government.\n" +
                "Unique Mining System the number of coins is equal to the difficulty multiplied by 30 (if the block index is even, if not even then we add +1 to this). Example: difficulty 9 a) even index 930=270, odd block index (930)+1= 271.\n" +
                "All laws and elected positions are valid for exactly 4 years and every four years you need re-vote.\n" +
                "A unique sanctions system where participants can donate their coins to another participant lost the same number of coins, but this mechanism only works for shares.\n" +
                "Detailed description of each part\n" +
                "1. Two unique coins\n" +
                "For each block two types of coins are given, one coin is a dollar, the second is a share, Shares are used in voting to elect officers, and in voting for laws.\n" +
                "\n" +
                "2. Every six months, coins are burned from all accounts\n" +
                "Every six months, coins are burned from all accounts in the amount 0.2% digital dollars and 0.4% digital stocks, which holds back inflation and allows miners to mine coins in the same quantity.\n" +
                "\n" +
                "3. Approximately 576 are mined per day\n" +
                "Approximately 576 blocks are mined per day, which allows a large number of participants engage in mining for many participants. But the quantity is not strict fixed, and each block is mined in approximately 150 seconds, and the difficulty may rise or fall if production rose 2.7 times or fell 1.6 times\n" +
                "\n" +
                "4. Unique mining algorithm\n" +
                "Unlike other cryptocurrencies, where they often use the number of zeros in bits hash, double check is used here. 1. Number of zeros, in the string, the hash of the block must match the complexity. 2. Also Each block hash must contain 2 or more zeros in the bits. This measure is made for more accurate protection against ASIC attacks.\n" +
                "\n" +
                "5. Difficulty adapts dynamically.\n" +
                "Difficulty adjustment occurs every 288 blocks, If a block is mined more than 2.7 times faster, then the difficulty increases +1, if production drops by 1.6 times, then difficulty drops to -1 if none of the conditions are met, then the difficulty remains the same\n" +
                "\n" +
                "public static int v2getAdjustedDifficultyMedian(Block latestBlock, List<Block> blocks, long BLOCK_GENERATION_INTERVAL, int DIFFICULTY_ADJUSTMENT_INTERVAL){\n" +
                "         Block prevAdjustmentBlock = blocks.get(blocks.size() - DIFFICULTY_ADJUSTMENT_INTERVAL);\n" +
                "         // Median time from index 0 to 10 of blocks\n" +
                "         List<Long> adjustmentBlockTimes = new ArrayList<>();\n" +
                "         for (int i = 0; i < Math.min(DIFFICULTY_ADJUSTMENT_INTERVAL, blocks.size()); i++) {\n" +
                "             adjustmentBlockTimes.add(blocks.get(i).getTimestamp().getTime());\n" +
                "         }\n" +
                "         Collections.sort(adjustmentBlockTimes);\n" +
                "         long prevTime = adjustmentBlockTimes.get(adjustmentBlockTimes.size() / 2);\n" +
                "\n" +
                "         // Includes the latestBlock time and the last 10 indices from blocks\n" +
                "         List<Long> latestBlockTimes = new ArrayList<>();\n" +
                "         latestBlockTimes.add(latestBlock.getTimestamp().getTime());\n" +
                "         for (int i = Math.max(blocks.size() - 30, 0); i < blocks.size(); i++) {\n" +
                "             latestBlockTimes.add(blocks.get(i).getTimestamp().getTime());\n" +
                "         }\n" +
                "         Collections.sort(latestBlockTimes);\n" +
                "         long latestTime = latestBlockTimes.get(latestBlockTimes.size() / 2);\n" +
                "\n" +
                "\n" +
                "         double percentGrow = 2.7;\n" +
                "         double percentDown = 1.6;\n" +
                "\n" +
                "\n" +
                "         long timeExpected = BLOCK_GENERATION_INTERVAL * DIFFICULTY_ADJUSTMENT_INTERVAL;\n" +
                "         long timeTaken = latestTime - prevTime;\n" +
                "\n" +
                "         if(timeTaken < timeExpected / percentGrow){\n" +
                "             return prevAdjustmentBlock.getHashCompexity() + 1;\n" +
                "         }else if(timeTaken > timeExpected * percentDown){\n" +
                "             return prevAdjustmentBlock.getHashCompexity() - 1;\n" +
                "         }else {\n" +
                "             return prevAdjustmentBlock.getHashCompexity();\n" +
                "         }\n" +
                "     }\n" +
                "6, 7, 8. Electoral system and branches of government.\n" +
                "For the electoral system of its representatives, shares are used. One share gives the right to vote one FOR and one AGAINST. Each participant can give your votes both FOR and AGAINST a candidate or law. You can also split your votes equally among several participants. For each candidate, a rating is calculated, rating this is the sum of all votes FOR minus all votes AGAINST, equals RATING. All Government consists of three branches of government .\n" +
                "\n" +
                "The legislative branch consists of the Parliament of the Council of Directors. their number is 201 accounts. One count equals one vote. For each law it is calculated as the rating received from the shares, and the rating received from a member of the board of directors. The law comes into force as soon as its rating from a stock is above 1, so his rating is above a certain level.\n" +
                "The executive branch is the General Executive Director, and other leadership positions. The entire executive branch is appointed Board of Directors.\n" +
                "The judiciary consists of 50 judges also elected by the community.\n" +
                "To elect the Board of Directors, 201 accounts with the largest rating, but each account can only submit itself for this position.\n" +
                "\n" +
                "At any time, any participant can change their voice to the opposite one. Any voice is only valid for 4 years and needs to be renewed again, so that he continues to act.\n" +
                "\n" +
                "9. Unique mining system.\n" +
                "There are currently only two types of coin mining in the world,\n" +
                "\n" +
                "Bitcoin and its successors, which reduce production every four year or other period.\n" +
                "dogecoin and its successors, which have a fixed number of coins.\n" +
                "This system has a unique production system, where production is determined from complexity, which makes it possible to regulate emissions and attract more investment. The number of coins for even blocks is equal to the difficulty multiplied by 30, for odd numbers (difficulty multiplied by 30) + 1. Thus, in even blocks, for example, if the difficulty is 9, it will be equal to 930=270, when the block index is not even (930)+1 = 271. Why is such a measure needed? To understand why it is needed, you first need to understand the disadvantages other coins.\n" +
                "\n" +
                "What disadvantages do type 1 coins have?\n" +
                "First of all, this reduces the incentive to invest in the coin, since if mining profitability falls, then in order to remain profitable the cost should increase in proportion to the reduction. Now I’ll explain in more detail: the current price of Bitcoin (10/23/2023) is equal to 30571.90, while the production per block is 6.25. Now let's imagine that Every four years production decreases,\n" +
                "\n" +
                "award 2024 3.125, cost-effective value 61143.8\n" +
                "award 2028 1.5625 cost effective 122287.6\n" +
                "award 2032 0.78125 cost effective 244575.2\n" +
                "award 2036 0.390625 cost effective 489150.4\n" +
                "award 2040 0.1953125 cost effective 978300.8\n" +
                "Thus, it is clear that after each extraction, to remain cost-effective the cost must double or they will need cut costs in half after every change. This coin will contribute to the monopolization of the system and degradation due to reduced investment. Since in some moment, production will become absolutely unprofitable, and it will be engage only large coin holders. Due to the reduction in production, the incentive to invest is reduced both for large holders, as well as new players.\n" +
                "\n" +
                "What disadvantages do type 2 coins have?\n" +
                "There are advantages to having a fixed amount of loot, but there are some disadvantages. Both excess inflation and oligopoly and stagnation. Since the number of coins is fixed, then at some point investing in equipment becomes not profitable. Due to increasing complexity, the cost increases costs, which will often lead to unspoken collusion when large participants will not invest and as a result society will not develop. But this also has a problem with inflation, because when demand falls, so does complexity falls more often, but the number of mined coins remains the same. But in fairness it must be said that the second coin more stable than the first and less subject to volatility.\n" +
                "\n" +
                "Our coin.\n" +
                "In this coin we give an additional reward for increasing difficulties, which in turn gives an incentive to participants to finance into the equipment. We also determine demand depending on complexity, the higher the complexity, the higher the demand, but if demand falls, so does the complexity also falls, which leads to a decrease in production and reduces inflation. And the difference between the income of even and odd blocks stimulates further violate the conspiracy. Since no one wants to give to others players have more advantage. This mechanism also allows you to better regulate the exchange rate of the coin.\n" +
                "\n" +
                "10. Laws and Positions\n" +
                "All participants can vote for both several participants and for individuals. Each vote is counted only for the last four years. For example, you have one hundred shares, which means you have one hundred votes FOR and one hundred votes against. Example: You have 100 shares. There are 6 Candidates board positions A) B) C) D) E) F) you want to support A) and C) then by voting for them they receive 50 votes each, votes divided by the number of candidates FOR. Similar you want to vote against the remaining 4 at the same time, and each of them will receive minus 25 votes, votes divided by the number candidates AGAINST, so you voted for 6 candidates, 2 of them received +50, four of them -25. Every law, like every position, has its own oh hash, for which the participants can cast votes and thus take part in the vote.\n" +
                "\n" +
                "11. Sanctions\n" +
                "This system implements a sanctions mechanism, imagine that there are participants who violate the rules of the network and they are trying to use laws of a fairly radical type. Let's imagine there are six participants with such views,\n" +
                "\n" +
                "one centrist.\n" +
                "two left\n" +
                "two right\n" +
                "one radical.\n" +
                "Each participant has one hundred shares, thus a radical may try to make a decision that everyone else participants are not supportive. And then they decide that each of them they are ready to lose twenty coins, but the radical also loses this number of coins. Now imagine that each of them imposes sanctions, against the radical, and all participants lose their coins, but the radical thus the radical loses all his hundred shares. But others too participants also lose their shares, 20 each.\n" +
                "\n" +
                "What does this mean for the whole society.\n" +
                "This coin stimulates investment by burning coins, which in turn reduces unemployment, as well as poverty, as well as the exchange rate becomes stable to fluctuations. Also index indicators ginnies will provide a more equitable distribution of income, and help reduce the stratification of society.");


        model.addAttribute("title", "Summary and benefits");
        model.addAttribute("texts", strings);
        return "summary_and_benefits";
    }


    @GetMapping("/how_to_install")
    public String installPage(Model model){
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
    public String howToOpenAnAccount(Model model){
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
    public String howToChangeMinerAccount(Model model){
        model.addAttribute("title", "How to change miner account");
        model.addAttribute("text1", "Start local server and login http://localhost:8082/seting\n" +
                "or click the settings button, enter your ADDRESS (PUBLIC KEY)\n" +
                "ENTER PUBKEY TO CHANGE MINER ADDRESS and press the button\n" +
                "CHANGE MINER ADDRESS. UtilsFileSaveRead.save() saves the new public account to a file\n" +
                "in folder: resources/minerAccount/minerAccount.txt");
        return "how_to_change_miner_account";
    }

    @GetMapping("/how_to_mining")
    public String howToMining(Model model){
        model.addAttribute("title", "How to mining");
        model.addAttribute("text1", "Block mining\n" +
                "\n" +
                "HOW TO START MINING\n" +
                "Before you start mining blocks, you\n" +
                "you need to set the address of the miner to which the block will be mined.\n" +
                "Once you have set your address as a miner, there are two options.\n" +
                "\n" +
                "OPTION 1.\n" +
                "To start mining, after launch, go to\n" +
                "there will be a button on http://localhost:8082/miningblock. START MINING\n" +
                "clicking on it will automatically produce a block.");

        model.addAttribute("text2", "### OPTION 2\n" +
                "push button ***Constant mining 576 block in while***\n" +
                "there will be a cycle of 576 attempts to find blocks\n" +
                "\n" +
                "\n" +
                "OPTION 3.\n" +
                "calling http://localhost:8082/mine automatically starts mining.\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "Blockchain complexity adapts similarly to bitcoin, but adaptation happens\n" +
                "once every half day.\n" +
                "Each block gives 400 digital dollars and 400 digital shares\n" +
                "\n" +
                "The current blockchain is not only the longest blockchain, but it should also have more zeros.\n" +
                "\n" +
                "this method counts the number of zeros in the blockchain and the current blockchain, not only the longest, but also the one with the most zeros\n");

        return "how_to_mining";
    }

    @GetMapping("/how_to_transaction")
    public String howToTransaction(Model model){
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
    public String howToApplyForAJob(Model model){
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
    public String howToMakeLaws(Model model){
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
    public String howToVoteAndWhatVotingTypesAreThere(Model model){
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
    public String solvingCommonProblems(Model model){
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
