package com.way.open.pay.util.wx;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;

public class WXPayConfigImpl extends WXPayConfig{

    private byte[] certData;
    private static WXPayConfigImpl INSTANCE;
    
    @Value("${WEIXIN_CERT}")
    private static String WEIXIN_CERT;

    private WXPayConfigImpl() throws Exception{
    	File file=new File(WEIXIN_CERT+"/apiclient_cert.p12");
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    public static WXPayConfigImpl getInstance() throws Exception{
        if (INSTANCE == null) {
            synchronized (WXPayConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WXPayConfigImpl();
                }
            }
        }
        return INSTANCE;
    }
    @Value("${WEIXIN_APPID}")
    private String appId;
    
    @Value("${WEIXIN_PAY_MCHID}")
    private String mchId;
    
    @Value("${WEIXIN_PAY_KEY}")
    private String key;
    
    @Value("${WEIXIN_PAY_DOMIAN_IP}")
    private String domainIp;
    
    @Value("${WEIXIN_PAY_DOMIAN_IP_URL}")
    private String primaryDomain;
    
    @Value("${WEIXIN_PAY_DOMIAN_IP_URL}")
    private String alternateDomain;
    

    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 2000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    protected IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }

    public String getDomainIp() {
        return "139.224.104.94";
    }
    
    public String getPrimaryDomain() {
        return "api.mch.weixin.qq.com";
    }

    public String getAlternateDomain() {
        return "api2.mch.weixin.qq.com";
    }

    @Override
    public int getReportWorkerNum() {
        return 1;
    }

    @Override
    public int getReportBatchSize() {
        return 2;
    }

	@Override
	public String getAppID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMchID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}
}
