package top.bootzhong.common.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * 初始化工具类
 * @author bootzhong
 */
public class ValueUtil {
    public static <T> void setZeroIfNull(T t){
        final Class<?> clazz = t.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field f : declaredFields) {
            try {
                f.setAccessible(true);
                if (Number.class.isAssignableFrom(f.getType()) && f.get(t) == null) {
                    if (f.getType().equals(Integer.class)){
                        f.set(t, 1);
                    }else if (f.getType().equals(BigDecimal.class)){
                        f.set(t, BigDecimal.ZERO);
                    }
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            } finally {
                f.setAccessible(false);
            }
        }
    }
}
