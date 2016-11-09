package com.companyname.framework;

import com.companyname.modules.rpc.HelloWorldClient;
import org.junit.Test;

/**
 * Created by Wangzr on 09/11/2016.
 */
public class GrpcTests {

    @Test
    public void sayHelloTest() {
        HelloWorldClient client = new HelloWorldClient("localhost", 50051);
        try {
            client.greet("world");
        } finally {
            try {
                client.shutdown();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
