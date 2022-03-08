
package com.gopi.etldemo.service;

import org.springframework.stereotype.Service;

import com.gopi.etldemo.model.Hospital;

/**
 * @author Gopikrishna Putti
 * Mar 5, 2022
 *
 */

@Service
public interface HospitalService {
	
	
	Hospital save(Hospital ho);

}
