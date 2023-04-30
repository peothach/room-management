package com.roommanagement.repository.lodger;

import com.roommanagement.entity.Lodger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LodgerRepository extends JpaRepository<Lodger, Integer> {
}
