package com.ucsmy.commons.utils;

import com.ucsmy.ucas.config.serialNumber.SerialNumberContextHelper;
import com.ucsmy.ucas.config.serialNumber.getter.AbstractSerialNumberGenerator;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * UUID生成器 <br>
 * Created by ucs_panwenbo on 2017/4/14.<br>
 * <br>
 * 新增流水号生成，所有UUID都改为使用redis方式的流水号生成器 <br>
 * Updated by chenqilin on 2017/5/26 <br>
 */
@Component
public class UUIDGenerator {

    private static final int IP;

    public static int IptoInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) - Byte.MIN_VALUE + bytes[i];
        }
        return result;
    }

    static {
        int ipadd;
        try {
            ipadd = IptoInt(InetAddress.getLocalHost().getAddress());
        } catch (Exception e) {
            ipadd = 0;
        }
        IP = ipadd;
    }

    private static short counter = (short) 0;
    private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

    /**
     * Unique across JVMs on this machine (unless they load this class in the
     * same quater second - very unlikely)
     */
    protected static int getJVM() {
        return JVM;
    }

    /**
     * Unique in a millisecond for this JVM instance (unless there are >
     * Short.MAX_VALUE instances created in a millisecond)
     */
    protected static short getCount() {
        synchronized (UUIDGenerator.class) {
            if (counter < 0) {
                counter = 0;
            }
            return counter++;
        }
    }

    /**
     * Unique in a local network
     */
    protected static int getIP() {
        return IP;
    }

    /**
     * Unique down to millisecond
     */
    protected static short getHiTime() {
        return (short) (System.currentTimeMillis() >>> 32);
    }

    protected static int getLoTime() {
        return (int) System.currentTimeMillis();
    }

    private final static String sep = "-";

    protected static String format(int intval) {
        String formatted = Integer.toHexString(intval);
        StringBuffer buf = new StringBuffer("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }

    protected static String format(short shortval) {
        String formatted = Integer.toHexString(shortval);
        StringBuilder buf = new StringBuilder("0000");
        buf.replace(4 - formatted.length(), 4, formatted);
        return buf.toString();
    }

    /**
     * 新的UUID生成规则，返回18位的流水号 <br>
     * created by chenqilin <br>
     * @return
     */
    public static String generate() {
        AbstractSerialNumberGenerator serialNumberGenerator = SerialNumberContextHelper.getSerialNumberGenerator();
        return serialNumberGenerator.get();
    }

    /**
     * 新的UUID生成规则，返回18位的流水号 <br>
     * created by chenqilin <br>
     * @param prefix 自定义的前缀，如果为null，使用默认前缀 <br>
     *               注意：自定义前缀的长度必须小于或等于配置里的前缀长度，小于时高位补0
     * @return
     */
    public static String generate(String prefix) {
        AbstractSerialNumberGenerator serialNumberGenerator = SerialNumberContextHelper.getSerialNumberGenerator();
        return serialNumberGenerator.get(prefix);
    }

    /**
     * 生成特定长度的自增流水号，如8位的ClientId
     * @param length 流水号长度
     * @param maxLength 流水号最大长度
     * @param prefix 业务标记
     * @param delta 自增量
     * @return
     */
    public static String generate(String prefix, int length, int maxLength,long delta) {
        AbstractSerialNumberGenerator serialNumberGenerator = SerialNumberContextHelper.getSerialNumberGenerator();
        return serialNumberGenerator.getAutoIncrement(prefix, length, maxLength, delta);
    }

    /**
     * 旧的UUID生成规则
     *
     * @return
     */
    public static String oldGenerate() {
        return new StringBuilder(36).append(format(getIP())).append(sep).append(
                format(getJVM())).append(sep).append(format(getHiTime()))
                .append(sep).append(format(getLoTime())).append(sep).append(
                        format(getCount())).toString();
    }

    /**
     * 截取生成的id，同时防止最后一个字符为分隔符sep
     *
     * @param capacity
     * @return
     */
    public static String generate(int capacity) {
        String uuid = oldGenerate();
        uuid = uuid.substring(0, capacity - 1);
        if (sep.equals(uuid.substring(uuid.length() - 2, uuid.length() - 1))) {
            uuid = uuid.substring(0, uuid.length() - 2);
        }
        return uuid;
    }

    /**
     * 截取生成的id，截取的是末尾的位数，同时防止第一个字符为分隔符sep
     *
     * @param capacity
     * @return
     */
    public static String generateFromEnd(int capacity) {
        String uuid = oldGenerate();
        uuid = uuid.substring(uuid.length() - capacity, uuid.length());
        if (sep.equals(uuid.substring(0, 1))) {
            uuid = uuid.substring(1, uuid.length());
        }
        return uuid;
    }
}
