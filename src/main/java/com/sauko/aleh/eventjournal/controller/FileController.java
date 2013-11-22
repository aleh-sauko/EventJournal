package com.sauko.aleh.eventjournal.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.io.Files;
import com.sauko.aleh.eventjournal.domain.Event;
import com.sauko.aleh.eventjournal.domain.User;
import com.sauko.aleh.eventjournal.service.EventService;
import com.sauko.aleh.eventjournal.service.UserService;
 
@Controller
@RequestMapping("/files")
public class FileController {
 
	@Autowired
	private UserService userService;

	@Autowired
	private EventService eventService; 
    
	
    @RequestMapping(value="/upload/{fileId}", method = RequestMethod.POST)
    public String uploadAvatar(@PathVariable("fileId") String fileId,
    		MultipartHttpServletRequest request, HttpServletResponse response) {
    	
        //1. build an iterator
        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;
 
        //2. get each file
        while(itr.hasNext()){
 
             //2.1 get next MultipartFile
             mpf = request.getFile(itr.next()); 
             System.out.println(mpf.getOriginalFilename() +" uploaded! ");
 
             //2.3 create new fileMeta
             File avatar = new File(fileId);
             if (fileId.startsWith("userId")) {
            	 String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            	 User user =  userService.getUser(userName);
	             user.setAvatarId(avatar.getName());
	             userService.updateUser(user);
             }
             if (fileId.startsWith("eventId")) {
	             Event event = eventService.getEvent(Integer.parseInt(fileId.substring(7)));
            	 event.setAvatarId(avatar.getName());
	             eventService.updateEvent(event);
             }
             
             
             try {
                 // copy file to local disk (make sure the path "e.g. D:/temp/files" exists)            
                 FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream("D:/temp/files/" + avatar.getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
         }
        
        return (fileId.startsWith("eventId") ? "event_edit/"+fileId.substring(7) : "profile");
    }
    
    
    @RequestMapping(value="/upload_files/{eventId}/{folder}", method = RequestMethod.POST)
    public String upload(@PathVariable("eventId") Integer eventId, @PathVariable("folder") String folder,
    		MultipartHttpServletRequest request, HttpServletResponse response) {
        //1. build an iterator
        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;
 
        System.out.println("1");
        Event event = eventService.getEvent(eventId);
        
        File dir = new File("D:/temp/files/" + eventId);
        if (!dir.exists()) {
       	 	dir.mkdir();
        }
        
        dir = new File("D:/temp/files/" + eventId + "/" + folder);
        if (!dir.exists()) {
       	 	dir.mkdir();
        }
        System.out.println("2");
        //2. get each file
        while(itr.hasNext()){
 
             //2.1 get next MultipartFile
             mpf = request.getFile(itr.next()); 
             System.out.println(mpf.getOriginalFilename() +" uploaded! ");
             
             //2.3 create new fileMeta
             File file = new File(mpf.getOriginalFilename());
            
             try {
                 // copy file to local disk (make sure the path "e.g. D:/temp/files" exists)            
                 FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream("D:/temp/files/" + eventId + "/" 
                		 	+ "/" + folder + "/" + file.getName()));
                 if (folder.equals("photos"))
                	 event.addPhoto(file.getName());
                 else
                	 event.addTrack(file.getName());
             } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("3");
        eventService.updateEvent(event);
        
        return (folder.equals("photos") ? "photos" : "event/"+eventId);
    }
    
    
    @RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
    public void get(HttpServletResponse response,@PathVariable String value){
         try {      
                FileCopyUtils.copy(Files.toByteArray(new File("D:/temp/files/" + value)), response.getOutputStream());
         }catch (IOException e) {
                e.printStackTrace();
         }
    }
    
    @RequestMapping(value = "/get_user_avatar/{name}", method = RequestMethod.GET)
    public void getAvatar(HttpServletResponse response,@PathVariable String name){
    	User user = userService.getUser(name);
    	try {      
                FileCopyUtils.copy(Files.toByteArray(new File("D:/temp/files/" + user.getAvatarId())), response.getOutputStream());
        }catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    @Cacheable("media")
    @RequestMapping(value = "/get/{eventId}/{folder}/{fileId:.+}", method = RequestMethod.GET)
    public void getFile(HttpServletResponse response, @PathVariable("eventId") String eventId,
    		@PathVariable("folder") String folder, @PathVariable("fileId") String fileId){
        try {      
        	System.out.println(fileId);
        	fileId = new String(fileId.getBytes("ISO-8859-1"), "UTF-8");
        	FileCopyUtils.copy(Files.toByteArray(new File("D:/temp/files/" + eventId + "/" + folder + "/" + fileId)), 
        			response.getOutputStream());
        }catch (IOException e) {
               e.printStackTrace();
        }
    }
    
    @CacheEvict("media") 
    @RequestMapping(value = "/remove/{eventId}/{folder}/{fileId:.+}", method = RequestMethod.GET)
    public String removeFile(HttpServletResponse response, @PathVariable("eventId") Integer eventId,
    		@PathVariable("folder") String folder, @PathVariable("fileId") String fileId){
    	Event event = eventService.getEvent(eventId);
    	
        try {      
        	System.out.println("removing " + fileId);
        	fileId = new String(fileId.getBytes("ISO-8859-1"), "UTF-8");
        	if (folder.equals("audio"))
        		event.removeTrack(fileId);
        	if (folder.equals("photos"))
        		event.removePhoto(fileId);
        	eventService.updateEvent(event);
        	File file = new File("D:/temp/files/" + eventId + "/" + folder + "/" + fileId);
        	file.delete();
        }catch (IOException e) {
               e.printStackTrace();
        }
        return "redirect:/event/" + eventId + (folder.equals("photos") ? "/photos" : "");
    }
}
