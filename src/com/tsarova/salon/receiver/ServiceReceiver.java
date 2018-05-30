package com.tsarova.salon.receiver;

import com.tsarova.salon.entity.Feedback;
import com.tsarova.salon.entity.Service;
import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.repository.Repository;
import com.tsarova.salon.repository.impl.FeedbackRepository;
import com.tsarova.salon.repository.impl.ServiceRepository;
import com.tsarova.salon.specification.Specification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceReceiver {
    private static Logger logger = LogManager.getLogger();


    public static List<Service> receiveServiceList() throws ReceiverException {

        List<Service> serviceList;
        Repository serviceRepository = new ServiceRepository();
        try {
            serviceList = serviceRepository.findAll(); //может optional
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR, e);
            throw new ReceiverException(e);
        }
        return serviceList;
    }

    public static boolean addService(String name, String content, double price)throws ReceiverException{
        if(name != null && content != null){//&& price????????????
            Service service = new Service(name, content, price);
            Repository<Service> serviceRepository = new ServiceRepository();

            try {
                if(serviceRepository.add(service)){
                    //user.getFeedbackIdList().add()
                    return true;
                }
            } catch (RepositoryException e) {
                logger.catching(Level.ERROR, e);
                throw new ReceiverException(e);
            }
        }
        return false;
    }


    public static boolean delService(Long serviceId) throws ReceiverException {
        if(serviceId!=null){//может, не надо
            Service service = new Service(serviceId);
            Repository<Service> serviceRepository = new ServiceRepository();
            try {
                System.out.println("SERVICE ID: " + service.getId());
                if(serviceRepository.remove(service)){
                    //user.getFeedbackIdList().add()
                    return true;
                }
            } catch (RepositoryException e) {
                logger.catching(Level.ERROR, e);
                throw new ReceiverException(e);
            }
        }
        return false;
    }

    public static boolean updateService(Long serviceId, String serviceName,
                                        String serviceContent, String pictureName,
                                        String servicePrice, Map<String, String> errorMap) throws ReceiverException{
        if(serviceId != null && !servicePrice.isEmpty() && !serviceContent.isEmpty() && !serviceName.isEmpty()){
            Service service = new Service(serviceId, serviceName, serviceContent,
                    Double.valueOf(servicePrice).doubleValue(), pictureName);
            Repository<Service> serviceRepository = new ServiceRepository();
            try {
                if(serviceRepository.update(service)){
                    //user.getFeedbackIdList().add()
                    return true;
                }
            } catch (RepositoryException e) {
                logger.catching(Level.ERROR, e);
                throw new ReceiverException(e);
            }
        }
        if(servicePrice.isEmpty()){
            errorMap.put("servicePrice", "Введите цену услуги");
        }
        if(serviceContent.isEmpty()){
            errorMap.put("serviceContent", "Введите описание услуги");
        }
        if(serviceName.isEmpty()){
            errorMap.put("serviceName", "Введите название услуги");
        }
        return false;
    }
}
