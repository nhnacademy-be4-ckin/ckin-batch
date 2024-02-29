package store.ckin.batch.keymanager;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 29
 */
@Getter
@Setter
@ConfigurationProperties("ckin.keymanager")
public class KeyManagerProperties {
    private String appKey;
    private String password;
    private String url;
    private String path;
}