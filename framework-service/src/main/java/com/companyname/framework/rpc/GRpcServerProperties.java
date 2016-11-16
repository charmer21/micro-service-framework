package com.companyname.framework.rpc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("grpc")
public class GRpcServerProperties {

    private int port = 6565;
}
