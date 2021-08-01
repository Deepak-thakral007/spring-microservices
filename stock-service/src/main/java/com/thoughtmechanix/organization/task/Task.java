package com.thoughtmechanix.organization.task;

import org.springframework.data.repository.CrudRepository;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public interface Task {

    public void initialize (Input input,Map contextMap,CrudRepository crudRepository);

    public Result execute (Input input, Map contextMap,CrudRepository crudRepository) throws ParseException, IOException;

    public void apply(Result result, Map contextMap, CrudRepository crudRepository);

    public String getName();

}
