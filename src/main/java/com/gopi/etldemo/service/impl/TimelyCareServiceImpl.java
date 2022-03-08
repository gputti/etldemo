
package com.gopi.etldemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gopi.etldemo.model.TimelyCare;
import com.gopi.etldemo.repositories.TimelyCareRepository;
import com.gopi.etldemo.service.TimelyCareService;

/**
 * @author Gopikrishna Putti
 * Mar 6, 2022
 *
 */

@Service
public class TimelyCareServiceImpl implements TimelyCareService {

	@Autowired
	TimelyCareRepository repo; 
	
	@Override
	public TimelyCare save(TimelyCare tc) {
		tc = repo.save(tc);
		return tc;		
	}

}
