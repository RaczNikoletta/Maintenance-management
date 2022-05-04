/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mm.rest.db.qualification.QualificationDatabase;
import com.mm.rest.db.task.TaskDatabase;
import com.mm.rest.exceptions.DatabaseException;
import com.mm.rest.models.equipment.AddTaskModel;
import com.mm.rest.models.equipment.EquipmentModel;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;

/**
 *
 * @author burkus
 */
public class TaskService {
    
    private TaskDatabase TDB = new TaskDatabase();
    private QualificationDatabase QDB = new QualificationDatabase();
    
    public Response addTask(AddTaskModel task) {
        
        
        int temp = TDB.addTaskToDatabase(task);
        
        if(temp!=-1){
            return Response.status(Response.Status.OK).entity("Task added!").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("ADD TASK ERROR!").build();
        }
    }
    
    public Response automaticTaskAddition() {
        try {
            List<AddTaskModel> repairs = TDB.getIntervalRepairs();
            System.out.println(repairs.size());
            int result;
            if(repairs.isEmpty()){
                result = 0;
            }else{
                result = TDB.addTasksToDatabase(repairs);
            }
            
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (DatabaseException ex) {
            Logger.getLogger(TaskService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_MODIFIED).entity("ERROR!").build();
        }
    }
    
    public Response getTasks() {
        try {
            ArrayNode tasks = TDB.getTasks();
            return Response.status(Response.Status.OK).entity(tasks.toString()).build();
        } catch (DatabaseException ex) {
            Logger.getLogger(TaskService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_MODIFIED).entity("ERROR!").build();
        }
    }
    
    public Response assignTask(int user_id, int taskId) {
        try {
            int qualificationId = TDB.getTaskQualification(taskId);
            System.out.println(qualificationId);
            
            if(QDB.checkIfQualified(user_id, qualificationId)){
                
                if(TDB.assignTask(user_id, taskId)){
                    return Response.status(Response.Status.OK).entity("Assigned Task").build();
                }else{
                    return Response.status(Response.Status.BAD_REQUEST).entity("assignTask failed, shouldnt get this error message").build();
                }
                
            }else{
                return Response.status(Response.Status.BAD_REQUEST).entity("assignTask failed, shouldnt get this error message").build();
            }
        } catch (DatabaseException ex) {
            Logger.getLogger(TaskService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }
}
