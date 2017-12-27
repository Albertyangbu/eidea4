package indi.liudalei.eidea.report.init;

import indi.liudalei.eidea.core.rmi.ReportSettingsRmi;
import indi.liudalei.setting.RmiSettingHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

/**
 * Created by 刘大磊 on 2017/4/24 17:10.
 */
@Configuration
public class RmiClientServiceConfig {


    @Bean
    public RmiProxyFactoryBean purchaseFavoritesService() {
        return registerManagerService("reportSettingsRmi", ReportSettingsRmi.class);
    }
    private RmiProxyFactoryBean registerManagerService(String serviceName, Class classz) {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceUrl(RmiSettingHelper.getMainRmiUrl() + serviceName);
        rmiProxyFactoryBean.setServiceInterface(classz);
        return rmiProxyFactoryBean;
    }
}
