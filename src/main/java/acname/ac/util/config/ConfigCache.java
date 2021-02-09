package acname.ac.util.config;

import acname.ac.Global;
import acname.ac.util.chat.ChatHelper;

import java.util.HashMap;

public class ConfigCache {

    public static class Config {

        public static HashMap<String, Object> cachedConfig = new HashMap<>();

        public static Object get(String str) {
            String var01 = (String) cachedConfig.getOrDefault(str, null);
            if (var01 == null) {
                var01 = Global.getConfig().getString(str);
                cachedConfig.put(str, var01);
            }
            return var01;
        }

        public static String getString(String str) {
            return get(str).toString();
        }

        public static int getInteger(String str) {
            return (int) Integer.parseInt(get(str).toString());
        }

        public static boolean getBoolean(String str) {
            return (boolean) Boolean.parseBoolean(get(str).toString());
        }

        public static double getDouble(String str) {
            return Double.parseDouble(get(str).toString());
        }

    }

    public static class Language {

        public static HashMap<String, Object> cachedLanguage = new HashMap<>();

        public static Object getThemeValue(String str) {
            String input = (String) cachedLanguage.getOrDefault(getStyle() + "." + str, null);
            if (input == null) {
                input = Global.getLanguage().getString(getStyle() + "." + str);
                cachedLanguage.put(getStyle() + "." + str, input);
            }
            return input;

        }

        public static String getStyle() {
            String style = (String) cachedLanguage.getOrDefault("style", null);
            if (style == null) {
                style = Global.getLanguage().getString("style");
                cachedLanguage.put("style", style);
            }
            return style;
        }

        public static Object get(String str) {
            return getThemeValue(str);
        }

        public static String getString(String str) {
            return ChatHelper.colorCodes((String) getThemeValue(str));
        }
    }

}
