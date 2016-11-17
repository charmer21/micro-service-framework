package com.companyname.projectname.rpc;

import com.companyname.framework.rpc.GrpcService;
import com.companyname.projectname.domain.Demo;
import com.companyname.projectname.service.Demo2Service;
import com.companyname.projectname.web.DemoEntity;
import com.companyname.projectname.web.DemoGrpc;
import com.companyname.projectname.web.QueryRequest;
import com.companyname.projectname.web.QueryResponse;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@GrpcService
public class DemoImpl extends DemoGrpc.DemoImplBase {

    @Autowired
    private Demo2Service service;

    @Override
    public void query(QueryRequest request, StreamObserver<QueryResponse> responseObserver) {
        int size = request.getSize();
        List<Demo> list = service.query();

        QueryResponse.Builder builder = QueryResponse.newBuilder();
        for(Demo item : list){
            DemoEntity.Builder entityBuilder = DemoEntity.newBuilder();
            entityBuilder.setId(item.getId());
            entityBuilder.setName(item.getName());
            entityBuilder.setMobile(item.getMobile());
            builder.addEntities(entityBuilder);
        }

        QueryResponse response = builder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
