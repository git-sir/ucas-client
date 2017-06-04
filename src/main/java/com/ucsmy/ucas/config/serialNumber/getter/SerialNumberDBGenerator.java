package com.ucsmy.ucas.config.serialNumber.getter;

import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.ucas.config.serialNumber.util.SerialNumberMode;
import com.ucsmy.ucas.manage.dao.ManageSerialNumberMapper;
import com.ucsmy.ucas.manage.entity.ManageSerialNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 序列号生成器 - DB模式
 * Created by chenqilin on 2017/5/18.
 */
public class SerialNumberDBGenerator extends AbstractSerialNumberGenerator {

    /**
     * db模式线程最大等待时间，毫秒
     */
    private Long waitTime;

    private final static int SERIALNUMBRE_MAX_LENGTH = 20;

    @Autowired
    private ManageSerialNumberMapper manageSerialNumberMapper;

    public SerialNumberDBGenerator(String prefix, String dataFormat, String decimalFormat, String waitTime) {
        super(prefix, dataFormat, decimalFormat);
        Long time = Long.parseLong(waitTime);
        this.waitTime = time;
    }

    @Override
    public String getMode() {
        return SerialNumberMode.DB.toString();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public String get() {
        return get(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public String get(String prefix) {
        String serialNumber;
        Date now = new Date();
        long delta = 1;
        if (StringUtils.isEmpty(prefix)) {
            prefix = super.getDateFormat().format(now);
        }
        String nextNumber = getAutoIncrement(prefix
                , 0, SERIALNUMBRE_MAX_LENGTH, delta);
        if (StringUtils.isEmpty(nextNumber)) {
            return null;
        }
        serialNumber = super.generateNumber(prefix, now, Long.parseLong(nextNumber));
        return serialNumber;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public String getAutoIncrement(String prefix, int length, int maxLength, long delta) {
        Date now = new Date();
        ManageSerialNumber manageSerialNumber = manageSerialNumberMapper.querySerialNumberById(prefix);
        if (manageSerialNumber == null) {
            // 没有记录
            manageSerialNumber = new ManageSerialNumber();
            manageSerialNumber.setSerialNumberId(prefix);
            manageSerialNumber.setGeneratedSerialNumber(delta);
            manageSerialNumber.setCurrentSerialNumber(1L);
            manageSerialNumber.setSerialNumberDate(now);
            int result = manageSerialNumberMapper.addSerialNumber(manageSerialNumber);
            if (result <= 0) {
                return null;
            }
        } else {
            Long nextNumber = manageSerialNumber.getCurrentSerialNumber() + manageSerialNumber.getGeneratedSerialNumber();
            manageSerialNumber.setCurrentSerialNumber(nextNumber);
            manageSerialNumber.setGeneratedSerialNumber(delta);
            manageSerialNumber.setSerialNumberDate(now);
            int result = manageSerialNumberMapper.updateSerialNumber(manageSerialNumber);
            if (result <= 0) {
                return null;
            }
        }
        return super.parseAutoIncrement(manageSerialNumber.getCurrentSerialNumber(), length, maxLength);
    }
}
