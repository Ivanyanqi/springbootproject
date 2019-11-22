package cn.ivan.client.other;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author yanqi
 * @date 2019-11-20 23:46
 */
@Slf4j
public class PriorityTest {

    public static void main(String[] args) {
        log.info("result == {}",new PriorityTest().check());
        log.info("resultGoods = {}",new PriorityTest().checkGoods(new Goods()));
    }

    @Test
    public void priorityTest1(){

        Long id = getId(1);
        if(id == null || getLId(id) != 0){
            log.info("=================,{}",id);
        }
        if(id == null || Long.compare(id,0L) != 1){
            log.info("=================ok,{}",id);
        }
        log.info("result == {}" ,check());
        log.info("resultGoods = {}",new PriorityTest().checkGoods(new Goods()));
    }

    private Long checkGoods(Goods goods){
        Long id = goods.getId();
        //int compare = Long.compare(id, 0L);
        return (id == null || Long.compare(id,0L) != 1)? goods.getSId() : id;
//        return (id == null || compare(id) != 1)? goods.getSId() : id;
    }

    private int compare(Long id){
        log.info("=======方法执行了============");
        return Long.compare(id,0L);
    }
    private Long check(){
        Long id = getId(2);
        return (id == null || Long.compare(id,0L) != 1) ? 1L : id;
    }


    private Long getId(int type){
        if(type == 1){
            return 1L;
        }else {
            return null;
        }
    }


    private long getLId(Long type){
        if(type.longValue() == 1){
            return 1;
        }else {
            return 0;
        }
    }

    @Data
    public static class Goods{
        private Long id ;

        private Long sId;
    }


}
