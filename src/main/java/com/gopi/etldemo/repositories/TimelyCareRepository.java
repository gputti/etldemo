
package com.gopi.etldemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gopi.etldemo.model.TimelyCare;

/**
 * @author Gopikrishna Putti
 * Mar 6, 2022
 *
 */

@Repository
public interface TimelyCareRepository extends JpaRepository<TimelyCare, Long> {

}
