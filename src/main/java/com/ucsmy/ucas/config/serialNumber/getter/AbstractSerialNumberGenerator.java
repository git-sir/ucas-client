package com.ucsmy.ucas.config.serialNumber.getter;

import com.ucsmy.commons.utils.StringUtils;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 抽象序列号模式
 * Created by chenqilin on 2017/5/18.
 */
public abstract class AbstractSerialNumberGenerator {

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 日期格式
     */
    private SimpleDateFormat dateFormat;

    /**
     * 编号格式
     */
    private DecimalFormat decimalFormat;

    /**
     * 序列号编号是否无序
     */
    private Boolean disOrder;

    AbstractSerialNumberGenerator(String prefix, String dataFormat, String decimalFormat) {
        this.prefix = prefix;
        this.dateFormat = new SimpleDateFormat(dataFormat);
        this.decimalFormat = new DecimalFormat(decimalFormat);
    }

    /**
     * 获取当前生成模式
     * @return
     */
    public abstract String getMode();

    /**
     * 生成序列号
     * @return 正常情况下返回序号，如果更新db/redis失败，返回null
     */
    public String get() {
        return this.get(null);
    }

    /**
     * 生成序列号
     * @param prefix 自定义的前缀，如果为null，使用默认前缀
     * @return 正常情况下返回序号，如果更新db/redis失败，返回null
     */
    public abstract String get(String prefix);

    /**
     * 获取一般序列号，即没有前缀和日期
     * @param prefix 业务前缀
     * @param length 需要生成的长度
     * @param maxLength 序列号最大长度
     * @param delta 增量
     * @return
     */
    public abstract String getAutoIncrement(String prefix, int length, int maxLength, long delta);

    /**
     * 根据长度和最大长度处理value
     * @param length 指定返回长度，不指定则填0或null
     * @param maxLength 最大长度
     * @return
     */
    protected String parseAutoIncrement(long value, int length, int maxLength) {
        String parseNumber = String .valueOf(value);
        if (length != 0 ) {
            StringBuilder initNumber = new StringBuilder();
            for (int i = 0; i < length; i++) {
                if (i == 0) {
                    initNumber.append('1');
                } else {
                    initNumber.append('0');
                }
            }
            BigInteger initValue = new BigInteger(initNumber.toString());
            BigInteger deltaValue = BigInteger.valueOf(value);
            BigInteger number = initValue.add(deltaValue);
            parseNumber = String.valueOf(number);
        }
        if (parseNumber.length() > maxLength) {
            throw new RuntimeException("Auto Increment Number Out Of Range.");
        }
        return parseNumber;
    }

    /**
     * 生成格式化的序列号
     * @return
     */
    public String generateNumber(String prefix, Date date, Long number) {
        String numberPrefix = this.prefix;
        if (StringUtils.isNotEmpty(prefix)) {
            if (prefix.length() > numberPrefix.length()) {
                prefix = prefix.substring(prefix.length() - numberPrefix.length() - 1, prefix.length() - 1);
            } else if (prefix.length() < numberPrefix.length()) {
                StringBuilder perfixZero = new StringBuilder();
                for (int i = 0; i < numberPrefix.length() - prefix.length(); i++) {
                    perfixZero.append("0");
                }
                perfixZero.append(prefix);
                prefix = perfixZero.toString();
            }
            numberPrefix = prefix;
        }
        return new StringBuffer(numberPrefix)
                .append(dateFormat.format(date))
                .append(decimalFormat.format(number))
                .toString();
    }

    /**
     * 基于JAVA的UUID来生成指定长度的数字 <br>
     * 这个方法返回的是不带前缀和日期格式的序列号，只是单纯的数字 <br>
     * @param length 结果长度
     * @return
     */
    public String getNumberByUUID(int length) {
        int uuid = UUID.randomUUID().hashCode();
        if (uuid < 0) {
            uuid = uuid & Integer.MAX_VALUE;
        }
        String result = String.valueOf(uuid);
        if (result.length() > length) {
            result = result.substring(result.length() - length - 1, result.length() - 1);
        } else {
            // length超过了timestamp长度，前面补0
            StringBuilder perfixZeroResult = new StringBuilder();
            for (int i = 0; i < result.length() - length; i++) {
                perfixZeroResult.append("0");
            }
            perfixZeroResult.append(result);
            result = perfixZeroResult.toString();
        }
        return result;
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    public void setDecimalFormat(DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

    public Boolean getDisOrder() {
        return disOrder;
    }

    public void setDisOrder(Boolean disOrder) {
        this.disOrder = disOrder;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
