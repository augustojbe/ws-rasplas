package com.augustojbe.client.service.impl;

import com.augustojbe.client.dto.PaymentProcessDto;
import com.augustojbe.client.dto.wsraspay.CustomerDto;
import com.augustojbe.client.dto.wsraspay.OrderDto;
import com.augustojbe.client.dto.wsraspay.PaymentDto;
import com.augustojbe.client.exception.BussinesException;
import com.augustojbe.client.exception.NotFoundException;
import com.augustojbe.client.integration.MailIntegration;
import com.augustojbe.client.integration.WsRaspayIntegration;
import com.augustojbe.client.mapper.UserPaymentInfoMapper;
import com.augustojbe.client.mapper.wsraspay.CrediCardMapper;
import com.augustojbe.client.mapper.wsraspay.PaymenteMapper;
import com.augustojbe.client.model.User;
import com.augustojbe.client.model.UserPaymenteInfo;
import com.augustojbe.client.mapper.wsraspay.CustomerMapper;
import com.augustojbe.client.mapper.wsraspay.OrderMapper;
import com.augustojbe.client.repository.UserPaymenteInfoRepository;
import com.augustojbe.client.repository.UserRepository;
import com.augustojbe.client.service.PaymentInfoService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaymentInfoServiceImp implements PaymentInfoService {

   private final UserRepository userRepository;
   private final UserPaymenteInfoRepository userPaymenteInfoRepository;
   private final WsRaspayIntegration wsRaspayIntegration;
   private final MailIntegration mailIntegration;

    public PaymentInfoServiceImp(UserRepository userRepository,
                                 UserPaymenteInfoRepository userPaymenteInfoRepository,
                                 WsRaspayIntegration wsRaspayIntegration, MailIntegration mailIntegration) {
        this.userRepository = userRepository;
        this.userPaymenteInfoRepository = userPaymenteInfoRepository;
        this.wsRaspayIntegration = wsRaspayIntegration;
        this.mailIntegration = mailIntegration;
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
            mailIntegration.send(user.getEmail(), "Usuário:" +user.getEmail()+ " - Senha: alunorasmo ", "Acesso Liberado");

            return true;

        }
        return false;


    }
}
