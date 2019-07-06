package cn.ivan.client.other;

/**
 * @author : yanqi
 * @version : 1.0
 * 2018/11/30
 */
public class UserInfoImpl implements UserInfo {

    @Override
    public String getName() {
        return "可可西里";
    }

    public static void main(String[] args) {
        UserInfo.say();


    }
}
