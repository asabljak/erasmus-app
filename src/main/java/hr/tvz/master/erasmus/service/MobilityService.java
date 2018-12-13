package hr.tvz.master.erasmus.service;

import hr.tvz.master.erasmus.repository.MobilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobilityService {

    @Autowired
    private MobilityRepository mobilityRepository;


}
