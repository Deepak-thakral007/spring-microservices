package com.thoughtmechanix.organization.repository;

import com.thoughtmechanix.organization.model.DailyPrice;
import com.thoughtmechanix.organization.model.FileDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyPriceRepository extends CrudRepository<DailyPrice,String> {
}
