package com.companyname.framework;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.AbstractStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public abstract class AbstractGrpcClient<T extends AbstractStub<T>> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    protected ManagedChannel channel;
    protected T blockingStub;
    protected final ManagedChannelBuilder<?> managedChannelBuilder;

    public AbstractGrpcClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true));
        channel = managedChannelBuilder.build();
        blockingStub = getBlockingStub();
    }

    protected abstract T getBlockingStub();

    AbstractGrpcClient(ManagedChannelBuilder<?> channelBuilder) {
        managedChannelBuilder = channelBuilder;
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

}
