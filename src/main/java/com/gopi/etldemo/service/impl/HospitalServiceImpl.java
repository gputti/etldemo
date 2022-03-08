
package com.gopi.etldemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gopi.etldemo.model.Hospital;
import com.gopi.etldemo.repositories.HospitalRepository;
import com.gopi.etldemo.service.HospitalService;

/**
 * @author Gopikrishna Putti
 * Mar 5, 2022
 *
 */

@Service
public class HospitalServiceImpl implements HospitalService {

	
	@Autowired
	HospitalRepository hospitalRepo;
	
	@Override
	public Hospital save(Hospital ho) {
		ho = hospitalRepo.save(ho);
		return ho;
	}

}
