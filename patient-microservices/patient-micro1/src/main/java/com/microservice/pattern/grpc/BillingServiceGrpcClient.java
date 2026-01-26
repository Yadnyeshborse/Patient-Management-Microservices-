package com.microservice.pattern.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {

    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);

    private final String host;
    private final int port;

    private BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    public BillingServiceGrpcClient(
            @Value("${BILLING_SERVICE_ADDRESS}") String host,
            @Value("${BILLING_SERVICE_GRPC_PORT}") String portStr) { // accept as String
        this.host = host.trim();
        try {
            this.port = Integer.parseInt(portStr.trim()); // trim spaces & parse int
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid GRPC port: " + portStr, e);
        }
    }


    private synchronized void initStub() {
        if (blockingStub == null) {
            log.info("Creating gRPC channel to {}:{}", host, port);

            ManagedChannel channel = NettyChannelBuilder
                    .forAddress(host, port)
                    .usePlaintext()
                    .build();

            blockingStub = BillingServiceGrpc.newBlockingStub(channel);
        }
    }

    public BillingResponse createBillingAccount(String patientId, String name, String email) {
        initStub(); // initialize lazily
        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .build();

        return blockingStub.createBillingAccount(request);
    }
}
