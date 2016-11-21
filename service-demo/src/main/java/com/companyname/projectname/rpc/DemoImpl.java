package com.companyname.projectname.rpc;

import com.companyname.framework.rpc.GrpcService;
import com.companyname.projectname.domain.Demo;
import com.companyname.projectname.service.Demo2Service;
import com.companyname.projectname.web.*;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
            BeanUtils.copyProperties(item, entityBuilder);
            builder.addEntities(entityBuilder);
        }

        QueryResponse response = builder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
