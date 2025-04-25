package com.augustojbe.client.service.impl;

import com.augustojbe.client.dto.PaymentProcessDto;
import com.augustojbe.client.dto.wsraspay.CustomerDto;
import com.augustojbe.client.dto.wsraspay.OrderDto;
import com.augustojbe.client.dto.wsraspay.PaymentDto;
import com.augustojbe.client.enuns.UserTypeEnum;
import com.augustojbe.client.exception.BussinesException;
import com.augustojbe.client.exception.NotFoundException;
import com.augustojbe.client.integration.MailIntegration;
import com.augustojbe.client.integration.WsRaspayIntegration;
import com.augustojbe.client.mapper.UserPaymentInfoMapper;
import com.augustojbe.client.mapper.wsraspay.CrediCardMapper;
import com.augustojbe.client.mapper.wsraspay.CustomerMapper;
import com.augustojbe.client.mapper.wsraspay.OrderMapper;
import com.augustojbe.client.mapper.wsraspay.PaymenteMapper;
import com.augustojbe.client.model.jpa.User;
import com.augustojbe.client.model.jpa.UserCredential;
import com.augustojbe.client.model.jpa.UserPaymenteInfo;
import com.augustojbe.client.repository.jpa.UseDetailRepository;
import com.augustojbe.client.repository.jpa.UserPaymenteInfoRepository;
import com.augustojbe.client.repository.jpa.UserRepository;
import com.augustojbe.client.repository.jpa.UserTypeRepository;
import com.augustojbe.client.service.PaymentInfoService;
import com.augustojbe.client.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaymentInfoServiceImp implements PaymentInfoService {

    @Value("${webservices.rasplus.default.password}")
    private String defaultPass;



   private final UserRepository userRepository;
   private final UserPaymenteInfoRepository userPaymenteInfoRepository;
   private final WsRaspayIntegration wsRaspayIntegration;
   private final MailIntegration mailIntegration;
   private final UseDetailRepository useDetailRepository;
   private final UserTypeRepository userTypeRepository;

    public PaymentInfoServiceImp(UserRepository userRepository,
                                 UserPaymenteInfoRepository userPaymenteInfoRepository,
                                 WsRaspayIntegration wsRaspayIntegration, MailIntegration mailIntegration, UseDetailRepository useDetailRepository, UserTypeRepository userTypeRepository) {
        this.userRepository = userRepository;
        this.userPaymenteInfoRepository = userPaymenteInfoRepository;
        this.wsRaspayIntegration = wsRaspayIntegration;
        this.mailIntegration = mailIntegration;
        this.useDetailRepository = useDetailRepository;
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public Boolean process(PaymentProcessDto dto) {
        //verifica usuario por id e verifica se jpa existe assinatura
        var userOpt = userRepository.findById(dto.getUserPaymentInfoDto().getUserId());
        if (userOpt.isEmpty()){
            throw new NotFoundException("Usuário não encontrado");
        }
        User user = userOpt.get();
        if (Objects.nonNull(user.getSubscriptionType())){
            throw new BussinesException("Pagamento não pode ser processado pois usuário já possui assinatura");
        }
        //cria ou atualiza usuario raspy
        CustomerDto customerDto = wsRaspayIntegration.createCustomer(CustomerMapper.build(user));

        //cria o pedido de pagamento
        OrderDto orderDto = wsRaspayIntegration.createOrder(OrderMapper.build(customerDto.getId(), dto));

        //processar pagamento
        PaymentDto paymentDto = PaymenteMapper.build(customerDto.getId(), orderDto.getId(), CrediCardMapper.build(dto.getUserPaymentInfoDto(), user.getCpf()));
        Boolean sucessPayment = wsRaspayIntegration.processPayment(paymentDto);

        if (Boolean.TRUE.equals(sucessPayment)){
            //salvar as informações de pagamento
            UserPaymenteInfo userPaymenteInfo = UserPaymentInfoMapper.fromDtoToEntity(dto.getUserPaymentInfoDto(), user);
            userPaymenteInfoRepository.save(userPaymenteInfo);

            var userTypeOpt = userTypeRepository.findById(UserTypeEnum.ALUNO.getId());

            if (userOpt.isEmpty()){
                throw new NotFoundException("UserType não encontrado");
            }

            UserCredential userCredential = new UserCredential(null, user.getEmail(), PasswordUtils.encode(defaultPass), userTypeOpt.get() );
            useDetailRepository.save(userCredential);

            mailIntegration.send(user.getEmail(), "Usuário:" +user.getEmail()+ " - Senha: " + defaultPass, "Acesso Liberado");

            return true;

        }
        return false;


    }
}
