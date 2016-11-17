package com.companyname.projectname.service;

import com.companyname.framework.AbstractGrpcClient;
import com.companyname.projectname.web.*;
import io.grpc.StatusRuntimeException;

import java.util.List;

public class DemoClient extends AbstractGrpcClient<DemoGrpc.DemoBlockingStub> {

    public DemoClient(String host, int port) {
        super(host, port);
    }

    @Override
    protected DemoGrpc.DemoBlockingStub getBlockingStub() {
        return DemoGrpc.newBlockingStub(channel);
    }

    public List<DemoEntity> query(int size) {
        QueryRequest request = QueryRequest.newBuilder().setSize(size).build();
        QueryResponse response;
        try {
            response = blockingStub.query(request);
        } catch (StatusRuntimeException e) {
            logger.warn("RPC failed: {0}", e.getStatus());
            return null;
        }
        return response.getEntitiesList();
    }
}
