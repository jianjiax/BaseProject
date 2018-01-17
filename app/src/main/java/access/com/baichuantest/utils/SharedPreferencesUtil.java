package access.com.baichuantest.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

/**
 * SharedPrefereces保存数据，工具类
 *
 * @author kTian
 */
public class SharedPreferencesUtil {
    private static Context mContext;
    public final static String FILE_NAME_BASE_URL = "file_name_base_url";

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 得到SharedPreferences对象
     *
     * @param sharedName 保存SharedPreferences的名字，不可以为null
     * @return 返回一个SharedPreferences对象
     */
    private static SharedPreferences newSharedPreferences(String sharedName) {
        return mContext.getSharedPreferences(sharedName, mContext.MODE_PRIVATE);
    }

    /**
     * 移除SharedPrefereces中的缓存数据
     *
     * @param sharedName
     * @param key
     */
    public static void removeDataSharedPreferences(String sharedName, String key) {
        newSharedPreferences(sharedName).edit().remove(key);
    }

    /**
     * 保存String类型的数据
     *
     * @param sharedName 保存SharedPreferences的名字，不可以为null
     * @param key
     * @param value      键对应的值
     */
    public static void putString(String sharedName, String key, String value) {
        Editor mEditor = newSharedPreferences(sharedName).edit();
        mEditor.putString(key, value);
        mEditor.commit();
    }

    /**
     * 获取保存的String类型的数据
     *
     * @param sharedName    保存SharedPreferences的名字，不可以为null
     * @param key
     * @param defaultString 找不到值的时候的默认值
     * @return String
     */
    public static String getStringWithDefault(String sharedName, String key, String defaultString) {
        return newSharedPreferences(sharedName).getString(key, defaultString);
    }

    /**
     * 获取保存的String类型的数据
     *
     * @param sharedName 保存SharedPreferences的名字，不可以为null
     * @param key
     * @return String
     */
    public static String getString(String sharedName, String key) {
        return newSharedPreferences(sharedName).getString(key, "");
    }

    /**
     * 保存integer类型的数据
     *
     * @param sharedName 保存SharedPreferences的名字，不可以为null
     * @param key
     * @param value      键对应的值
     */
    public static void putInt(String sharedName, String key, int value) {
        Editor mEditor = newSharedPreferences(sharedName).edit();
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    /**
     * 获取保存的integer类型的数据
     *
     * @param sharedName 保存SharedPreferences的名字，不可以为null
     * @param key
     * @return integer
     */

    public static int getInt(String sharedName, String key, int defaultValue) {
        return newSharedPreferences(sharedName).getInt(key, defaultValue);
    }

    /**
     * 保存boolean类型的数据
     *
     * @param sharedName 保存SharedPreferences的名字，不可以为null
     * @param key
     * @param value      键对应的值
     */
    public static void putBoolean(String sharedName, String key, boolean value) {
        Editor mEditor = newSharedPreferences(sharedName).edit();
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    /**
     * 获取保存的boolean类型的数据
     *
     * @param sharedName 保存SharedPreferences的名字，不可以为null
     * @param key
     * @return boolean
     */

    public static boolean getBoolean(String sharedName, String key) {
        return getBoolean(sharedName, key, false);
    }

    public static boolean getBoolean(String sharedName, String key, boolean defaultVal) {
        return newSharedPreferences(sharedName).getBoolean(key, defaultVal);
    }

    /**
     * 保存float类型的数据
     *
     * @param sharedName 保存SharedPreferences的名字，不可以为null
     * @param key
     * @param value      键对应的值
     */
    public static void putFloat(String sharedName, String key, float value) {
        Editor mEditor = newSharedPreferences(sharedName).edit();
        mEditor.putFloat(key, value);
        mEditor.commit();
    }

    /**
     * 获取保存的float类型的数据
     *
     * @param sharedName 保存SharedPreferences的名字，不可以为null
     * @param key
     * @return float
     */

    public static float getFloat(Context context, String sharedName, String key) {
        return newSharedPreferences(sharedName).getFloat(key, 0f);
    }

    /**
     * 保存long类型的数据
     *
     * @param sharedName 保存SharedPreferences的名字，不可以为null
     * @param key
     * @param value      键对应的值
     */
    public static void putLong(String sharedName, String key, long value) {
        Editor mEditor = newSharedPreferences(sharedName).edit();
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    /**
     * 获取保存的long类型的数据
     *
     * @param sharedName 保存SharedPreferences的名字，不可以为null
     * @param key
     * @return long
     */

    public static long getLong(String sharedName, String key) {
        return newSharedPreferences(sharedName).getLong(key, 0);
    }

    /**
     * 将Object信息Base64后存入Preferences
     *
     * @param context
     * @param name
     * @param obj
     */

    public static <T> void putPreferences(Context context, String preferenceName, String name, T obj) {
        SharedPreferences sp = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        if (obj == null) {
            editor.putString(name, null);
            editor.commit();
            return;
        }

        try {
            ByteArrayOutputStream toByte = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(toByte);
            oos.writeObject(obj);

            // 对byte[]进行Base64编码
            String obj64 = new String(Base64.encode(toByte.toByteArray(), Base64.DEFAULT));

            editor.putString(name, obj64);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取SharePreference中值，Base64解码之后传出
     *
     * @param context
     * @param name
     */
    @SuppressWarnings("unchecked")
    public static <T> T getPreferences(Context context, String preferenceName, String name) {
        SharedPreferences sp = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);

        try {
            String obj64 = sp.getString(name, "");
            if (TextUtils.isEmpty(obj64)) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(obj64, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

}
