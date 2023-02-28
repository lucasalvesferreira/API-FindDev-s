//package projetopi.finddevservice.services;
//
//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import projetopi.finddevservice.models.UsuarioModel;
//import projetopi.finddevservice.repositories.UsuarioRepository;
//
//import java.util.UUID;
//
//@Service
//public class SmsService {
//
//
//    @Autowired
//    UsuarioRepository repository;
//
//
//    @Value("${twilio.sid}")
//    private String twilioSid;
//
//    @Value("${twilio.key}")
//    private String twilioKey;
//
//    @Value("${twilio.phone.from}")
//    private String twilioPhoneFrom;
//
//    @Value("${twilio.phone.to}")
//    private String twilioPhoneTo;
//
//
//    public void resetPassworCpf(UUID id , String cellphone) {
//
//        UsuarioModel user = repository.findById(id).get();
//
//        String msg = "SEU TOKEN FIND DEV:  "+
//                "" +
//                "\n Se voce nao reconhece essa mensagem entre em contato urgentemente conosco!";
//
//        Twilio.init(twilioSid, twilioKey);
//
//        PhoneNumber to = new PhoneNumber(twilioPhoneTo);
//        PhoneNumber from = new PhoneNumber(twilioPhoneFrom);
//
//        Message message = Message.creator(to, from,msg).create();
//
//        System.out.println(message.getSid());
//    }
//
////    public void sendSms(UUID id , String cellphone) {
////
////        UsuarioModel user = repository.findById(id).get();
////
////        String msg = "Ola : "+ user.getNome() + "\n S"
////                " foi destaque em "+ date +" com um total de R$ "+ String.format("%.2f",sale.getAmount());
////
////        Twilio.init(twilioSid, twilioKey);
////
////        PhoneNumber to = new PhoneNumber(twilioPhoneTo);
////        PhoneNumber from = new PhoneNumber(twilioPhoneFrom);
////
////        Message message = Message.creator(to, from,msg).create();
////
////        System.out.println(message.getSid());
////    }
//}