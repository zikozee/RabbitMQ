package com.zikozee.rabbit_two;

import com.zikozee.rabbit_two.entity.DummyMessage;
import com.zikozee.rabbit_two.entity.InvoiceCancelledMessage;
import com.zikozee.rabbit_two.entity.InvoiceCreatedMessage;
import com.zikozee.rabbit_two.entity.InvoicePaidMessage;
import com.zikozee.rabbit_two.producer.*;
import com.zikozee.rabbit_two.producer.MultipleMessageTypes.InvoiceProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class RabbitProducerTwoApplication implements CommandLineRunner {

//    private final DummyProducer producer;
//    private final MultiplePrefetchProducer multiplePrefetchProducer;
//    private final InvoiceProducer invoiceProducer;
//    private final SingleActiveProducer activeProducer;
//    private final ReliableProducer reliableProducer;
    private final AnotherDummyProducer anotherDummyProducer;

    public static void main(String[] args) {
        SpringApplication.run(RabbitProducerTwoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        anotherDummy();
        log.info("Done");
    }

    private void anotherDummy(){
        var dummyMessage = new DummyMessage("Just a dummy message", 1);
        anotherDummyProducer.sendDummy(dummyMessage);
    }
//
//    private void requestReply(){
//        for (var i = 0; i < 10; i++) {
//            var invoiceNumber = "INV-" + i;
//            var invoiceCancelledMessage = new InvoiceCancelledMessage(LocalDate.now(), invoiceNumber, "test " + i);
//            invoiceProducer.sendInvoiceCancelled(invoiceCancelledMessage);
//        }
//    }
//
//
//    private void reliableProducer(){
//        var dummyMessage1 = new DummyMessage("Invalid test", 10);
//        var dummyMessage2 = new DummyMessage("Invalid test", 20);
//
//        reliableProducer.sendDummyWIthInvalidExchange(dummyMessage1);
//        reliableProducer.sendDummyWIthInvalidRoutingKey(dummyMessage2);
//    }
//
//    private void singleActiveProducer(){
//        activeProducer.sendDummy();
//    }
//
//    private void consistentHash(){
//        for (var i = 0; i < 200; i++) {
//            var invoiceNumber = "INV-" + (i % 60);
//            var invoice  = new InvoiceCreatedMessage(ThreadLocalRandom.current().nextInt(200), LocalDate.now(),
//                    "USD", invoiceNumber);
//
//            invoiceProducer.sendInvoiceCreated(invoice);
//        }
//    }
//
//
//    private void oneQueueMultipleMessageTypes(){
//        var randomInvoiceNumber = "INV-" + ThreadLocalRandom.current().nextInt(100, 200);
//        var invoiceCreatedMessage = new InvoiceCreatedMessage(152.26, LocalDate.now().minusDays(2), "USD", randomInvoiceNumber);
//        invoiceProducer.sendInvoiceCreated(invoiceCreatedMessage);
//
//        randomInvoiceNumber = "INV-" + ThreadLocalRandom.current().nextInt(200, 300);
//        var randomPaymentNumber = "PAY-" + ThreadLocalRandom.current().nextInt(800, 1000);
//        var invoicePaidMessage = new InvoicePaidMessage(randomInvoiceNumber, LocalDate.now(), randomPaymentNumber);
//        invoiceProducer.sendInvoicePaid(invoicePaidMessage);
//
//        randomInvoiceNumber = "INV-" + ThreadLocalRandom.current().nextInt(300, 400);
//        var invoiceCancelledMessage = new InvoiceCancelledMessage(LocalDate.now(), randomInvoiceNumber, "Just a test");
//        invoiceProducer.sendInvoiceCancelled(invoiceCancelledMessage);
//    }
//
//    private void multiplePrefetch(){
//        multiplePrefetchProducer.simulateScheduler();
//        multiplePrefetchProducer.simulateTransaction();
//        log.info("Done");
//    }
}
