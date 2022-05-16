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
    
    public Response getUserTasks(int userId, String status) {
        try {
            if(status.equals("all")){
                ArrayNode tasks = TDB.getUserAllTasks(userId);
                return Response.status(Response.Status.OK).entity(tasks.toString()).build();
            }else{
                ArrayNode tasks = TDB.getUserTasks(userId, status);
                return Response.status(Response.Status.OK).entity(tasks.toString()).build();
            }
        } catch (DatabaseException ex) {
            Logger.getLogger(TaskService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }
    
    public Response changeTaskStatus(int taskId, String status, String reason) {
        try {
            if(status.equals("elfogadott")){
                if(TDB.acceptTask(taskId)){
                    return Response.status(Response.Status.OK).entity("Accepted Task").build();
                }else{
                    return Response.status(Response.Status.BAD_REQUEST).entity("acceptTask failed, shouldnt get this error message").build();
                }
            }
            
            if(status.equals("elutasitott")){
                if(TDB.refuseTask(taskId, reason)){
                    return Response.status(Response.Status.OK).entity("Refused Task").build();
                }else{
                    return Response.status(Response.Status.BAD_REQUEST).entity("refusTask failed, shouldnt get this error message").build();
                }
            }
            
            if(status.equals("elkezdve")){
                if(TDB.startTask(taskId)){
                    return Response.status(Response.Status.OK).entity("Started Task").build();
                }else{
                    return Response.status(Response.Status.BAD_REQUEST).entity("startTask failed, shouldnt get this error message").build();
                }
            }
            
            if(status.equals("befejezve")){
                if(TDB.finishTask(taskId)){
                    return Response.status(Response.Status.OK).entity("Finished Task").build();
                }else{
                    return Response.status(Response.Status.BAD_REQUEST).entity("finishTask failed, shouldnt get this error message").build();
                }
            }
            
            return Response.status(Response.Status.BAD_REQUEST).entity("Cant recognize the given status").build();
        } catch (DatabaseException ex) {
            Logger.getLogger(TaskService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }
    
    public Response getUsers(String role) {
        try {
            if(role.equals("all")){
                ArrayNode users = TDB.getAllUsers();
                return Response.status(Response.Status.OK).entity(users.toString()).build();
            }else{
                ArrayNode users = TDB.getSomeUsers(role);
                return Response.status(Response.Status.OK).entity(users.toString()).build();
            }
        } catch (DatabaseException ex) {
            Logger.getLogger(TaskService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }
}
