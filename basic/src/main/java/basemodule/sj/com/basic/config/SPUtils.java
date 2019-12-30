package basemodule.sj.com.basic.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.core.content.SharedPreferencesCompat;
import basemodule.sj.com.basic.util.JsonHelper;
import basemodule.sj.com.basic.util.Util;


/**
 * content: SharedPreferences工具类
 * author：sj
 * time: 2017/6/22 11:47
 * email：13658029734@163.com
 * phone:13658029734
 */

public class SPUtils {
    //app系统配置
    public static final String APP_PREFS = "app_prefs";
    private static Map<String, SPUtils> sSPMap = new HashMap<>();
    private SharedPreferences sp;

    private SPUtils(String spName) {
        sp = Util.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    /**
     * 获取SP实例
     *
     * @return {@link SPUtils}
     */
    public static SPUtils getInstance() {
        return getInstance("");
    }

    /**
     * 获取SP实例
     * spName 未空 ：系统配置，不为空 ：用户配置
     *
     * @param spName sp名 默认为系统配置
     * @return {@link SPUtils}
     */
    public static SPUtils getInstance(String spName) {
        if (isSpace(spName)) {
            spName = APP_PREFS;
        }
        SPUtils sp = sSPMap.get(spName);
        if (sp == null) {
            sp = new SPUtils(spName);
            sSPMap.put(spName, sp);
        }
        return sp;
    }

    private static boolean isSpace(String s) {
        if (s == null)
            return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public void put(String key, Object object) {
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 保存对象， key为对象SimpleName 值为对象
     *
     * @param bean
     * @param <T>
     */
    public <T> void saveObject(T bean) {
        if (bean != null) {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            String json = gson.toJson(bean);
            String className = bean.getClass().getSimpleName();
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(className, json);
            editor.apply();
        }
    }

    /**
     * 移除对象
     *
     * @param bean
     * @param <T>
     */
    public <T> void removeObject(Class bean) {
        String className = bean.getSimpleName();
        remove(className);
    }

    public <T> T getObject(Class<T> classOfT) {
        T user = null;
        if (classOfT != null) {
            String json = getObjectJson(classOfT);
            user = JsonHelper.parserJson2Object(json, classOfT);
        }

        return user;
    }

    public <T> String getObjectJson(Class<T> classOfT) {
        String json = "";
        if (classOfT != null) {
            String name = classOfT.getSimpleName();
            json = sp.getString(name, "");
        }
        return json;
    }

    /**
     * 存入list集合
     *
     * @param key
     * @param datas
     */
    public void putList(String key, List<Map<String, String>> datas) {
        JSONArray mJsonArray = new JSONArray();
        for (int i = 0; i < datas.size(); i++) {
            Map<String, String> itemMap = datas.get(i);
            Iterator<Map.Entry<String, String>> iterator = itemMap.entrySet().iterator();

            JSONObject object = new JSONObject();

            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                try {
                    object.put(entry.getKey(), entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mJsonArray.put(object);
        }
        sp.edit().putString(key, mJsonArray.toString()).apply();
    }

    /**
     * 取出list集合
     *
     * @param key
     * @return
     */
    public List<Map<String, String>> getList(String key) {
        List<Map<String, String>> datas = new ArrayList<>();
        String result = sp.getString(key, "");
        try {
            if (!TextUtils.isEmpty(result)) {
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject itemObject = array.getJSONObject(i);
                    Map<String, String> itemMap = new HashMap<>();
                    JSONArray names = itemObject.names();
                    if (names != null) {
                        for (int j = 0; j < names.length(); j++) {
                            String name = names.getString(j);
                            String value = itemObject.getString(name);
                            itemMap.put(name, value);
                        }
                    }
                    datas.add(itemMap);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return datas;
    }


    /**
     * SP中获取所有键值对
     *
     * @return Map对象
     */
    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    /**
     * SP中是否存在该key
     *
     * @param key 键
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public boolean contains(@NonNull String key) {
        return sp.contains(key);
    }

    /**
     * SP中移除该key
     *
     * @param key 键
     */
    public void remove(String key) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }


    /**
     * SP中清除所有数据
     */
    public void clear() {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().apply();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {

            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}
