package com.companyname.projectname.rpc;

import com.companyname.framework.rpc.GrpcService;
import com.companyname.projectname.domain.Demo;
import com.companyname.projectname.service.Demo2Service;
import com.companyname.projectname.web.GreeterGrpc;
import com.companyname.projectname.web.HelloReply;
import com.companyname.projectname.web.HelloRequest;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@GrpcService
public class GreeterImpl extends GreeterGrpc.GreeterImplBase {

    @Autowired
    private Demo2Service service;

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        String message = "";
        List<Demo> demos = service.query();
        if (demos.size() > 0) {
            message = demos.get(0).getName();
        }

        

        HelloReply reply = HelloReply.newBuilder().setMessage(request.getName() + " " + message).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
