
package com.gopi.etldemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gopi.etldemo.model.Hospital;

/**
 * @author Gopikrishna Putti
 * Mar 6, 2022
 *
 */

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

}
