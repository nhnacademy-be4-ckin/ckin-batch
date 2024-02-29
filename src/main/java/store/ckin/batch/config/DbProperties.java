package store.ckin.batch.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * DbProperties
 *
 * @author : gaeun
 * @version : 2024. 02. 20
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "ckin.mysql")
public class DbProperties {
    private String driver;
    private String url;
    private String userName;
    private String password;
    private int initialSize;
    private int maxTotal;
    private int maxIdle;
    private int minIdle;
    private int maxWaitMillis;
    private boolean testOnBorrow;
}
