package com.example.es.client.demo.util;

import com.example.es.client.demo.entity.User;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * EsResponseUtil:请求ES返回后的数据的处理工具类
 *
 * @author zhangxiaoxiang
 * @date: 2019/07/19
 */
@Component
public class MapUtil {


    /**
     * 利用反射将map集合封装成bean对象
     * @param map
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<?> clazz) throws Exception {
        Object obj = clazz.newInstance();
        if (map != null && !map.isEmpty() && map.size() > 0) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                // 属性名
                String propertyName = entry.getKey();
                // 属性值
                Object value = entry.getValue();
                String setMethodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
                //获取和map的key匹配的属性名称
                Field field = getClassField(clazz, propertyName);
                if (field == null){
                    continue;
                }
                Class<?> fieldTypeClass = field.getType();
                value = convertValType(value, fieldTypeClass);
                try {
                    clazz.getMethod(setMethodName, field.getType()).invoke(obj, value);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        return (T) obj;
    }

    /**
     * 根据给定对象类匹配对象中的特定字段
     * @param clazz
     * @param fieldName
     * @return
     */
    private static Field getClassField(Class<?> clazz, String fieldName) {
        if (Object.class.getName().equals(clazz.getName())) {
            return null;
        }
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        //如果该类还有父类，将父类对象中的字段也取出
        Class<?> superClass = clazz.getSuperclass();
        //递归获取
        if (superClass != null) {
            return getClassField(superClass, fieldName);
        }
        return null;
    }

    /**
     * 将map的value值转为实体类中字段类型匹配的方法
     * @param value
     * @param fieldTypeClass
     * @return
     */
    private static Object convertValType(Object value, Class<?> fieldTypeClass) {
        Object retVal = null;

        if (Long.class.getName().equals(fieldTypeClass.getName())
                || long.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Long.parseLong(value.toString());
        } else if (Integer.class.getName().equals(fieldTypeClass.getName())
                || int.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Integer.parseInt(value.toString());
        } else if (Float.class.getName().equals(fieldTypeClass.getName())
                || float.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Float.parseFloat(value.toString());
        } else if (Double.class.getName().equals(fieldTypeClass.getName())
                || double.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Double.parseDouble(value.toString());
        } else {
            retVal = value;
        }
        return retVal;
    }

    /**
     * bean转map
     * @param obj
     * @return
     */
    public static Map<String, String> beanToMap(Object obj) {

        Map<String, String> map = new HashMap<>();
        // 获取f对象对应类中的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            //将key置为小写，默认为对象的属性
            varName = varName.toLowerCase();
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o = fields[i].get(obj);
                if (o != null) {
                    map.put(varName, o.toString());
                }
                // System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o); 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }


    public static void main(String[] args) throws Exception {
        Map<String, Object> jsonMap = new HashMap<>();
        User user=new User();
        user.setName("zxx");
        user.setAge(12);
        user.setBirthday(new Date());
        Map<String, String> map = beanToMap(user);
        System.out.println(map);

    }
}
