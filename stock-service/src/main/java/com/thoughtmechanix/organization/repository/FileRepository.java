package com.thoughtmechanix.organization.repository;

import com.thoughtmechanix.organization.model.FileDetail;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<FileDetail,String> {
}
