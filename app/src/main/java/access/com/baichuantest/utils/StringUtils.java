package access.com.baichuantest.utils;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kain on 2016/8/15.
 */
public class StringUtils {

    /**
     * 格式化金额，大于9999以"万"表示，并保留两位小数
     * @param amountStr
     * @return
     */
    public static String formatAmountForText(String amountStr){
        if(!TextUtils.isEmpty(amountStr)){
            float totalAmount = Float.parseFloat(amountStr);
            return formatAmount(totalAmount);
        }else{
            return "0";
        }
    }

    /**
     * 格式化金额，大于9999以"万"表示，并保留两位小数
     * @param amount
     * @return
     */
    public static String formatAmount(float amount){
        if (amount > 9999) {
            String totalStr = String.valueOf((int) amount);
            totalStr = totalStr.substring(0, totalStr.length() - 4) + "." + totalStr.substring(totalStr.length() - 4, totalStr.length() - 2) + "万";
            return totalStr;
        } else {
            return String.valueOf(StringUtils.roundDoubleForMoney(amount));
        }
    }

    /**
     * 根据换行符得到editText每一行的内容
     * @param str
     * @return
     */
    public static ArrayList<String> getStringListForInput(String str) {
        if (TextUtils.isEmpty(str)) {
            return new ArrayList<>();
        }
        str = str.replaceAll(" ", "");
        String encodeStr = "";
        try {
            Log.e("onTextChanged", URLEncoder.encode(str.toString(), "utf-8"));
            encodeStr = URLEncoder.encode(str.toString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ArrayList<String> strList = new ArrayList<>();
        // 换行符
        String symbol = "%0A";
        String[] arr = encodeStr.split(symbol);
        for (String s : arr) {
            try {
                if(!TextUtils.isEmpty(s)){
                    strList.add(URLDecoder.decode(s, "utf-8"));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return strList;
    }

    /**
     * url 地址后面拼接uid
     * @param url
     * @param id
     * @return
     */
    public static String getUrlForUid(String url, String id) {
        String symbolStr = TextUtils.isEmpty(URI.create(url).getQuery()) ? "?" : "&";
        String anchorStr = "";
        String uid = Uri.parse(url).getQueryParameter("uid");
        if (url.contains("#")) {
            anchorStr = url.substring(url.indexOf("#"), url.length());
            url = url.substring(0, url.indexOf("#"));
        }
        LogUtils.e(" uid == " + uid + " &" + TextUtils.isEmpty(uid));
        if (TextUtils.isEmpty(uid) && !url.contains("uid=")) {
            url = url + symbolStr + "uid=" + id;
        } else {
            url = url.replaceAll("uid="+uid, "uid=" + id);
        }
        return url + anchorStr;
    }

    /**
     * 保留两位小数，不够用0补
     *
     * @param paramDouble
     * @return
     */
    public static String roundDoubleForMoney(double paramDouble) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(paramDouble);
    }

    public static String map2String(HashMap<String, String> param) {
        if (param == null) {
            return "";
        }
        String strParam = "";
        Iterator iterator = param.entrySet().iterator();
        while (iterator.hasNext()) {
            java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
            Log.e("map", entry.getKey() + "   " + entry.getValue());
            strParam += entry.getKey() + "=" + entry.getValue() + "&";
        }
        return strParam;
    }

    /**
     * 判断字符串是否相等
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean textEquals(String str1, String str2) {
        if (!TextUtils.isEmpty(str1) && !TextUtils.isEmpty(str2)) {
            return str1.equals(str2);
        } else return TextUtils.isEmpty(str1) && TextUtils.isEmpty(str2);
    }

    /**
     * @param number
     * @return
     * @Title : filterNumber
     * @Type : FilterStr
     * @date : 2014年3月12日 下午7:23:03
     * @Description : 过滤出数字
     */
    public static String filterNumber(String number) {
        number = number.replaceAll("[^(0-9)]", "");
        return number;
    }

    /**
     * @param alph
     * @return
     * @Title : filterAlphabet
     * @Type : FilterStr
     * @date : 2014年3月12日 下午7:28:54
     * @Description : 过滤出字母
     */
    public static String filterAlphabet(String alph) {
        alph = alph.replaceAll("[^(A-Za-z)]", "");
        return alph;
    }

    /**
     * @param chin
     * @return
     * @Title : filterChinese
     * @Type : FilterStr
     * @date : 2014年3月12日 下午9:12:37
     * @Description : 过滤出中文
     */
    public static String filterChinese(String chin) {
        chin = chin.replaceAll("[^(\\u4e00-\\u9fa5)]", "");
        return chin;
    }

    /**
     * @param character
     * @return
     * @Title : filter
     * @Type : FilterStr
     * @date : 2014年3月12日 下午9:17:22
     * @Description : 过滤出字母、数字和中文
     */
    public static String filter(String character) {
        character = character.replaceAll("[^(a-zA-Z0-9\\u4e00-\\u9fa5)]", "");
        return character;
    }

    /**
     * @param character
     * @return
     * @Title : filter
     * @Type : FilterStr
     * @date : 2014年3月12日 下午9:17:22
     * @Description : 过滤出中文和字母
     */
    public static String keepNumFilter(String character) {
        character = character.replaceAll("[^(a-zA-Z\\u4e00-\\u9fa5)]", "");
        return character;
    }

    /**
     * string to json
     *
     * @param str
     * @return
     */
    public static JSONObject string2JSON(String str) {
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    /**
     * 检查输入的数据中是否有特殊字符
     *
     * @param qString 要检查的数据
     * @param regx    特殊字符正则表达式
     * @return boolean 如果包含正则表达式 <code> regx </code> 中定义的特殊字符，返回true；
     * 否则返回false
     */
    public static boolean hasCrossScriptRisk(String qString) {
        if (qString != null) {
//            qString = qString.trim();
            String regx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
            Pattern p = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(qString);
            return m.find() || qString.contains(" ");
        }
        return false;
    }

    // 信息隐藏处理
    public static String dealWithAccount(String oStr) {
        if (TextUtils.isEmpty(oStr)) {
            return "";
        }

        String nStr;
        // 保留多少位，如=2，则xx*******xx
        int keep = 0;
        int strLength = oStr.length();
        if (strLength > 5 && strLength < 10) {
            keep = 2;
        } else if (strLength >= 10) {
            keep = 4;
        } else {
            return oStr;
        }
        int end = strLength - keep;
        StringBuilder replaceStr = new StringBuilder();
        for (int i = 0; i < strLength - keep * 2; i++) {
            replaceStr.append("*");
        }
        nStr = oStr.substring(0, keep) + replaceStr + oStr.substring(end, strLength);

        return nStr;
    }

    /**
     * random string
     *
     * @param length string length
     * @return
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    // 校验字符串只能是数字,英文字母和中文
    public static boolean isValidString(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }
}
