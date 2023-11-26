package bsuir.dtalalaev.lab2.locale;

public class LanguageFabric {
    public static Language parseLanguage(String lang){
        switch (lang.toUpperCase()){
            case "RU": return Language.RU;
            case "EN": return Language.EN;
            case "BY": return  Language.BY;
            default: return Language.UNDEFINED;
        }
    }
}
