package com.example.website.citu.controller;

import com.example.website.citu.entity.SubBlockchainEntity;
import com.example.website.citu.model.Block;
import com.example.website.citu.model.DtoTransaction;
import com.example.website.citu.utils.UtilUrl;
import com.example.website.citu.utils.UtilsJson;
import com.example.website.citu.utils.UtilsUse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class ExplorerController {

    private static boolean IS_TEST = false;
    private static String address = IS_TEST?"http://localhost:8083" : "http://194.87.236.238:82";

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        ExplorerController.address = address;
    }

    @GetMapping("/conductor")
    public String conductors(
            Model model,
            HttpSession session) throws IOException { // добавить параметр сессии

        // получить список блоков из сессии, если он существует
        List<Block> blocks = (List<Block>) session.getAttribute("blocks");

        // если список блоков пуст, то получить его из сервиса
        if (blocks == null || blocks.isEmpty()) {
            int different = 10;
            //получить список блоков
            String sizeStr = UtilUrl.readJsonFromUrl(address + "/size");
            Integer size = Integer.valueOf(sizeStr);

            int start = size-different, end = size-1;

            System.out.println("start: " + start);
            System.out.println("end: " + end);
            SubBlockchainEntity subBlockchainEntity = new SubBlockchainEntity(start, end);

            String subBlockchainJson = UtilsJson.objToStringJson(subBlockchainEntity);

            String jsonList = UtilUrl.getObject(subBlockchainJson,   address + "/sub-blocks");

            blocks = UtilsJson.jsonToListBlock(jsonList);
            blocks = blocks.stream().sorted(Comparator.comparing(Block::getIndex).reversed())
                    .collect(Collectors.toList());

            // сохранить список блоков в сессии
            session.setAttribute("blocks", blocks);
        }

        // добавить список блоков в модель
        model.addAttribute("blocks", blocks);


        return "conductor";
    }

    @PostMapping("/conductor")
    public String conductor(
            @RequestParam(value = "info", defaultValue = "0") String info,
            @RequestParam(required = false) String page,
            RedirectAttributes redirectAttrs,
            HttpSession session) throws IOException {
        int different = 10;
        //получить список блоков
        String sizeStr = UtilUrl.readJsonFromUrl(address + "/size");
        Integer size = Integer.valueOf(sizeStr);

        int start = size-different, end = size-1;
        if (page != null) {

            String[] str = page.split(":");
            System.out.println("button push: " + str[0]);
            int index = Integer.valueOf(str[1]);
            if(str[0].equals("next")){
                start = index - different;
                end = index - 1;

                System.out.println("start: next: " + start);
                System.out.println("end: next: " + end);
            }else {
                start = index + 1;
                end = index + different;

                System.out.println("start: prev: " + start);
                System.out.println("end: prev: " + end);

            }

        }
        if(start >= size){
            start = size -different;
        }
        if (end >= size){
            end = size -1;
        }

        System.out.println("start: " + start);
        System.out.println("end: " + end);
        SubBlockchainEntity subBlockchainEntity = new SubBlockchainEntity(start, end);



        String subBlockchainJson = UtilsJson.objToStringJson(subBlockchainEntity);

        String jsonList = UtilUrl.getObject(subBlockchainJson,   address + "/sub-blocks");

        List<Block> blocks = UtilsJson.jsonToListBlock(jsonList);
        blocks = blocks.stream().sorted(Comparator.comparing(Block::getIndex).reversed())
                .collect(Collectors.toList());

        // обновить список блоков в сессии
        session.setAttribute("blocks", blocks);
        redirectAttrs.addFlashAttribute("blocks", blocks);
        if(isNumeric(info)){
            String url = address +"/conductorBlock?index=" + info;

            Integer integer = Integer.valueOf(info);

            // Используем GET-запрос для получения данных по индексу
            String text = UtilUrl.getObject( url );

            if(integer == 0){
                String information = "The peculiarity of this blockchain is " +
                        "that the genesis block also has an index of 1, " +
                        "as does the block following it. This is normal," +
                        " the entire block chain is correct and each block is unique, " +
                        "with unique content. Thus, in the blockchain, " +
                        "two blocks have identical indices, but different contents. " +
                        "This is simply a feature of this blockchain.\n";


                redirectAttrs.addFlashAttribute("text", information );
            }
            Block block = (Block) UtilsJson.jsonToClass(text, Block.class);
            redirectAttrs.addFlashAttribute("block", block);

        }else {
            String url = address +"/conductorHashTran?hash="+info;
//            String url = address +"/conductorHashTran";

            String json = info;
            // Используем GET-запрос для получения данных по хешу
            String text = UtilUrl.getObject( url );

            redirectAttrs.addFlashAttribute("text", null);
            DtoTransaction dtoTransaction = (DtoTransaction) UtilsJson.jsonToClass(text, DtoTransaction.class);
            redirectAttrs.addFlashAttribute("dto", dtoTransaction);
        }

        return "redirect:/conductor";
    }
    public static boolean isNumeric(String str) {
        // Проверяем, не пустая ли строка
        if (str == null || str.isEmpty()) {
            return false;
        }
        // Создаем регулярное выражение для целых или дробных чисел
        String regex = "[-+]?\\d+(\\.\\d+)?";
        // Проверяем, соответствует ли строка регулярному выражению
        return str.matches(regex);
    }


    @GetMapping("detail-transactions")
    public String details(Model model) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, SignatureException, NoSuchProviderException, InvalidKeyException {

        return "detail-transactions";
    }






    @GetMapping("/detail-transactions/{index}")
    public String detailTransactionIndex(@PathVariable(value = "index") String index, RedirectAttributes redirectAttrs) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, SignatureException, NoSuchProviderException, InvalidKeyException {
        System.out.println("LawsController /detail-laws-all/{index}: " + index);
        redirectAttrs.addAttribute("title", "detail transactions");
        //ORIGINAL_ALL_CORPORATION_LAWS_FILE

        System.out.println("index: " + index);
        String url = address +"/conductorBlock?index=" + index;

        Integer integer = Integer.valueOf(index);

        // Используем GET-запрос для получения данных по индексу
        String text = UtilUrl.getObject( url );

        Block block = (Block) UtilsJson.jsonToClass(text, Block.class);
        List<DtoTransaction> transactions = block.getDtoTransactions();

        redirectAttrs.addFlashAttribute("transactions", transactions);
        return "redirect:/detail-transactions";
    }


    @GetMapping("detail-laws")
    public String detailsLaw(Model model) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, SignatureException, NoSuchProviderException, InvalidKeyException {

        return "detail-laws";
    }
    @GetMapping("/detail-laws/{hex}")
    public String detailLaws(@PathVariable(value = "hex") String hex, RedirectAttributes redirectAttrs) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, SignatureException, NoSuchProviderException, InvalidKeyException {

        redirectAttrs.addAttribute("title", "detail laws");
        //ORIGINAL_ALL_CORPORATION_LAWS_FILE

        byte[] sign = UtilsUse.hexToBytes(hex);
        String signStr = Base64.getEncoder().encodeToString(sign);
        System.out.println("LawsController /detail-laws-all/{signStr}: " + signStr);
        String url = address +"/conductorHashTran?hash=" + signStr;


        // Используем GET-запрос для получения данных по индексу
        String text = UtilUrl.getObject( url );
        DtoTransaction dtoTransaction = (DtoTransaction) UtilsJson.jsonToClass(text, DtoTransaction.class);
        List<String> allLaws = dtoTransaction.getLaws().getLaws();

        redirectAttrs.addFlashAttribute("laws", allLaws);
        redirectAttrs.addFlashAttribute("packageName",
                dtoTransaction.getLaws().getPacketLawName());
        redirectAttrs.addFlashAttribute("hashLaw",
                dtoTransaction.getLaws().getHashLaw());

        return "redirect:/detail-laws";
    }

}
