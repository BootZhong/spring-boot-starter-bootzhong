package top.bootzhong.common.util;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 集合工具类
 * 闲余时间写的集合工具类
 * @author bootzhong
 * @date 2021/8/28
 */
public class ListUtil {

    /**
     * 匹配两个集合并设置 t是主体 r是副体， ft是t的匹配字段， fr是r的匹配字段， fs是匹配成功之后执行的操作
     * 为空的项不匹配
     * @param t 设置的集合
     * @param ft t的匹配字段
     * @param r 资源集合
     * @param fr r的匹配字段
     * @param fs 设置的方法
     * @param <T> 目标集合
     * @param <R> 筛选集合
     * @param <M> 匹配的项的类型
     */
    public static <T, R, M> void matchAndSet(Collection<T> t, Collection<R> r, Function<T, M> ft, Function<R, M> fr, BiConsumer<T,R> fs){
        for (T a:t){
            for (R b:r){
                if (ft.apply(a) != null && ft.apply(a).equals(fr.apply(b))){
                    fs.accept(a, b);
                    break;
                }
            }
        }
    }


    /**
     * 匹配两个集合并设置 t是主体 r是资源， ft是t的匹配字段， fr是r的匹配字段， fs是匹配成功之后执行的操作 fsg是fs设置的字段来源
     * @param t 设置的集合
     * @param ft t的匹配字段
     * @param r 资源集合
     * @param fr r的匹配字段
     * @param fs 设置的方法
     * @param fsr 被设置的资源
     * @param <T> 目标集合
     * @param <R> 筛选集合
     * @param <M> 匹配的项的类型
     * @param <S> 要设置的类型
     */
    public static <T, R, M, S> void matchAndSet(Collection<T> t, Collection<R> r, Function<T, M> ft, Function<R, M> fr,
                                                BiConsumer<T, S> fs, Function<R, S> fsr){

        
        for (T a:t){
            for (R b:r){
                if (ft.apply(a) != null && ft.apply(a).equals(fr.apply(b))){
                    fs.accept(a, fsr.apply(b));
                    break;
                }
            }
        }
    }

    /**
     * 匹配两个集合并设置
     * 匹配上会中断
     * @param t 设置的集合
     * @param r 资源集合
     * @param c 在两个集合遍历的时候需要进行的操作
     * @param <T> 目标集合
     * @param <R> 筛选集合
     * @apiNote 使用实例
     *   matchAndSet(aList, b.List, (a, b) -> {
     *             if (a.getCompId().equals(b.getCompId())){
     *                 a.setCompName(b.getCompName());
     *                 return true;
     *             }
     *             return false;
     *         });
     */
    public static <T, R> void matchAndSet(Collection<T> t, Collection<R> r, PredicateConsumerMulti<T, R> c){
        for (T a:t){
            for (R b:r){
                boolean accept = c.accept(a, b);
                //如果消费成功，即已经匹配上了，就跳出本次循环
                if (accept){
                    break;
                }
            }
        }
    }

    /**
     * 匹配两个集合并设置
     * 匹配上不会中断
     * @param t
     * @param r
     * @param c
     * @param <T>
     * @param <R>
     */
    public static <T, R> void matchAndSet(Collection<T> t, Collection<R> r, ConsumerMulti<T, R> c){
        for (T a:t){
            for (R b:r){
               c.accept(a, b);
            }
        }
    }

    /**
     * 判断是否有重复的对象 判断条件为funs传递进来的属性
     * WARNING!!! 不建议校验非基本类型数据，会有很大误差
     * @param col 集合
     * @param funs 需要判断的属性
     * @param <T> 集合的类型
     * @return boolean
     */
    public static <T> boolean hasDuplicate(Collection<T> col, Function<T, Object>... funs){
        HashMap<String, String> map = new HashMap(funs.length);

        for (T c:col){
            StringBuilder key = new StringBuilder();
            for (Function<T, Object> f:funs){
                key.append(f.apply(c));
            }

            if (map.get(key.toString()) != null){
                return true;
            } else {
                map.put(key.toString(), "-1");
            }
        }

        return false;
    }

    /**
     * 判断在集合中 指定的几个字段是否全部都相同
     * WARNING!!! 不建议校验非基本类型数据，会有很大误差
     * @param col
     * @param funs
     * @param <T>
     * @return
     */
    public static <T> boolean isUnique(Collection<T> col, Function<T, Object>... funs){
        Set<String> set = new HashSet<>();
        for (T c:col){
            StringBuilder key = new StringBuilder();
            for (Function<T, Object> f:funs){
                key.append(f.apply(c));
            }

            set.add(key.toString());
            if (set.size() > 1){
                return false;
            }
        }

        return true;
    }

    /**
     * 合并list
     * @param ts
     * @param <T>
     * @return
     */
    public static <T> List<T> merge(List<? extends T> ...ts){
        List<T> r = new ArrayList<>();
        for (List<? extends T> t : ts) {
            if (!isEmpty(t)){
                r.addAll(t);
            }
        }
        return r;
    }

    /**
     * 判断对象集合内对象的某属性是否为空
     * @param col 集合
     * @param funs 需要判断的项
     * @param <T> 集合的类型
     * @return
     */
    @SafeVarargs
    public static <T> boolean isNull(Collection<T> col, Function<T, Object>... funs){
        if (funs == null || funs.length <= 0){
            return col == null;
        }

        for (T t:col){
            for (Function<T, Object> f:funs){
                if (f.apply(t) == null){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isEmpty(Collection collection){
        if (collection == null || collection.isEmpty()){
            return true;
        }
        //如果全是空 也判断为空
        if (collection.stream().allMatch(e -> e == null)){
            return true;
        }

        return false;
    }

    public static  <T> T findOne(Collection<T> list, Predicate<T> predicate){
        if (isEmpty(list)){
            return null;
        }
        for (T t:list){
            if (predicate.test(t)){
                return t;
            }
        }
        return null;
    }

    /**
     * 把list分割成多批
     * @param tList
     * @param batchSize
     * @return
     */
    public static <T> List<List<T>> split(List<T> tList, int batchSize){
        int start = 0;
        int total = tList.size();

        List<List<T>> result = new ArrayList<>();
        while (start < total){
            int endIndex = Math.min(start + batchSize, total);
            result.add(tList.subList(start, endIndex));
            start = start + batchSize;
        }
        return result;
    }

    public static  <T> void ifNotEmptyDo(List<T> dataList, Consumer<List<T>> consumer){
        if (!isEmpty(dataList)){
            consumer.accept(dataList);
        }
    }

    /**
     * 函数式
     * 传递两个参数T,R来消费，返回是否消费成功的结果
     * @param <T>
     * @param <R>
     */
    @FunctionalInterface
    public interface PredicateConsumerMulti<T, R> {
        boolean accept(T t, R r);

    }

    /**
     * 函数式
     * 传递两个参数T,R来消费，返回是否消费成功的结果
     * @param <T>
     * @param <R>
     */
    @FunctionalInterface
    public interface ConsumerMulti<T, R> {
        void accept(T t, R r);
    }
}
