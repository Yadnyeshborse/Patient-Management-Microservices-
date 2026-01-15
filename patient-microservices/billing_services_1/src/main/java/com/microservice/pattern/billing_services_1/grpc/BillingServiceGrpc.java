package com.microservice.pattern.billing_services_1.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingServiceGrpc extends BillingServiceImplBase {

    private static final Logger log =
            LoggerFactory.getLogger(BillingServiceGrpc.class);

    @Override
    public void createBillingAccount(
            BillingRequest request,
            StreamObserver<BillingResponse> responseObserver) {

        log.info(
                "Received billing request for patientId={}, name={}, email={}",
                request.getPatientId(),
                request.getName(),
                request.getEmail()
        );

        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("BILL-" + request.getPatientId())
                .setStatus("SUCCESS")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
