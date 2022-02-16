package jedis;

import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.Scanner;

/**
 * 完成一个手机验证码功能
 * 1.输入手机号、点击发送后随机生成6位数字码，2分钟有效
 * 2.输入验证码，点击验证，返回成功或失败
 * 3.每个手机号每天只能输入3次
 */
public class PhoneCodeVerify {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入你的手机号：");
        String phone = scanner.nextLine();
        saveCode(phone);
        System.out.println("请输入你的验证码：");
        String code = scanner.nextLine();
        verifyCode(code,phone);
    }

    // 判断验证码是否正确
    private static void verifyCode(String code,String phone) {
        if (code.equals(getCode(phone))) {
            System.out.println("验证码正确！");
        }else {
            System.out.println("验证码错误！");
        }
    }

    private static String getCode(String phone) {
        String verifyCodeKey = "verify" + phone + "code";

        Jedis jedis = new Jedis("192.168.132.129", 6379);

        return jedis.get(verifyCodeKey);
    }


    private static void saveCode(String phone) {
        Jedis jedis = new Jedis("192.168.132.129", 6379);

        String verifyCodeKey = "verify" + phone + "code";
        String verifyCodeCountKey = "verify" + phone + "count";

        String count = jedis.get(verifyCodeCountKey);
        if (count == null) {
            jedis.setex(verifyCodeCountKey, 24*60*60, "1");
        } else if (Integer.parseInt(count) <= 2) {
            jedis.incr(count);
        } else if (Integer.parseInt(count) > 2) {
            System.out.println("今天发送次数已经超过三次");
        }

        jedis.setex(verifyCodeKey, 120, generateCode());
        jedis.close();
    }

    public static String generateCode() {
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            code+=random.nextInt(10);
        }
        return code;
    }

}
