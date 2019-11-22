package cn.ivan.client.util;

import org.springframework.boot.jdbc.metadata.CommonsDbcp2DataSourcePoolMetadata;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * redis 版的可重入锁,基于threadLocal和引用计数
 *
 * @author yanqi
 * @version 1.0
 */
public class RedisWithReentrantLock {

    private ThreadLocal<Map<String, Integer>> lockers = new ThreadLocal<>();

    private Jedis jedis;

    public RedisWithReentrantLock(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * 实际操作redis 加锁
     *
     * @param key 健
     * @return 是否成功
     */
    private boolean _lock(String key) {
        return jedis.set(key, "", "nx", "ex", 5) != null;
    }

    private void _unlock(String key) {
        jedis.del(key);
    }

    private Map<String, Integer> currentLockers() {
        Map<String, Integer> refs = lockers.get();
        if (refs != null) {
            return refs;
        }
        lockers.set(new HashMap<>());
        return lockers.get();
    }

    public boolean lock(String key) {
        Map<String, Integer> refs = currentLockers();
        Integer count = refs.get(key);
        if (count != null) {
            refs.put(key, count + 1);
            return true;
        }
        boolean ok = _lock(key);
        if (!ok) {
            return false;
        }
        refs.put(key, 1);
        return true;
    }

    public boolean unlock(String key) {
        Map<String, Integer> refs = currentLockers();
        Integer refCnt = refs.get(key);
        if (refCnt == null) {
            return false;
        }
        refCnt -= 1;
        if (refCnt > 0) {
            refs.put(key, refCnt);
        } else {
            refs.remove(key);
            this._unlock(key);
        }
        return true;
    }


    public static void main(String[] args) {
//        Jedis jedis = new Jedis();
//        RedisWithReentrantLock redis = new RedisWithReentrantLock(jedis);
//        System.out.println(redis.lock("codehole"));
//        System.out.println(redis.lock("codehole"));
//        System.out.println(redis.unlock("codehole"));
//        System.out.println(redis.unlock("codehole"));

        //1.2.3a 和 1.2.4b
        //大版本号， 小版本号
        int compare = compare("1.2.3a", "1.2.3a.1");
        System.out.println(compare);

    }


    /**
     *  比较版本好， 返回 1 0 -1
     * @param b1
     * @param b2
     * @return
     */
    public static int compare(String b1 ,String b2){
        if (b1 == null || "".equals(b1) || b2 == null || "".equals(b2)){
            throw new RuntimeException("版本号不对");
        }
        // 如果相等
        if(b1.equals(b2)) return 0;
        // 如果不相等
        String[] split = b1.split("\\.");
        String[] split1 = b2.split("\\.");
        // 比较版本
        int length = split.length > split1.length ? split1.length: split.length;
        for (int i = 0 ; i< length ; i++){
            // 不知道是不是纯数字,如果是字母数字组合应该怎么判断
            if(split[i].matches("^[0-9]+$") && split1[i].matches("^[0-9]+$")){
                int res =  Integer.parseInt(split[i]) - Integer.parseInt(split1[i]) ;
                if (res == 0) continue;
                return res > 0 ? 1 : -1;
            }else {
                // 其中一个不是字母数字的组合,此时一次比较字符大小
                int l = split[i].length() > split1[i].length() ? split1[i].length():split[i].length();
                for(int j=0 ;j < l ; j++){
                    char a = split[i].charAt(j);
                    char c = split1[i].charAt(j);
                    if(a == c) continue;
                    return a > c ? 1 : -1;
                }
                // 如果长度不想等，则长度大的版本大
                if(split[i].length() != split1[i].length()){
                    return split[i].length() > split1[i].length() ? 1 : -1;
                }
            }
        }
        return split.length > split1.length ? 1 : -1 ;
    }

}
