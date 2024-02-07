package com.example.website.citu.controller;

import com.example.website.citu.model.LiteVersionWiner;
import com.example.website.citu.utils.UtilsJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WinnersController {
    @GetMapping("/winners")
    public String winners(Model model) throws JsonProcessingException {
        String allWiners = WebController.getResult("/allwinners", "error allWiners");
        List<LiteVersionWiner> allWinersList = UtilsJson.jsonToLiteVersionWiners(allWiners);
        model.addAttribute("allWiners", allWinersList);
        model.addAttribute("allWinnerCount", allWinersList.size());

        String allDiff = WebController.getResult("/powerWiners", "error powerWiners");
        List<LiteVersionWiner> allDiffList = UtilsJson.jsonToLiteVersionWiners(allDiff);
        model.addAttribute("allDiff", allDiffList);
        model.addAttribute("allDiffCount", allDiffList.size());

        String allTransactions = WebController.getResult("/countTransactionsWiner", "error countTransactionsWiner");
        List<LiteVersionWiner> allTransactionsList = UtilsJson.jsonToLiteVersionWiners(allTransactions);
        model.addAttribute("allTransactions", allTransactionsList);
        model.addAttribute("allTransactionsCount", allTransactionsList.size());

        String allStaking = WebController.getResult("/stakingWiners", "error countTransactionsWiner");
        List<LiteVersionWiner> allStakingList = UtilsJson.jsonToLiteVersionWiners(allStaking);
        model.addAttribute("allStaking", allStakingList);
        model.addAttribute("allStakingCount", allStakingList.size());

        String bigRandom = WebController.getResult("/bigRandomWiner", "error bigRandom");
        List<LiteVersionWiner> bigRandomList = UtilsJson.jsonToLiteVersionWiners(bigRandom);
        model.addAttribute("bigRandom", bigRandomList);
        model.addAttribute("bigRandomCount", bigRandomList.size());

        return "winners";
    }
}
