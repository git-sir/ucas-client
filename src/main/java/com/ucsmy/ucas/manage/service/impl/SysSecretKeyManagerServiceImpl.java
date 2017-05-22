package com.ucsmy.ucas.manage.service.impl;

import com.ucsmy.commons.utils.AESUtil;
import com.ucsmy.commons.utils.RSAUtils;
import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.manage.service.SysSecretKeyManagerService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ucs_leijinming on 2017/4/19.
 */
@Service
public class SysSecretKeyManagerServiceImpl implements SysSecretKeyManagerService {
    @Override
    @Logger
    public Map<String, String> getRsaPubKey(HttpSession httpSession) {
        Map<String, String> data = new HashMap<String, String>();
        KeyPair keyPair = RSAUtils.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        httpSession.setAttribute(RSAUtils.PRIVATE_KEY_ATTRIBUTE_NAME, privateKey);
        data.put("modulus", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
        data.put("exponent", Base64.encodeBase64String(publicKey
                .getPublicExponent().toByteArray()));
        data.put("strkey", Base64.encodeBase64String(this.getAesKeyByte(httpSession)));
        return data;
    }

    @Override
    @Logger
    public Map<String, String> getAesKey(HttpSession httpSession) {
        Map<String, String> data = new HashMap<String, String>();
        byte[] rawKey =   AESUtil.getRawKey(AESUtil.strKey.getBytes());
        httpSession.setAttribute(AESUtil.strKey, rawKey);
        data.put("skey", Base64.encodeBase64String(rawKey));
        return data;
    }

    @Logger
    private byte[] getAesKeyByte(HttpSession httpSession) {
        Map<String, String> data = new HashMap<String, String>();
        byte[] rawKey =   AESUtil.getRawKey(AESUtil.strKey.getBytes());
        httpSession.setAttribute(AESUtil.strKey, rawKey);
        return rawKey;
    }
}
