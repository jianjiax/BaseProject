package access.com.baichuantest.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Kain on 2016/10/10.
 */
public class EncryptUtils {

    private static final String HTTP_METHOD_GET = "GET";
    private static final String HTTP_METHOD_POST = "POST";

    /**
     * 加密密钥
     */
    public static final String XXTEASECRET = "youngtxxx";

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    /**
     * md5加密
     *
     * @param strParam
     * @param encryptionStr 加密方式 "MD5""  SHA1"
     * @return MD5
     * @Description: TODO
     */
    public final static String getMessageDigest(String strParam, String encryptionStr) {
        if (strParam == null || strParam.length() == 0) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            if (encryptionStr == null || "".equals(encryptionStr)) {
                encryptionStr = "MD5";
            }
            MessageDigest mdTemp = MessageDigest.getInstance(encryptionStr);
            mdTemp.update(strParam.getBytes());
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * SHA1加密
     */
    public static String SHA1Encrypt(String strSrc) {
        // parameter strSrc is a string will be encrypted,
        // parameter encName is the algorithm name will be used.
        // encName dafault to "MD5"
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Invalid algorithm.");
            return null;
        }
        return strDes;
    }


    /**
     * 参数自然排序，按照k=v方式&连接
     */
    public static String paramSort(HashMap<String, String> map) {
        if (map == null || map.size() < 1) {
            return "";
        }
        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
                map.entrySet());
        // 排序前
        for (int i = 0; i < infoIds.size(); i++) {
            String id = infoIds.get(i).toString();
            // System.out.println(id);
        }

        // 排序
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
                // return (o2.getValue() - o1.getValue());
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });

        // 排序后
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < infoIds.size(); i++) {
            String id = infoIds.get(i).toString();
            sb.append(id);
            sb.append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
