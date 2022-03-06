/**
 * Copyright © 2018–2020 by MachEye Inc. All rights reserved.
 * NOTICE: All information contained herein is the property of MachEye Inc.
 * No part of this publication may be reproduced, stored in a retrieval system, 
 * or transmitted, in any form or by any means, electronic, mechanical, 
 * photocopying, or otherwise, without the prior written consent of 
 * the publisher. Any software referred to herein is furnished under 
 * license and may only be used or copied in accordance with the terms 
 * of such license.
 * This publication and the information herein is furnished AS IS, and 
 * is subject to change without notice. MachEye Inc. assumes no responsibility 
 * or liability for any errors or inaccuracies, makes no warranty of any kind 
 * (express, implied or statutory) with respect to this publication, 
 * and expressly disclaims any and all warranties of merchantability, 
 * fitness for particular purposes and noninfringement of third party rights.
 * 
 **/
package com.gopi.etldemo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Gopikrishna Putti
 * Mar 5, 2022
 * 
 * loads data from raw data files
 */

public class LoadingService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoadingService.class);
	
	// returns file contents as string.
	public static String getFileContentsFromtPath(String path) {

		StringBuilder sb = new StringBuilder( );
		try {			
			File file = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;			
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\r\n");
			}
			br.close();

		} catch (IOException e) {
			logger.error("Error reading peoplename.txt " +e.getMessage());
		}
		return sb.toString();

	}
	
	

}
