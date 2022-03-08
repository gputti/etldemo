
package com.gopi.etldemo.service;

import org.springframework.stereotype.Service;

import com.gopi.etldemo.model.TimelyCare;

/**
 * @author Gopikrishna Putti
 * Mar 6, 2022
 *
 */

@Service
public interface TimelyCareService {

	TimelyCare save(TimelyCare tc);
}
