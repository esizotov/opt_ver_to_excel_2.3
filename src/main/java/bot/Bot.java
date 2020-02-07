package bot;

import dbController.DataBaseAudi;
import dbController.DataBaseJLR;
import dbController.DataBaseVolvo;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

public class Bot {

    static String[] COMMON_PHRASES = {
            "Привет.",
            "Отлично.",
            "Готово.",
            "Выполняю."};
    static String[] ELUSIVE_ANSWERS = {
            "Надо подумать...",
            "Я не понимаю о чем ты говоришь("};
    static final Map<String, String > PATTERNS_FOR_ANALYSIS = new HashMap<String, String>() {{
        // hallo
        put("привет", "hallo");
        put("хай", "hallo");
        put("здравствуй", "hallo");
        put("здравствуйте", "hallo");
        // who
        put("кто\\s.*ты", "who");
        put("ты\\s.*кто", "who");
        // name
        put("как\\s.*зовут", "name");
        put("как\\s.*имя", "name");
        put("есть\\s.*имя", "name");
        put("какое\\s.*имя", "name");
        // howareyou
        put("как\\s.*дела", "howareyou");
        put("как\\s.*жизнь", "howareyou");
        // whatdoyoudoing
        put("зачем\\s.*тут", "whatdoyoudoing");
        put("зачем\\s.*здесь", "whatdoyoudoing");
        put("что\\s.*делаешь", "whatdoyoudoing");
        put("чем\\s.*занимаешься", "whatdoyoudoing");
        // whatdoyoulike
        put("что\\s.*нравится", "whatdoyoulike");
        put("что\\s.*любишь", "whatdoyoulike");
        // iamfeelling
        put("кажется", "iamfeelling");
        put("чувствую", "iamfeelling");
        put("испытываю", "iamfeelling");
        // yes
        put("^да", "yes");
        put("согласен", "yes");
        // whattime
        put("который\\s.*час", "whattime");
        put("сколько\\s.*время", "whattime");
        put("сегодня\\s.*число", "whattime");
        put("какой\\s.*месяц", "whattime");
        put("какой\\s.*год", "whattime");
        // weather
        put("погода", "weather");
        put("погоду", "weather");
        put("погоды", "weather");
        // commandToDownloadVAG
        put("загрузи\\s.*остатки\\s.*\\audi", "commandToDownloadVAG");
        put("загрузи\\s.*остатки\\s.*\\ауди", "commandToDownloadVAG");
        put("загрузи\\s.*цс\\s.*\\audi", "commandToDownloadVAG");
        put("загрузи\\s.*цс\\s.*\\ауди", "commandToDownloadVAG");
        put("загрузи\\s.*остатки\\s.*\\vag", "commandToDownloadVAG");
        put("загрузи\\s.*остатки\\s.*\\ваг", "commandToDownloadVAG");
        put("загрузи\\s.*цс\\s.*\\vag", "commandToDownloadVAG");
        put("загрузи\\s.*цс\\s.*\\ваг", "commandToDownloadVAG");
        put("обнови\\s.*остатки\\s.*\\audi", "commandToDownloadVAG");
        put("обнови\\s.*остатки\\s.*\\ауди", "commandToDownloadVAG");
        put("обнови\\s.*цс\\s.*\\audi", "commandToDownloadVAG");
        put("обнови\\s.*цс\\s.*\\ауди", "commandToDownloadVAG");
        put("обнови\\s.*остатки\\s.*\\vag", "commandToDownloadVAG");
        put("обнови\\s.*остатки\\s.*\\ваг", "commandToDownloadVAG");
        put("обнови\\s.*цс\\s.*\\vag", "commandToDownloadVAG");
        put("обнови\\s.*цс\\s.*\\ваг", "commandToDownloadVAG");
        // commandToDownloadVolvo
        put("загрузи\\s.*остатки\\s.*\\volvo", "commandToDownloadVolvo");
        put("загрузи\\s.*остатки\\s.*\\вольво", "commandToDownloadVolvo");
        put("загрузи\\s.*цс\\s.*\\volvo", "commandToDownloadVolvo");
        put("загрузи\\s.*цс\\s.*\\вольво", "commandToDownloadVolvo");
        put("обнови\\s.*остатки\\s.*\\volvo", "commandToDownloadVolvo");
        put("обнови\\s.*остатки\\s.*\\вольво", "commandToDownloadVolvo");
        put("обнови\\s.*цс\\s.*\\volvo", "commandToDownloadVolvo");
        put("обнови\\s.*цс\\s.*\\вольво", "commandToDownloadVolvo");
        // commandToDownloadJaguar
//        put("загрузи\\s.*остатки\\s.*\\jaguar", "commandToDownloadJaguar");
        put("загрузи\\s.*остатки\\s.*\\ягуар", "commandToDownloadJaguar");
//        put("загрузи\\s.*цс\\s.*\\jaguar", "commandToDownloadJaguar");
        put("загрузи\\s.*цс\\s.*\\ягуар", "commandToDownloadJaguar");
//        put("обнови\\s.*остатки\\s.*\\jaguar", "commandToDownloadJaguar");
        put("обнови\\s.*остатки\\s.*\\ягуар", "commandToDownloadJaguar");
//        put("обнови\\s.*цс\\s.*\\jaguar", "commandToDownloadJaguar");
        put("обнови\\s.*цс\\s.*\\ягуар", "commandToDownloadJaguar");
        // commandToDownloadLR
//        put("загрузи\\s.*остатки\\s.*\\rover", "commandToDownloadLR");
//        put("загрузи\\s.*остатки\\s.*\\land", "commandToDownloadLR");
        put("загрузи\\s.*остатки\\s.*\\ровер", "commandToDownloadLR");
        put("загрузи\\s.*остатки\\s.*\\ленд", "commandToDownloadLR");
//        put("загрузи\\s.*цс\\s.*\\rover", "commandToDownloadLR");
//        put("загрузи\\s.*цс\\s.*\\land", "commandToDownloadLR");
        put("загрузи\\s.*цс\\s.*\\ровер", "commandToDownloadLR");
        put("загрузи\\s.*цс\\s.*\\ленд", "commandToDownloadLR");
//        put("обнови\\s.*остатки\\s.*\\rover", "commandToDownloadLR");
//        put("обнови\\s.*остатки\\s.*\\land", "commandToDownloadLR");
        put("обнови\\s.*остатки\\s.*\\ровер", "commandToDownloadLR");
        put("обнови\\s.*остатки\\s.*\\ленд", "commandToDownloadLR");
//        put("обнови\\s.*цс\\s.*\\rover", "commandToDownloadLR");
//        put("обнови\\s.*цс\\s.*\\land", "commandToDownloadLR");
        put("обнови\\s.*цс\\s.*\\ровер", "commandToDownloadLR");
        put("обнови\\s.*цс\\s.*\\ленд", "commandToDownloadLR");
        // bye
        put("прощай", "bye");
        put("увидимся", "bye");
        put("пока", "bye");
        put("досвидания", "bye");
        // thanks
        put("спасибо", "thanks");
    }};
    static final Map<String, String> ANSWERS_BY_PATTERNS = new HashMap<String, String>() {{
        put("hallo", "Привет.");
        put("who", "Я обычный чат-бот.");
        put("name", "Зовите меня Jackson.");
        put("howareyou", "У меня все хорошо. Спасибо.");
        put("whatdoyoudoing", "Я пробую общаться с людьми.");
        put("whatdoyoulike", "Мне нравится думать, что я не просто программа.");
        put("iamfeelling", "Как давно это началось? Рассакажите чуть подробнее.");
        put("yes", "Согласен.");
        put("bye", "Пока.");
        put("thanks", "Пожалуйста.");
    }};

    private static Pattern pattern;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd.MM.yyy");
    private static String url = "https://yandex.ru/pogoda/moscow";
    private static final String defoltFileAudi = "C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\Stock_remains\\stock_audi.txt";
    private static final String defoltFileVolvo = "C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\Stock_remains\\stock_volvo.xls";
    private static final String defoltFileJaguar = "C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\Stock_remains\\stock_jaguar.xls";
    private static final String defoltFileLR = "C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\Stock_remains\\stock_land_rover.xls";

    public static String sayInReturn(String msg) throws IOException {
        String say = (msg.trim().endsWith("?"))?
                ELUSIVE_ANSWERS[(int)(Math.random()*ELUSIVE_ANSWERS.length)]:
                COMMON_PHRASES[(int)(Math.random()*COMMON_PHRASES.length)];
        String message = String.join(" ",
                msg.toLowerCase().split("[ {,|.}?]+"));
        for(Map.Entry<String, String> o : PATTERNS_FOR_ANALYSIS.entrySet()) {
            pattern = Pattern.compile(o.getKey());
            if (pattern.matcher(message).find()) {
                if (o.getValue().equals("whattime")) {
                    Date date = new Date();
                    return simpleDateFormat.format(date.getTime());
                } else if (o.getValue().equals("weather")) {
                    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                        URI uri = null;
                        try {
                            uri = new URI(url);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        desktop.browse(uri);
                    } return "Готово!";
                } else if (o.getValue().equals("commandToDownloadVAG")) {
                    DataBaseAudi.loadPartsCS(defoltFileAudi);
                    return "Готово, мой друг!";
                } else if (o.getValue().equals("commandToDownloadVolvo")) {
                    DataBaseVolvo.loadPartsCSVolvo(defoltFileVolvo);
                    return "Готово, мой друг!";
                } else if (o.getValue().equals("commandToDownloadJaguar")) {
                    DataBaseJLR.loadPartsCSJaguar(defoltFileJaguar);
                    return "Готово, мой друг!";
                } else if (o.getValue().equals("commandToDownloadLR")) {
                    DataBaseJLR.loadPartsCSLR(defoltFileLR);
                    return "Готово, мой друг!";
                } else return ANSWERS_BY_PATTERNS.get(o.getValue());
            }
        }
        return say;
    }
}
