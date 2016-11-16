package com.companyname.projectname.service;

import com.companyname.projectname.web.GreeterGrpc;
import com.companyname.projectname.web.HelloReply;
import com.companyname.projectname.web.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class GreeterClient {

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    public GreeterClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true));
    }

    GreeterClient(ManagedChannelBuilder<?> channelBuilder){
        channel = channelBuilder.build();
        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public String greet(String greet) {
        HelloRequest request = HelloRequest.newBuilder().setName(greet).build();
        HelloReply response;
        try {
            response = blockingStub.sayHello(request);
        }catch(StatusRuntimeException e){
            logger.warn("RPC failed: {0}", e.getStatus());
            return "";
        }
        return response.getMessage();
    }
}
