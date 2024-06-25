package com.example.website.citu.controller;

import com.example.website.citu.model.LiteVersionWiner;
import com.example.website.citu.utils.UtilsJson;
import com.example.website.citu.utils.UtilsUse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WinnersController {
    @GetMapping("/winners")
    public String winners(Model model) throws JsonProcessingException {
        String allWiners = WebController.getResult("/allwinners", "error allWiners");
        List<LiteVersionWiner> allWinersList = UtilsJson.jsonToLiteVersionWiners(allWiners);
        model.addAttribute("allWiners", allWinersList);

        int uniq = allWinersList.stream().filter(UtilsUse.distinctByKey(LiteVersionWiner::getAddress)).collect(Collectors.toList()).size();
        model.addAttribute("uniq", uniq);
        model.addAttribute("allWinnerCount", allWinersList.size());

        String bigRandom = WebController.getResult("/bigRandomWiner", "error bigRandom");
        List<LiteVersionWiner> bigRandomList = UtilsJson.jsonToLiteVersionWiners(bigRandom);
        model.addAttribute("bigRandom", bigRandomList);
        model.addAttribute("bigRandomCount", bigRandomList.size());

        return "winners";
    }
}
